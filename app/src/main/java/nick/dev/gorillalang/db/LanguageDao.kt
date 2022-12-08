package nick.dev.gorillalang.db

import androidx.lifecycle.LiveData
import androidx.room.*
import nick.dev.gorillalang.models.*
import nick.dev.gorillalang.models.relations.ModuleWithWordsRemote


@Dao
interface LanguageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertModule(module: Module):Long
    @Query("SELECT * FROM modules_remote")
    fun getAllModules():LiveData<List<Module>>
    @Delete
    suspend fun deleteModule(module: Module)


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertMistake(mistake: Mistake):Long
    @Query("SELECT * FROM mistakes")
    fun getAllMistakes():LiveData<List<Mistake>>
    @Delete
    suspend fun deleteMistake(mistake: Mistake)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertWord(word: Word):Long

    @Query("select * from words_remote where remoteId=:id")
    fun getWordById(id:String):LiveData<Word>

    @Transaction
    @Query("select * from modules_remote where remoteId = :moduleId")
    fun getModuleWithWords(moduleId:String):LiveData<List<ModuleWithWordsRemote>>

    @Delete
    suspend fun deleteWord(word: Word)



    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertRemoteWordsProgress(progress: RemoteWordsProgress):Long

    @Query("SELECT * FROM remoteWordsProgress where remoteWordId = :id")
    fun getRemoteWordProgress(id:String):List<RemoteWordsProgress>



    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertRemoteModulesProgress(progress: RemoteModuleProgress):Long

    @Query("SELECT * FROM remoteModulesProgress where remoteModuleId = :id")
    fun getRemoteModuleProgressById(id:String):LiveData<List<RemoteModuleProgress>>

}