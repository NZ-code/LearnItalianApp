package nick.dev.gorillalang.ui.viewModels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import nick.dev.gorillalang.R
import nick.dev.gorillalang.etraining.*
import nick.dev.gorillalang.models.*
import nick.dev.gorillalang.repository.LanguageRepository
import nick.dev.gorillalang.ui.fragments.training.MatchFragment
import nick.dev.gorillalang.ui.fragments.training.QuizFragment
import nick.dev.gorillalang.ui.fragments.training.WritingFragment
import nick.dev.gorillalang.util.Constants.LEARNED_WORDS_MODULE_ID
import nick.dev.gorillalang.util.Constants.MAX_WORD_PROGRESS
import kotlin.math.min

const val TAG = "TRAINING"
class TrainingViewModel(app:Application, private val languageRepository:
LanguageRepository,val module:ModuleRemote):AndroidViewModel(app)
{


    private val progressQuestions = mutableListOf<Question>()

    private val _words = MutableLiveData<List<WordRemote>>()
    val words = _words
    fun getModuleWithWords(moduleId:String) = languageRepository.getModuleWithWords(moduleId)

    fun updateProgress(){
        for (question in progressQuestions){
            when(question.type){
                Question.TYPE_QUIZ->{

                    val q = question as QuizQuestion
                    val word = q.questionWordRemote
                    val prevProgress = q.questionWordRemote.progress
                    val newProgress = min(MAX_WORD_PROGRESS, prevProgress + q.progressVal)
                    word.progress = newProgress
                    updateWordProgress(word, newProgress)


                }
                Question.TYPE_MATCH->{
                    val q = question as MatchQuestion
                    for(word in q.wordsMatching){
                        val prevProgress = word.progress
                        val newProgress = min(MAX_WORD_PROGRESS, prevProgress+ q.progressVal)
                        word.progress = newProgress
                        updateWordProgress(word, newProgress)
                    }

                }
                Question.TYPE_WRITING->{
                    val q = question as WritingQuestion
                    val word = q.questionWordRemote
                    val prevProgress = q.questionWordRemote.progress
                    val newProgress = min(MAX_WORD_PROGRESS, prevProgress+ q.progressVal)
                    word.progress = newProgress
                    updateWordProgress(word, newProgress)

                }
            }
        }
    }
    fun updateWordProgress(word:WordRemote,progress:Int) = viewModelScope.launch {
        languageRepository.upsertRemoteWordsProgress(RemoteWordsProgress(word.remoteId,progress))
    }
    fun addGoodAnsweredQuestion(question:Question){
        progressQuestions.add(question)
    }


    fun getWordByRemoteModule(module: ModuleRemote) = languageRepository.getWordsByRemoteModule(module)
        .addOnSuccessListener {
            _words.value = it.toListOfWords(module)

            // update progress
            for (word in _words.value!!){
                val progress = languageRepository.getRemoteProgressById(word.remoteId)
                progress.observeForever{
                        progressList->
                    if(progressList.isNotEmpty()){
                        word.progress = progressList[0].level
                    }
                }
            }

        }.addOnFailureListener {
            _words.value = listOf()

        }
    fun getWordByLocalModule(module: ModuleRemote) = languageRepository.getModuleWithWords(module.remoteId)
        

    fun getNewQuizGame(words:List<WordRemote>) = QuizGame(words)

    fun saveWordToLearned(wordRemote: WordRemote) = viewModelScope.launch {
        val savedWord = wordRemote.copy()
        savedWord.moduleId = LEARNED_WORDS_MODULE_ID
        languageRepository.upsertWord(savedWord)
    }
}

