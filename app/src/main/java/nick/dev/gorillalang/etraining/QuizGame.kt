package nick.dev.gorillalang.etraining

import nick.dev.gorillalang.models.WordRemote

class QuizGame(val wordRemotes:List<WordRemote>){
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

        val quizNum = 3
        val matchNum = 2
        val writingNum = 1
        gameQuestionsNum = quizNum+matchNum+writingNum
        val quizWords = wordRemotes.shuffled().takeLast(quizNum)


        for(quizWord in quizWords){
            questions.add(QuizQuestion(quizWord, wordRemotes))
        }
        repeat(matchNum){
            questions.add(MatchQuestion(wordRemotes))
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
    fun updateProgress(wordRemote:WordRemote, progress:Int){

    }
}