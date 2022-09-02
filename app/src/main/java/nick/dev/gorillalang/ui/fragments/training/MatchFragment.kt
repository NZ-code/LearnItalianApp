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
import nick.dev.gorillalang.ui.viewModels.LanguageViewModel
import nick.dev.gorillalang.ui.viewModels.TrainingViewModel

class MatchFragment(val matchQuestion: MatchQuestion) :Fragment(R.layout.fragment_match) {

    private var leftPicked = false
    private var rightPicked = false
    private var leftPickedId = -1
    private var rightPickedId = -1
    private var wasNoMistake = true
    private lateinit var binding: FragmentMatchBinding
    lateinit var trainingViewModel: TrainingViewModel
    lateinit var leftTextViews:List<TextView>
    lateinit var rightTextViews: List<TextView>
    var leftFinishedIds = mutableListOf<Int>()
    var rightFinishedIds = mutableListOf<Int>()
    lateinit var trainingActivity : TrainingActivity
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        trainingViewModel =(activity as TrainingActivity).trainingViewModel
        trainingActivity = activity as TrainingActivity
        binding = FragmentMatchBinding.bind(view)
        if(matchQuestion.isLast){
            binding.btnSubmit.text = "FINISH"

        }
        else{
            binding.btnSubmit.text = "NEXT"

        }


        binding.btnSubmit.visibility = View.GONE



        leftTextViews = listOf<TextView>(
            binding.tvOptionOneLeft, binding.tvOptionTwoLeft, binding.tvOptionThreeLeft,binding.tvOptionFourLeft
        )
        rightTextViews = listOf<TextView>(
            binding.tvOptionOneRight, binding.tvOptionTwoRight, binding.tvOptionThreeRight,binding.tvOptionFourRight
        )
        setSetup()
    }
    fun checkAnswer(){
        val isGood = matchQuestion.checkIsRight(leftPickedId,rightPickedId)

        if(isGood){


            leftTextViews[leftPickedId].optionGoodAnswer()
            rightTextViews[rightPickedId].optionGoodAnswer()
            leftFinishedIds.add(leftPickedId)
            rightFinishedIds.add(rightPickedId)
            leftPickedId = -1
            rightPickedId = -1
            if(leftFinishedIds.size == 4 && rightFinishedIds.size == 4){
                binding.btnSubmit.visibility = View.VISIBLE
                binding.btnSubmit.setOnClickListener{
                    trainingActivity.updateScore(wasNoMistake)
                    if(wasNoMistake){
                        trainingViewModel.addGoodAnsweredQuestion(matchQuestion)
                    }
                    if(matchQuestion.isLast){

                        trainingActivity.showResults()
                        Log.d("Bl","show")
                    }
                    else{

                        trainingActivity.nextQuestion()
                        Log.d("Bl","next")
                    }
                }
            }
        }
        else{
            wasNoMistake = false

            leftTextViews[leftPickedId].optionBadAnswer()
            rightTextViews[rightPickedId].optionBadAnswer()
            leftPickedId = -1
            rightPickedId = -1
        }

    }
    fun onPickRight(view: TextView){
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
    fun onPickLeft(view: TextView){
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
    fun TextView.optionDefault(){
        setBackgroundColor(Color.WHITE)
        setTextColor(Color.BLACK)
    }
    fun TextView.optionPicked(){
        setBackgroundColor(Color.GRAY)
        setTextColor(Color.WHITE)
    }
    fun TextView.optionGoodAnswer(){
        setBackgroundColor(Color.GREEN)
        setTextColor(Color.WHITE)
        setOnClickListener(null)
    }
    fun TextView.optionBadAnswer(){
        setBackgroundColor(Color.RED)
        setTextColor(Color.WHITE)
    }
    fun setDefaultViewsRight(){
        for(i in rightTextViews.indices){
            if(!rightFinishedIds.contains(i)) {
                rightTextViews[i].apply {
                    this.optionDefault()
                }
            }
        }
    }
    fun setDefaultViewsLeft(){
        for(i in leftTextViews.indices){
            if(!leftFinishedIds.contains(i)){
                leftTextViews[i].apply {
                    this.optionDefault()
                }
            }

        }
    }
    fun setSetup( ){
        for(i in leftTextViews.indices){
            leftTextViews[i].apply {
                text = matchQuestion.optionsLeft[i]
                setOnClickListener {
                    leftPickedId = i
                    onPickLeft(this)
                }
            }
        }
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