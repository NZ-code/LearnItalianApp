package nick.dev.gorillalang.etraining

import nick.dev.gorillalang.models.WordRemote
import java.util.*

class WritingQuestion(val questionWordRemote:WordRemote):Question(){

    val question:String = questionWordRemote.moduleUserLang.lowercase()
    val initLetters:String = questionWordRemote.moduleLearnLang.toMutableList().shuffled(Random()).joinToString("").lowercase()
    //toMutableList().also { }.let{String(it)}
    val rightAnswer = questionWordRemote.moduleLearnLang.lowercase()
    override var type = Question.TYPE_WRITING
    override var progressVal: Int = 3

}