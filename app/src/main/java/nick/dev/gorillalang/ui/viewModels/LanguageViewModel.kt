package nick.dev.gorillalang.ui.viewModels

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.launch
import nick.dev.gorillalang.models.Mistake
import nick.dev.gorillalang.models.Module
import nick.dev.gorillalang.models.Word
import nick.dev.gorillalang.repository.LanguageRepository

class LanguageViewModel(app:Application, private val languageRepository:
LanguageRepository):AndroidViewModel(app)
{

    private val _publicModules = MutableLiveData<List<Module>>()
    val publicModules = _publicModules

    private val _allModules = MutableLiveData<List<Module>>()
    val allModules = _allModules

    private val _allMistakes = MutableLiveData<List<Mistake>>()
    val allMistakes = _allMistakes

    private val _words = MutableLiveData<List<Word>>()
    val words = _words
    var wordsReady = false




    fun deleteMistake(mistake: Mistake) = viewModelScope.launch {
        languageRepository.deleteMistake(mistake)
    }

    fun saveModule(module: Module) = viewModelScope.launch {
        languageRepository.upsertModule(module)
    }
    fun deleteModule(module: Module) = viewModelScope.launch {
        languageRepository.deleteModule(module)
    }
    fun getUserModules() = languageRepository.getUserModules()
    fun getUserMistakes() = languageRepository.getMistakes()


    fun saveWord(word: Word) = viewModelScope.launch {
        languageRepository.upsertWord(word)
    }
    fun getWordById(wordId:String) = languageRepository.getWordById(wordId)



    fun getModuleWithWords(moduleId:String) = languageRepository.getModuleWithWords(moduleId)
    fun deleteWord(word:Word) = viewModelScope.launch {
        languageRepository.deleteWord(word)
    }

    fun getPublicModules() = languageRepository.getPublicModules()
        .addOnSuccessListener {
            documents->
                val modules = mutableListOf<Module>()
            for(document in documents) {
                modules.add(Module(document["moduleName"].toString(),true,document.id))

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


        }.addOnFailureListener {
            _publicModules.value = listOf()

        }
}

fun QuerySnapshot.toListOfWords(module: Module):List<Word>{

    val wordsRemote = mutableListOf<Word>()
    for(document in documents) {

        val itWord = document["it_word"].toString()
        val enWord = document["en_word"].toString()


        wordsRemote.add(Word(enWord, itWord, module.remoteId, isRemote = true,document.id))
    }
    return wordsRemote
}
