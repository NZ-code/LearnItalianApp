package nick.dev.gorillalang.ui.fragments.training

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import nick.dev.gorillalang.R
import nick.dev.gorillalang.databinding.FragmentMatchBinding
import nick.dev.gorillalang.etraining.MatchQuestion
import nick.dev.gorillalang.ui.TrainingActivity
import nick.dev.gorillalang.ui.viewModels.TrainingViewModel

class MatchFragment(val matchQuestion: MatchQuestion) :Fragment(R.layout.fragment_match) {

    // is right or left option currently picked
    private var leftPicked = false
    private var rightPicked = false

    // id of picked options started with 0
    private var leftPickedId = -1
    private var rightPickedId = -1

    // is User ever did mistake in this exercise
    private var wasNoMistake = true

    // viewBinding
    private lateinit var binding: FragmentMatchBinding
    // viewModel
    lateinit var trainingViewModel: TrainingViewModel

    // left and right  Options
    lateinit var leftTextViews:List<TextView>
    lateinit var rightTextViews: List<TextView>

    // options that already checked
    var leftFinishedIds = mutableListOf<Int>()
    var rightFinishedIds = mutableListOf<Int>()

    // top activity
    lateinit var trainingActivity : TrainingActivity
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // init activity and viewModel
        trainingActivity = activity as TrainingActivity
        trainingViewModel =trainingActivity.trainingViewModel

        // view binding
        binding = FragmentMatchBinding.bind(view)

        // setting init button
        setButtonStart()




        setOptionsStart()

        // set options value and onclick listeners
        fillOptions()
    }

    private fun setOptionsStart(){
        // set options as variables
        leftTextViews = listOf<TextView>(
            binding.tvOptionOneLeft, binding.tvOptionTwoLeft, binding.tvOptionThreeLeft,binding.tvOptionFourLeft
        )
        rightTextViews = listOf<TextView>(
            binding.tvOptionOneRight, binding.tvOptionTwoRight, binding.tvOptionThreeRight,binding.tvOptionFourRight
        )
    }
    private fun setButtonStart(){
        // setting button text
        if(matchQuestion.isLast){
            binding.btnSubmit.text = "FINISH"

        }
        else{
            binding.btnSubmit.text = "NEXT"
        }
        binding.btnSubmit.visibility = View.GONE
    }

    private fun checkAnswer(){
        val isGood = matchQuestion.checkIsRight(leftPickedId,rightPickedId)

        if(isGood){
            checkAnswerGood()
        }
        else{
            checkAnswerBad()
        }

    }

    private fun checkAnswerBad() {

        wasNoMistake = false
        leftTextViews[leftPickedId].optionBadAnswer()
        rightTextViews[rightPickedId].optionBadAnswer()
        leftPickedId = -1
        rightPickedId = -1
    }

    private fun checkAnswerGood(){
        leftTextViews[leftPickedId].optionGoodAnswer()
        rightTextViews[rightPickedId].optionGoodAnswer()
        leftFinishedIds.add(leftPickedId)
        rightFinishedIds.add(rightPickedId)
        leftPickedId = -1
        rightPickedId = -1
        val optionNumber = 4
        if(leftFinishedIds.size == optionNumber && rightFinishedIds.size == optionNumber){
            binding.btnSubmit.visibility = View.VISIBLE
            binding.btnSubmit.setOnClickListener{
                onLastAnswer()
            }
        }
    }
    private fun onLastAnswer(){
        // updating score
        trainingActivity.updateScore(wasNoMistake)
        if(wasNoMistake){
            // adding to update word progress
            trainingViewModel.addGoodAnsweredQuestion(matchQuestion)
        }
        if(matchQuestion.isLast){
            trainingActivity.finishQuiz()
        }
        else{
            trainingActivity.nextQuestion()

        }
    }

    private fun onPickRight(view: TextView){
        if(!leftPicked){
            setDefaultViewsLeft()
        }
        setDefaultViewsRight()
        view.optionPicked()
        rightPicked = true

        if(leftPicked){
            leftPicked = false
            rightPicked = false
            checkAnswer()
        }
    }
    private fun onPickLeft(view: TextView){
        trainingActivity.playWord(view.text.toString())
        if(!rightPicked){
            setDefaultViewsRight()
        }
        setDefaultViewsLeft()
        view.optionPicked()
        leftPicked = true
        if(rightPicked){
            leftPicked = false
            rightPicked = false
            checkAnswer()
        }
    }
    private fun TextView.optionDefault(){
        setBackgroundColor(Color.WHITE)
        setTextColor(Color.BLACK)
    }
    private fun TextView.optionPicked(){
        setBackgroundColor(Color.GRAY)
        setTextColor(Color.WHITE)
    }
    private fun TextView.optionGoodAnswer(){
        setBackgroundColor(Color.GREEN)
        setTextColor(Color.WHITE)
        setOnClickListener(null)
    }
    private fun TextView.optionBadAnswer(){
        setBackgroundColor(Color.RED)
        setTextColor(Color.WHITE)
    }
    private fun setDefaultViewsRight(){
        for(i in rightTextViews.indices){
            if(!rightFinishedIds.contains(i)) {
                rightTextViews[i].apply {
                    this.optionDefault()
                }
            }
        }
    }
    private fun setDefaultViewsLeft(){
        for(i in leftTextViews.indices){
            if(!leftFinishedIds.contains(i)){
                leftTextViews[i].apply {
                    this.optionDefault()
                }
            }

        }
    }
    private fun fillOptions( ){
        // left options
        for(i in leftTextViews.indices){
            leftTextViews[i].apply {
                // option value
                text = matchQuestion.optionsLeft[i]
                // option clicked
                setOnClickListener {
                    leftPickedId = i
                    onPickLeft(this)
                }
            }
        }
        // right options
        for(i in rightTextViews.indices){
            rightTextViews[i].apply {
                text = matchQuestion.optionsRight[i]
                setOnClickListener {
                    rightPickedId = i
                    onPickRight(this)
                }
            }
        }



    }

}