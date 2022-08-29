package nick.dev.gorillalang.ui.fragments.training

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import nick.dev.gorillalang.R
import nick.dev.gorillalang.databinding.FragmentQuizBinding
import nick.dev.gorillalang.etraining.QuizQuestion
import nick.dev.gorillalang.ui.TrainingActivity
import nick.dev.gorillalang.ui.viewModels.LanguageViewModel

class QuizFragment(val question: QuizQuestion):Fragment(R.layout.fragment_quiz) {

    private var isPicked = false
    private lateinit var binding: FragmentQuizBinding
    //lateinit var languageViewModel: LanguageViewModel
    private var pickedOption = -1
    lateinit var trainingActivity : TrainingActivity
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        trainingActivity = activity as TrainingActivity
        //languageViewModel =trainingActivity.languageViewModel
        binding = FragmentQuizBinding.bind(view)

        trainingActivity.playWord(question.question)
        setSetup()
    }
    fun setSetup(){

        binding.tvWord.text = question.question
        println(question.options.toString())

        binding.tvOptionOne.apply {
            text = question.options[0]
            setOnClickListener {
                pickedOption = 0
                onPick(it as TextView)
            }
        }
        binding.ivPlay.setOnClickListener{
            trainingActivity.playWord(question.question)
        }
        binding.tvOptionTwo.apply {
            text = question.options[1]
            setOnClickListener {
                pickedOption = 1
                onPick(it as TextView)
            }
        }
        binding.tvOptionThree.apply {
            text = question.options[2]
            setOnClickListener {
                pickedOption = 2
                onPick(it as TextView)
            }
        }
        binding.tvOptionFour.apply {
            text = question.options[3]
            setOnClickListener {
                pickedOption = 3
                onPick(it as TextView)
            }
        }
        binding.btnSubmit.setOnClickListener {

            if(!isPicked){
                if(pickedOption != -1){
                    val goodAnswer = question.rightAnswerId
                    showAnswer(goodAnswer)
                    if(goodAnswer == pickedOption){
                        trainingActivity.updateScore(true)

                    }
                    else{
                        trainingActivity.updateScore(false)
                    }

                    isPicked = true
                    buttonPressedOnce(it as Button)

                }
                else{

                }
            }
            else{
                if(!question.isLast){
                    trainingActivity.nextQuestion()
                }
                else{
                    trainingActivity.showResults()
                }


            }

        }


    }
    fun buttonPressedOnce(imageButton: Button) {
        imageButton.apply {
            if(question.isLast){
                text = "FINISH"
            }
            else{
                text = "NEXT"
            }

        }
    }
    fun onPick(view: TextView){
        if(!isPicked){
            setDefaulViews()
            view.quizPicked()
        }

    }
    fun showAnswer(rightAnswerId:Int){
        when(pickedOption){
            0-> binding.tvOptionOne.quizBadAnswer()
            1-> binding.tvOptionTwo.quizBadAnswer()
            2-> binding.tvOptionThree.quizBadAnswer()
            3-> binding.tvOptionFour.quizBadAnswer()
            else->{

            }
        }
        when(rightAnswerId){
            0-> binding.tvOptionOne.quizGoodAnswer()
            1-> binding.tvOptionTwo.quizGoodAnswer()
            2-> binding.tvOptionThree.quizGoodAnswer()
            3-> binding.tvOptionFour.quizGoodAnswer()
            else->{

            }
        }

    }
    fun TextView.quizDefault(){
        setBackgroundColor(Color.WHITE)
        setTextColor(Color.BLACK)
    }
    fun TextView.quizPicked(){
        setBackgroundColor(Color.GRAY)
        setTextColor(Color.WHITE)
    }
    fun TextView.quizGoodAnswer(){
        setBackgroundColor(Color.GREEN)
        setTextColor(Color.WHITE)
    }
    fun TextView.quizBadAnswer(){
        setBackgroundColor(Color.RED)
        setTextColor(Color.WHITE)
    }
    fun setDefaulViews(){

        binding.tvOptionOne.apply {
            quizDefault()
        }
        binding.tvOptionTwo.apply {
           quizDefault()
        }
        binding.tvOptionThree.apply {
            quizDefault()
        }
        binding.tvOptionFour.apply {
            quizDefault()
        }
    }


}