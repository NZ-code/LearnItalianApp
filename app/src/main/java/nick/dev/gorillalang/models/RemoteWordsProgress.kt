package nick.dev.gorillalang.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(
    tableName = "remoteWordsProgress"
)
data class RemoteWordsProgress(
    @PrimaryKey(autoGenerate = false)
    var remoteWordId:String,
    val level:Int
):Serializable{

}
