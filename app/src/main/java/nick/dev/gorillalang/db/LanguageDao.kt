package nick.dev.gorillalang.db

import androidx.lifecycle.LiveData
import androidx.room.*
import nick.dev.gorillalang.models.Module
import nick.dev.gorillalang.models.Word
import nick.dev.gorillalang.models.relations.ModuleWithWords

@Dao
interface LanguageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertModule(module: Module):Long
    @Query("SELECT * FROM modules")
    fun getAllModules():LiveData<List<Module>>
    @Delete
    suspend fun deleteModule(module: Module)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertWord(word: Word):Long

    @Transaction
    @Query("select * from modules where id = :moduleId")
    fun getModuleWithWords(moduleId:Int):LiveData<List<ModuleWithWords>>

    @Delete
    suspend fun deleteWord(word: Word)

}