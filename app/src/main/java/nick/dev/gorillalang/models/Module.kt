package nick.dev.gorillalang.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(
    tableName = "modules_remote"
)
data class Module(
    val moduleName:String,
    val isRemote:Boolean = false,
    @PrimaryKey(autoGenerate = false)
    val remoteId: String
):Serializable{


}
