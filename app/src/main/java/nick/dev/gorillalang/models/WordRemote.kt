package nick.dev.gorillalang.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(
    tableName = "words_remote"
)

data class WordRemote  (
    val moduleUserLang:String,
    val moduleLearnLang:String,
    val moduleId: String,
    val isRemote:Boolean = false,
    @PrimaryKey(autoGenerate = false)
    val remoteId:String
):Serializable
