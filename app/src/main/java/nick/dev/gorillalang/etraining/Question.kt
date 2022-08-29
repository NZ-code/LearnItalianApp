package nick.dev.gorillalang.etraining

abstract class Question{
    open var rightAnswerId:Int = -1
    abstract var type :String
    abstract var progressVal:Int
    var isLast = false
    companion object{
        val TYPE_NONE = "NONE"
        val TYPE_QUIZ = "QUIZ"
        val TYPE_MATCH = "MATCH"
        val TYPE_WRITING = "WRITING"
    }

}