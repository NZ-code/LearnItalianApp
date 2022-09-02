package nick.dev.gorillalang.repository

import nick.dev.gorillalang.db.LanguageDatabase
import nick.dev.gorillalang.models.ModuleRemote
import nick.dev.gorillalang.models.RemoteModuleProgress
import nick.dev.gorillalang.models.RemoteWordsProgress
import nick.dev.gorillalang.models.WordRemote
import nick.dev.gorillalang.remote.RemoteLanguageDatabase

class LanguageRepository(
    val db: LanguageDatabase,
    val rdb :RemoteLanguageDatabase

    )
{
    fun getUserModules() = db.getLanguageDao().getAllModules()
    suspend fun deleteModule(moduleRemote:ModuleRemote) = db.getLanguageDao().deleteModule(moduleRemote)
    suspend fun upsertModule(moduleRemote: ModuleRemote)=db.getLanguageDao().upsertModule(moduleRemote)

    suspend fun upsertWord(wordRemote: WordRemote) = db.getLanguageDao().upsertWord(wordRemote)

    fun getModuleWithWords(moduleId:String) = db.getLanguageDao().getModuleWithWords(moduleId)
    suspend fun deleteWord(wordRemote: WordRemote) = db.getLanguageDao().deleteWord(wordRemote)

    fun getPublicModules() = rdb.getModules()
    fun getWordsByRemoteModule(moduleRemote:ModuleRemote) = rdb.getWordsByModule(moduleRemote)

    suspend fun upsertRemoteWordsProgress(progress: RemoteWordsProgress)
    = db.getLanguageDao().upsertRemoteWordsProgress(progress)

    fun getRemoteProgressById(id:String)
    =db.getLanguageDao().getRemoteProgressById(id)


    suspend fun upsertRemoteModuleProgress(progress: RemoteModuleProgress)
            = db.getLanguageDao().upsertRemoteModulesProgress(progress)

    fun getRemoteModuleProgressById(id:String)
            =db.getLanguageDao().getRemoteModuleProgressById(id)
}