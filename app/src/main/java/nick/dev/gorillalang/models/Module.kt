package nick.dev.gorillalang.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(
    tableName = "modules"
)
data class Module(
    @PrimaryKey(autoGenerate = true)
    val id:Int?=null,
    val moduleName:String?
):Serializable
