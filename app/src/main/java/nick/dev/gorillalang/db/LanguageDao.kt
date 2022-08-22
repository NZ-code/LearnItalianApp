package nick.dev.gorillalang.db

import androidx.lifecycle.LiveData
import androidx.room.*
import nick.dev.gorillalang.models.Module

@Dao
interface LanguageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertModule(module: Module):Long
    @Query("SELECT * FROM modules")
    fun getAllModules():LiveData<List<Module>>
    @Delete
    suspend fun deleteModule(module: Module)
}