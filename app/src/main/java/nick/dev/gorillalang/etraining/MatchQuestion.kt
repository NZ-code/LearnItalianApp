package nick.dev.gorillalang.etraining

import android.util.Log
import nick.dev.gorillalang.models.Word

class MatchQuestion(val words:List<Word>):Question(){

    val wordsMatching = words.shuffled().takeLast(4)
    val optionsLeft = wordsMatching.map {
        it.moduleLearnLang
    }
    val optionsRight = wordsMatching.map {
        it.moduleUserLang
    }.shuffled()

    fun checkIsRight(idLeft:Int, idRight:Int):Boolean{
        if(wordsMatching[idLeft].moduleUserLang  == optionsRight[idRight]) return true
        return false
    }
    override var type = Question.TYPE_MATCH
    override var progressVal: Int = 1

}