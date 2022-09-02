package nick.dev.gorillalang.etraining

import nick.dev.gorillalang.models.WordRemote

class QuizQuestion(val questionWordRemote:WordRemote, val wordRemotes:List<WordRemote>
):Question(){

    val question:String = questionWordRemote.moduleLearnLang
    val options:List<String> = (wordRemotes.shuffled().filter { it.moduleLearnLang != question }
        .takeLast(3).map { it->it.moduleUserLang  } + questionWordRemote.moduleUserLang).shuffled()
    val rightAnswer = questionWordRemote.moduleUserLang
    override var rightAnswerId: Int = options.indexOf(questionWordRemote.moduleUserLang)
    override var type = Question.TYPE_QUIZ
    override var progressVal: Int = 2
}