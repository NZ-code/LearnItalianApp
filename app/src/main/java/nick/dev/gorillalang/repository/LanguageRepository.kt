package nick.dev.gorillalang.repository

import androidx.lifecycle.LiveData
import nick.dev.gorillalang.db.LanguageDatabase
import nick.dev.gorillalang.models.Module
import nick.dev.gorillalang.models.Word

class LanguageRepository(
    val db: LanguageDatabase
    )
{
    fun getUserModules() = db.getLanguageDao().getAllModules()
    suspend fun deleteModule(module:Module) = db.getLanguageDao().deleteModule(module)
    suspend fun upsertModule(module: Module)=db.getLanguageDao().upsertModule(module)

    suspend fun upsertWord(word: Word) = db.getLanguageDao().upsertWord(word)

    fun getModuleWithWords(moduleId:Int) = db.getLanguageDao().getModuleWithWords(moduleId)
    suspend fun deleteWord(word: Word) = db.getLanguageDao().deleteWord(word)
}