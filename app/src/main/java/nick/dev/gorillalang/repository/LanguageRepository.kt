package nick.dev.gorillalang.repository

import androidx.lifecycle.LiveData
import kotlinx.coroutines.tasks.await
import nick.dev.gorillalang.db.LanguageDatabase
import nick.dev.gorillalang.models.Module
import nick.dev.gorillalang.models.RemoteWordsProgress
import nick.dev.gorillalang.models.Word
import nick.dev.gorillalang.remote.RemoteLanguageDatabase

class LanguageRepository(
    val db: LanguageDatabase,
    val rdb :RemoteLanguageDatabase

    )
{
    fun getUserModules() = db.getLanguageDao().getAllModules()
    suspend fun deleteModule(module:Module) = db.getLanguageDao().deleteModule(module)
    suspend fun upsertModule(module: Module)=db.getLanguageDao().upsertModule(module)

    suspend fun upsertWord(word: Word) = db.getLanguageDao().upsertWord(word)

    fun getModuleWithWords(moduleId:Int) = db.getLanguageDao().getModuleWithWords(moduleId)
    suspend fun deleteWord(word: Word) = db.getLanguageDao().deleteWord(word)

    fun getPublicModules() = rdb.getModules()
    fun getWordsByRemoteModule(module:Module) = rdb.getWordsByModule(module)

    suspend fun upsertRemoteWordsProgress(progress: RemoteWordsProgress)
    = db.getLanguageDao().upsertRemoteWordsProgress(progress)

    fun getRemoteProgressById(id:String)
    =db.getLanguageDao().getRemoteProgressById(id)
}