package nick.dev.gorillalang.ui.viewModels

import android.app.Application
import android.speech.tts.TextToSpeech
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.viewModelFactory
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import nick.dev.gorillalang.R
import nick.dev.gorillalang.etraining.*
import nick.dev.gorillalang.models.Module
import nick.dev.gorillalang.models.RemoteWordsProgress
import nick.dev.gorillalang.models.Word
import nick.dev.gorillalang.repository.LanguageRepository
import nick.dev.gorillalang.ui.fragments.training.MatchFragment
import nick.dev.gorillalang.ui.fragments.training.QuizFragment
import nick.dev.gorillalang.ui.fragments.training.WritingFragment
import nick.dev.gorillalang.util.Constants.MAX_WORD_PROGRESS
import java.lang.Exception
import java.util.*
import kotlin.math.min

class LanguageViewModel(app:Application, private val languageRepository:
LanguageRepository):AndroidViewModel(app)
{
    private val context = getApplication<Application>().applicationContext
    var tts:TextToSpeech?=null
    private val _publicModules = MutableLiveData<List<Module>>()
    val publicModules = _publicModules

    private val _allModules = MutableLiveData<List<Module>>()
    val allModules = _allModules

    private val _words = MutableLiveData<List<Word>>()
    val words = _words

    fun saveModule(module: Module) = viewModelScope.launch {
        languageRepository.upsertModule(module)
    }
    fun deleteModule(module: Module) = viewModelScope.launch {
        languageRepository.deleteModule(module)
    }
    fun getUserModules() = languageRepository.getUserModules()

    fun saveWord(word: Word) = viewModelScope.launch {
        languageRepository.upsertWord(word)
    }
    fun getModuleWithWords(moduleId:Int) = languageRepository.getModuleWithWords(moduleId)
    fun deleteWord(word:Word) = viewModelScope.launch {
        languageRepository.deleteWord(word)
    }
    fun upsertRemoteProgress(question:Question)= viewModelScope.launch {
        when(question.type){
                Question.TYPE_MATCH->{
                    val q = question as MatchQuestion
                    for(word in q.words){
                        upsertRemoteProgressSingleWord(word)
                    }
                }
                Question.TYPE_WRITING->{
                    val q = question as WritingQuestion
                    val word =  q.questionWord
                    upsertRemoteProgressSingleWord(word)
                }
                Question.TYPE_QUIZ->{
                    val q = question as QuizQuestion
                    val word =  q.questionWord
                    upsertRemoteProgressSingleWord(word)
                }
            }

        }
    suspend fun upsertRemoteProgressSingleWord(word:Word){

        val wordId = word.remoteId
        val prevProgress = languageRepository.getRemoteProgressById(wordId).value?.get(0)?.level ?: 0
        val currentProgress = min(prevProgress, MAX_WORD_PROGRESS)
        languageRepository.upsertRemoteWordsProgress(RemoteWordsProgress(wordId,currentProgress))
    }
    fun getPublicModules() = languageRepository.getPublicModules()
        .addOnSuccessListener {
            documents->
                val modules = mutableListOf<Module>()
            for(document in documents) {
                modules.add(Module(document["moduleName"].toString(),true))
            }
            _publicModules.value = modules
            Log.d("Firebase","get all modules ")
        }.addOnFailureListener {
            _publicModules.value = listOf()
            Log.d("Firebase","failed to get all modules ")
        }
    fun getWordByRemoteModule(module: Module) = languageRepository.getWordsByRemoteModule(module)
        .addOnSuccessListener {
            _words.value = it.toListOfWords(module)
            Log.d("Firebase","get all words ")
        }.addOnFailureListener {
            _publicModules.value = listOf()
            Log.d("Firebase","failed to get all words ")
        }

    fun getNewQuizGame(words:List<Word>) = QuizGame(words)

    fun playWord(text:String) {
        if(tts != null){
            Log.d("TAG","tts is ready")
        }
        tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null,"")
    }

    override fun onCleared() {
        super.onCleared()

        tts?.stop()
        tts?.shutdown()


    }
}

fun QuerySnapshot.toListOfWords(module: Module):List<Word>{

    val wordsRemote = mutableListOf<Word>()
    for(document in documents) {

        val itWord = document["it_word"].toString()
        val enWord = document["en_word"].toString()
        wordsRemote.add(Word(enWord, itWord, module.id, isRemote = true,document.id))
    }
    return wordsRemote
}
