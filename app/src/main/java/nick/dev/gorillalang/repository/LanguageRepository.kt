package nick.dev.gorillalang.repository

import androidx.lifecycle.LiveData
import nick.dev.gorillalang.db.LanguageDatabase
import nick.dev.gorillalang.models.Module

class LanguageRepository(
    val db: LanguageDatabase
    )
{
    fun getUserModules() = db.getLanguageDao().getAllModules()
    suspend fun deleteModule(module:Module) = db.getLanguageDao().deleteModule(module)
    suspend fun upsertModule(module: Module)=db.getLanguageDao().upsertModule(module)


}