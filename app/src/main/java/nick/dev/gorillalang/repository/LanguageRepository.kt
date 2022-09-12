package nick.dev.gorillalang.repository

import nick.dev.gorillalang.db.LanguageDatabase
import nick.dev.gorillalang.models.*
import nick.dev.gorillalang.remote.RemoteLanguageDatabase

class LanguageRepository(
    val db: LanguageDatabase,
    val rdb :RemoteLanguageDatabase

    )
{
    fun getUserModules() = db.getLanguageDao().getAllModules()
    suspend fun deleteModule(module:Module) = db.getLanguageDao().deleteModule(module)
    suspend fun upsertModule(module: Module)=db.getLanguageDao().upsertModule(module)

    fun getMistakes() = db.getLanguageDao().getAllMistakes()
    suspend fun deleteMistake(mistake:Mistake) = db.getLanguageDao().deleteMistake(mistake)
    suspend fun upsertMistake(mistake: Mistake)=db.getLanguageDao().upsertMistake(mistake)

    suspend fun upsertWord(word: Word) = db.getLanguageDao().upsertWord(word)

    fun getModuleWithWords(moduleId:String) = db.getLanguageDao().getModuleWithWords(moduleId)
    suspend fun deleteWord(word: Word) = db.getLanguageDao().deleteWord(word)

    fun getPublicModules() = rdb.getModules()
    fun getWordsByRemoteModule(module:Module) = rdb.getWordsByModule(module)

    suspend fun upsertRemoteWordsProgress(progress: RemoteWordsProgress)
    = db.getLanguageDao().upsertRemoteWordsProgress(progress)

    fun getRemoteProgressById(id:String)
    =db.getLanguageDao().getRemoteWordProgress(id)


    suspend fun upsertRemoteModuleProgress(progress: RemoteModuleProgress)
            = db.getLanguageDao().upsertRemoteModulesProgress(progress)

    fun getRemoteModuleProgressById(id:String)
            =db.getLanguageDao().getRemoteModuleProgressById(id)

    fun getWordById(wordId: String) = db.getLanguageDao().getWordById(wordId)


}