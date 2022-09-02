package nick.dev.gorillalang.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(
    tableName = "remoteModulesProgress"
)
data class RemoteModuleProgress(
    @PrimaryKey(autoGenerate = false)
    var remoteModuleId:String,
    val level:Int
):Serializable{

}
