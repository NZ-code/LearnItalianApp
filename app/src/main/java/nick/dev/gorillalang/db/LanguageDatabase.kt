package nick.dev.gorillalang.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import nick.dev.gorillalang.models.*


@Database(
    entities =[Module::class, Word::class,RemoteWordsProgress::class,RemoteModuleProgress::class,
                Mistake::class
              ],

    version = 2
)
abstract class LanguageDatabase: RoomDatabase(){
    abstract fun getLanguageDao(): LanguageDao
    companion object{
        @Volatile
        private var instance: LanguageDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK){
            instance ?: createDatabase(context).also{
                instance = it
            }
        }

        private fun createDatabase(context:Context) =
            Room.databaseBuilder(
                context.applicationContext,
                LanguageDatabase::class.java,
                "language_db.db"
            ).build()
    }
}