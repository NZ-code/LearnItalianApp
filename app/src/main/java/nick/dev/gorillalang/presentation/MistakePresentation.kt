package nick.dev.gorillalang.presentation

import nick.dev.gorillalang.models.Mistake
import nick.dev.gorillalang.models.Word

data class MistakePresentation(
    val mistake: Mistake,
    val word: Word
)