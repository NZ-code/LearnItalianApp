package nick.dev.gorillalang.models.relations

import androidx.room.Embedded
import androidx.room.Relation
import nick.dev.gorillalang.models.Module
import nick.dev.gorillalang.models.Word

data class ModuleWithWordsRemote(
    @Embedded val module:Module,
    @Relation(
        parentColumn = "remoteId",
        entityColumn = "moduleId"
    )
    val words:List<Word>
)
