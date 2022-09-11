package nick.dev.gorillalang.ui.viewModels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.launch
import nick.dev.gorillalang.models.ModuleRemote
import nick.dev.gorillalang.models.RemoteWordsProgress
import nick.dev.gorillalang.models.WordRemote
import nick.dev.gorillalang.repository.LanguageRepository

class LanguageViewModel(app:Application, private val languageRepository:
LanguageRepository):AndroidViewModel(app)
{

    private val _publicModules = MutableLiveData<List<ModuleRemote>>()
    val publicModules = _publicModules

    private val _allModules = MutableLiveData<List<ModuleRemote>>()
    val allModules = _allModules

    private val _words = MutableLiveData<List<WordRemote>>()
    val words = _words
    var wordsReady = false





    fun saveModule(moduleRemote: ModuleRemote) = viewModelScope.launch {
        languageRepository.upsertModule(moduleRemote)
    }
    fun deleteModule(moduleRemote: ModuleRemote) = viewModelScope.launch {
        languageRepository.deleteModule(moduleRemote)
    }
    fun getUserModules() = languageRepository.getUserModules()

    fun saveWord(wordRemote: WordRemote) = viewModelScope.launch {
        languageRepository.upsertWord(wordRemote)
    }



    fun getModuleWithWords(moduleId:String) = languageRepository.getModuleWithWords(moduleId)
    fun deleteWord(wordRemote:WordRemote) = viewModelScope.launch {
        languageRepository.deleteWord(wordRemote)
    }

    fun getPublicModules() = languageRepository.getPublicModules()
        .addOnSuccessListener {
            documents->
                val moduleRemotes = mutableListOf<ModuleRemote>()
            for(document in documents) {
                moduleRemotes.add(ModuleRemote(document["moduleName"].toString(),true,document.id))

            }
            _publicModules.value = moduleRemotes
            Log.d("Firebase","get all modules ")
        }.addOnFailureListener {
            _publicModules.value = listOf()
            Log.d("Firebase","failed to get all modules ")
        }
    fun getWordByRemoteModule(moduleRemote: ModuleRemote) = languageRepository.getWordsByRemoteModule(moduleRemote)
        .addOnSuccessListener {

            _words.value = it.toListOfWords(moduleRemote)


        }.addOnFailureListener {
            _publicModules.value = listOf()

        }
}

fun QuerySnapshot.toListOfWords(moduleRemote: ModuleRemote):List<WordRemote>{

    val wordsRemote = mutableListOf<WordRemote>()
    for(document in documents) {

        val itWord = document["it_word"].toString()
        val enWord = document["en_word"].toString()


        wordsRemote.add(WordRemote(enWord, itWord, moduleRemote.remoteId, isRemote = true,document.id))
    }
    return wordsRemote
}
