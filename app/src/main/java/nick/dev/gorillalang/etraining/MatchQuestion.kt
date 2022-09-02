package nick.dev.gorillalang.etraining

import nick.dev.gorillalang.models.WordRemote

class MatchQuestion(val wordRemotes:List<WordRemote>):Question(){

    val wordsMatching = wordRemotes.shuffled().takeLast(4)
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