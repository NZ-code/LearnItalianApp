package nick.dev.gorillalang.ui.viewModels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import nick.dev.gorillalang.etraining.*
import nick.dev.gorillalang.models.*
import nick.dev.gorillalang.repository.LanguageRepository
import nick.dev.gorillalang.util.Constants.MAX_WORD_PROGRESS
import kotlin.math.min

const val TAG = "TRAINING"
class TrainingViewModel(app:Application, private val languageRepository:
LanguageRepository,val module:ModuleRemote):AndroidViewModel(app)
{


    private val progressQuestions = mutableListOf<Question>()

    private val _words = MutableLiveData<List<WordRemote>>()
    val words = _words


    fun updateProgress(){
        for(question in progressQuestions){
            upsertRemoteProgress(question)

        }
        var progressAll = 0
        for (word in words.value!!){
            progressAll += (languageRepository.getRemoteProgressById(word.remoteId).value?.get(0)?.level
                ?: 0)
        }
        val maxPossibleProgress = words.value!!.size * MAX_WORD_PROGRESS

        var moduleProgress = progressAll/maxPossibleProgress
        Log.d(TAG,"PROGRESS ALL:$progressAll")
        upsertSingleRemoteModuleProgress(moduleProgress)
    }
    fun upsertSingleRemoteModuleProgress(moduleProgress: Int) = viewModelScope.launch {
        languageRepository.upsertRemoteModuleProgress(RemoteModuleProgress(module.remoteId,moduleProgress))
    }

    fun addGoodAnsweredQuestion(question:Question){
        progressQuestions.add(question)
    }
    fun upsertRemoteProgress(question:Question)= viewModelScope.launch {
        when(question.type){
                Question.TYPE_MATCH->{
                    val q = question as MatchQuestion
                    for(word in q.wordRemotes){

                        upsertRemoteProgressSingleWord(word,question.progressVal)
                    }
                }
                Question.TYPE_WRITING->{
                    val q = question as WritingQuestion
                    val word =  q.questionWordRemote
                    upsertRemoteProgressSingleWord(word,question.progressVal)
                }
                Question.TYPE_QUIZ->{
                    val q = question as QuizQuestion
                    val word =  q.questionWordRemote
                    upsertRemoteProgressSingleWord(word,question.progressVal)
                }
            }

        }
    suspend fun upsertRemoteProgressSingleWord(word:WordRemote, progress:Int){

        val wordId = word.remoteId
        val prevProgress = languageRepository.getRemoteProgressById(wordId).value?.get(0)?.level ?: 0
        Log.d(TAG,"PREVIOUS PROGRESS:$prevProgress")
        val currentProgress = min(prevProgress + progress, MAX_WORD_PROGRESS)

        languageRepository.upsertRemoteWordsProgress(RemoteWordsProgress(wordId,currentProgress))
    }

    fun getWordByRemoteModule(module: ModuleRemote) = languageRepository.getWordsByRemoteModule(module)
        .addOnSuccessListener {
            _words.value = it.toListOfWords(module)
            Log.d("Firebase","get all words ")
        }.addOnFailureListener {
            _words.value = listOf()
            Log.d("Firebase","failed to get all words ")
        }

    fun getNewQuizGame(words:List<WordRemote>) = QuizGame(words)


}

