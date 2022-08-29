package nick.dev.gorillalang.etraining

import nick.dev.gorillalang.models.Word
import kotlin.random.Random

class QuizGame(val words:List<Word>){
    var gameQuestionsNum:Int = 0
    private val questions = mutableListOf<Question>()
    val wasRightList= mutableListOf<Question>()
    var currentQuestionId = 0
    var isReady = false
    var score = 0

    init {
        createGame()
    }

    fun createGame(){
        score = 0

        // words for the quiz

        val quizNum = 6
        val matchNum = 3
        val writingNum = 2
        gameQuestionsNum = quizNum+matchNum+writingNum
        val quizWords = words.shuffled().takeLast(quizNum)


        for(quizWord in quizWords){
            questions.add(QuizQuestion(quizWord, words))
        }
        repeat(matchNum){
            questions.add(MatchQuestion(words))
        }
        questions.shuffled()

        val writingWords = quizWords.takeLast(writingNum)

        for(word in writingWords){
            questions.add(WritingQuestion(word))
        }


        questions.last().isLast = true
        isReady = true
    }
    fun changeScore(wasRight:Boolean){
        if(wasRight){
            score++
            wasRightList.add(getCurrentQuestion())
        }

    }
    fun getCurrentQuestion():Question{
        return questions[currentQuestionId]
    }
    fun nextQuestion(){
        currentQuestionId++
    }
    fun updateProgress(word:Word, progress:Int){

    }
}