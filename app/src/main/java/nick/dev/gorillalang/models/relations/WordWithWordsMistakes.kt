package nick.dev.gorillalang.models.relations

import androidx.room.Embedded
import androidx.room.Relation
import nick.dev.gorillalang.models.Mistake
import nick.dev.gorillalang.models.Word

data class WordWithWordsMistakes(
    @Embedded val word:Word,
    @Relation(
        parentColumn = "remoteId",
        entityColumn = "wordId"
    )
    val mistakes:List<Mistake>
)
