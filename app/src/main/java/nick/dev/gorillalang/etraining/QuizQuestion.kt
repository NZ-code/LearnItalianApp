package nick.dev.gorillalang.etraining

import nick.dev.gorillalang.models.Word

class QuizQuestion(val questionWord:Word, val words:List<Word>
):Question(){

    val question:String = questionWord.moduleLearnLang
    val options:List<String> = (words.shuffled().filter { it.moduleLearnLang != question }
        .takeLast(3).map { it->it.moduleUserLang  } + questionWord.moduleUserLang).shuffled()
    val rightAnswer = questionWord.moduleUserLang
    override var rightAnswerId: Int = options.indexOf(questionWord.moduleUserLang)
    override var type = Question.TYPE_QUIZ
    override var progressVal: Int = 2
}