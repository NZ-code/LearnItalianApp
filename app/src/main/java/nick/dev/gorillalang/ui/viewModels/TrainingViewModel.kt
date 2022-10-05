package nick.dev.gorillalang.ui.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import nick.dev.gorillalang.etraining.*
import nick.dev.gorillalang.models.*
import nick.dev.gorillalang.repository.LanguageRepository
import nick.dev.gorillalang.util.Constants.LEARNED_WORDS_MODULE_ID
import nick.dev.gorillalang.util.Constants.MAX_WORD_PROGRESS
import java.util.*
import kotlin.math.min

const val TAG = "TRAINING"
class TrainingViewModel(app:Application, private val languageRepository:
LanguageRepository,val module:Module):AndroidViewModel(app)
{


    private val progressQuestions = mutableListOf<Question>()
    private val mistakes = mutableMapOf<Word,MutableList<String>>()
    private val _words = MutableLiveData<List<Word>>()
    val words = _words
    fun getModuleWithWords(moduleId:String) = languageRepository.getModuleWithWords(moduleId)


    fun  addMistake(word:Word, mistake: String){
        if(mistakes.isEmpty()){
            mistakes[word] = mutableListOf(mistake)
        }
        else{
            mistakes[word]?.add(mistake)
        }
    }

    fun saveMistake(mistake: Mistake) = viewModelScope.launch {
        languageRepository.upsertMistake(mistake)
    }
    fun deleteMistake(mistake: Mistake) = viewModelScope.launch {
        languageRepository.deleteMistake(mistake)
    }
    fun updateProgress(){
        for (mistake in mistakes){
            for(userMistake in mistake.value){
                val realWord = mistake.key

                val uniqueID = UUID.randomUUID().toString()
                saveMistake(Mistake(uniqueID,userMistake,realWord.remoteId))
            }

        }

        for (question in progressQuestions){
            when(question.type){
                Question.TYPE_QUIZ->{

                    val q = question as QuizQuestion
                    val word = q.questionWord
                    val prevProgress = q.questionWord.progress
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
                    val word = q.questionWord
                    val prevProgress = q.questionWord.progress
                    val newProgress = min(MAX_WORD_PROGRESS, prevProgress+ q.progressVal)
                    word.progress = newProgress
                    updateWordProgress(word, newProgress)

                }
            }
        }
    }
    fun updateWordProgress(word:Word, progress:Int) = viewModelScope.launch {
        languageRepository.upsertRemoteWordsProgress(RemoteWordsProgress(word.remoteId,progress))
    }
    fun addGoodAnsweredQuestion(question:Question){
        progressQuestions.add(question)
    }


    fun getWordByRemoteModule(module: Module) = languageRepository.getWordsByRemoteModule(module)
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
    fun getWordByLocalModule(module: Module) = languageRepository.getModuleWithWords(module.remoteId)
        

    fun getNewQuizGame(words:List<Word>) = QuizGame(words)


}

