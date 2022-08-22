package nick.dev.gorillalang.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(
    tableName = "modules"
)
data class Module(
    val moduleName:String
):Serializable{
    @PrimaryKey(autoGenerate = true)
    var id:Int = 0
}
