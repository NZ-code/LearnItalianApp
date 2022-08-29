package nick.dev.gorillalang.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(
    tableName = "words"
)
data class Word(
    val moduleUserLang:String,
    val moduleLearnLang:String,
    val moduleId: Int,
    val isRemote:Boolean = false,
    val remoteId:String
):Serializable{
    @PrimaryKey(autoGenerate = true)
    var id:Int = 0
}
