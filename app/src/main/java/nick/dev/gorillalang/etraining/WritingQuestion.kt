package nick.dev.gorillalang.etraining

import android.util.Log
import nick.dev.gorillalang.models.Word
import java.util.*

class WritingQuestion(val questionWord:Word):Question(){

    val question:String = questionWord.moduleUserLang.lowercase()
    val initLetters:String = questionWord.moduleLearnLang.toMutableList().shuffled(Random()).joinToString("").lowercase()
    //toMutableList().also { }.let{String(it)}
    val rightAnswer = questionWord.moduleLearnLang.lowercase()
    override var type = Question.TYPE_WRITING
    override var progressVal: Int = 3

}