package nick.dev.gorillalang.db

import androidx.lifecycle.LiveData
import androidx.room.*
import nick.dev.gorillalang.models.ModuleRemote
import nick.dev.gorillalang.models.RemoteModuleProgress
import nick.dev.gorillalang.models.RemoteWordsProgress
import nick.dev.gorillalang.models.WordRemote
import nick.dev.gorillalang.models.relations.ModuleWithWordsRemote


@Dao
interface LanguageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertModule(moduleRemote: ModuleRemote):Long
    @Query("SELECT * FROM modules_remote")
    fun getAllModules():LiveData<List<ModuleRemote>>
    @Delete
    suspend fun deleteModule(moduleRemote: ModuleRemote)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertWord(wordRemote: WordRemote):Long

    @Transaction
    @Query("select * from modules_remote where remoteId = :moduleId")
    fun getModuleWithWords(moduleId:String):LiveData<List<ModuleWithWordsRemote>>

    @Delete
    suspend fun deleteWord(wordRemote: WordRemote)



    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertRemoteWordsProgress(progress: RemoteWordsProgress):Long

    @Query("SELECT * FROM remoteWordsProgress where remoteWordId = :id")
    fun getRemoteProgressById(id:String):LiveData<List<RemoteWordsProgress>>



    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertRemoteModulesProgress(progress: RemoteModuleProgress):Long

    @Query("SELECT * FROM remoteModulesProgress where remoteModuleId = :id")
    fun getRemoteModuleProgressById(id:String):LiveData<List<RemoteModuleProgress>>

}