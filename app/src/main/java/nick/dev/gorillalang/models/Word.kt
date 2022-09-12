package nick.dev.gorillalang.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(
    tableName = "words_remote"
)

data class Word  (
    val moduleUserLang:String,
    val moduleLearnLang:String,
    var moduleId: String,
    val isRemote:Boolean = false,
    @PrimaryKey(autoGenerate = false)
    val remoteId:String,
    var progress: Int = 0
):Serializable
