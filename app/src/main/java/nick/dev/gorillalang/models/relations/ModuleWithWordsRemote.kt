package nick.dev.gorillalang.models.relations

import androidx.room.Embedded
import androidx.room.Relation
import nick.dev.gorillalang.models.ModuleRemote
import nick.dev.gorillalang.models.WordRemote

data class ModuleWithWordsRemote(
    @Embedded val moduleRemote:ModuleRemote,
    @Relation(
        parentColumn = "remoteId",
        entityColumn = "moduleId"
    )
    val wordRemotes:List<WordRemote>
)
