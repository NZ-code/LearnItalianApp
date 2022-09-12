package nick.dev.gorillalang.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(
    tableName = "mistakes"
)

data class Mistake  (
    @PrimaryKey(autoGenerate = false)
    val remoteId:String,
    val mistakeText: String,
    val wordId: String
):Serializable
