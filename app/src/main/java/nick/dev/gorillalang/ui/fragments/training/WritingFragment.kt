package nick.dev.gorillalang.ui.fragments.training

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.*
import androidx.core.widget.TextViewCompat
import androidx.fragment.app.Fragment
import nick.dev.gorillalang.R
import nick.dev.gorillalang.databinding.FragmentWritingBinding
import nick.dev.gorillalang.etraining.WritingQuestion
import nick.dev.gorillalang.ui.TrainingActivity
import nick.dev.gorillalang.ui.viewModels.LanguageViewModel
import kotlin.math.max
import kotlin.math.min


class WritingFragment(val writingQuestion: WritingQuestion):Fragment(R.layout.fragment_writing) {


    private lateinit var binding: FragmentWritingBinding
    lateinit var languageViewModel: LanguageViewModel
    private var currentPosition = 0
    private val blankViews = mutableListOf<TextView>()
    private val lettersViews = mutableListOf<TextView>()
    private val isNotEmptyViewList = mutableListOf<Boolean>()
    private val positionsLettersToBlank = mutableMapOf<Int,Int>()
    private lateinit var trainingActivity :TrainingActivity
    private var isChecked = false
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        languageViewModel = (activity as TrainingActivity).languageViewModel
        trainingActivity = activity as TrainingActivity
        binding = FragmentWritingBinding.bind(view)


        setSetup()
    }

    val tableParams = TableLayout.LayoutParams(
        TableLayout.LayoutParams.WRAP_CONTENT,
        TableLayout.LayoutParams.WRAP_CONTENT
    )

    fun setSetup() {
        val question = writingQuestion.question
        val answer = writingQuestion.rightAnswer
        binding.tvWord.text = question
        println(writingQuestion.initLetters)
        println(println(writingQuestion.initLetters.length))

        setLetters(writingQuestion.initLetters)
        setBlank(writingQuestion.initLetters)
        binding.btnSubmit.visibility = View.GONE
        binding.btnSubmit.text ="CHECK"
        binding.btnSubmit.setOnClickListener{
            if(isChecked){
                if(writingQuestion.isLast){
                    trainingActivity.showResults()

                }
                else{
                    trainingActivity.nextQuestion()

                }
            }
            else{
                isChecked = true
                if(writingQuestion.rightAnswer == blankViews.joinToString("") { it.text }){
                    binding.tvAnswer.text = "Great"
                    trainingActivity.updateScore(true)

                }
                else{
                    binding.tvAnswer.text = "Wrong!\n Answer:${writingQuestion.rightAnswer}"
                    trainingActivity.playWord(writingQuestion.rightAnswer)
                    trainingActivity.updateScore(false)
                }
                if(writingQuestion.isLast){
                    binding.btnSubmit.text = "FINISH"

                }
                else{
                    binding.btnSubmit.text = "NEXT"
                }
            }




        }
    }

    fun setLetters(letters: String) {

        // params for TableView
        val layoutParams =
            LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ) // assuming the parent view is a LinearLayout
        layoutParams.topMargin = 100
        binding.tlLetters.layoutParams = layoutParams


        // Params for TableRow
        val rowParams = TableRow.LayoutParams(
            TableRow.LayoutParams.WRAP_CONTENT,
            TableRow.LayoutParams.WRAP_CONTENT
        )
        val margin = 50
        rowParams.setMargins(0, margin, 0, margin)


        // Params for TextView
        val textParams = TableRow.LayoutParams(
            TableRow.LayoutParams.WRAP_CONTENT,
            TableRow.LayoutParams.WRAP_CONTENT
        )
        textParams.setMargins(10,10,10,10)

        val lettersInRow = 7

        val rowNumber = (letters.length / lettersInRow) + 1
        for (j in (0 until rowNumber)) {
            val tableRow = TableRow(context)
            tableRow.layoutParams = rowParams// TableLayout is the parent view

            val lettersLeft = max(letters.length - j * lettersInRow, 0)
            for (i in (0 until min(lettersInRow, lettersLeft))) {
                val textView = TextView(context)
                val index = lettersInRow * (j) + i
                textView.text = letters[index].toString()
                TextViewCompat.setTextAppearance(textView, R.style.WritingText)
                val coeff = 1.5
                textView.minHeight = (textView.textSize * coeff).toInt()
                textView.minWidth = (textView.textSize * coeff).toInt()
                textView.gravity = Gravity.CENTER
                textView.layoutParams = textParams // TableRow is the parent view

                textView.setOnClickListener{
                    onLetterClick(it,index)
                }

                textView.setBackgroundColor(Color.GRAY)
                tableRow.addView(textView)
                lettersViews.add(textView)

            }
            binding.tlLetters.addView(tableRow)
        }
    }
    fun onLetterClick(letter:View,position:Int){
        if(!isChecked){
            letter.visibility = View.GONE
            val firstEmptyId = isNotEmptyViewList.indexOf(false)
            isNotEmptyViewList[firstEmptyId] = true
            blankViews[firstEmptyId].text = (letter as TextView).text
            positionsLettersToBlank[firstEmptyId] = position
            if(isNotEmptyViewList.reduce{b1, b2 -> b1 and b2}){
                binding.btnSubmit.visibility = View.VISIBLE

            }
        }

    }
    fun setBlank(letters: String) {

        // params for TableView
        val layoutParams =
            LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ) // assuming the parent view is a LinearLayout
        layoutParams.topMargin = 100
        binding.tlBlankLetters.layoutParams = layoutParams


        // Params for TableRow
        val rowParams = TableRow.LayoutParams(
            TableRow.LayoutParams.WRAP_CONTENT,
            TableRow.LayoutParams.WRAP_CONTENT
        )
        val margin = 50
        rowParams.setMargins(0, margin, 0, margin)


        // Params for TextView
        val textParams = TableRow.LayoutParams(
            TableRow.LayoutParams.WRAP_CONTENT,
            TableRow.LayoutParams.WRAP_CONTENT
        )
        textParams.setMargins(10,10,10,10)

        val lettersInRow = 7

        val rowNumber = (letters.length / lettersInRow) + 1
        for (j in (0 until rowNumber)) {
            val tableRow = TableRow(context)
            tableRow.layoutParams = rowParams// TableLayout is the parent view

            val lettersLeft = max(letters.length - j * lettersInRow, 0)
            for (i in (0 until min(lettersInRow, lettersLeft))) {
                val textView = TextView(context)
                textView.text = " "
                val index = lettersInRow * (j) + i
                TextViewCompat.setTextAppearance(textView, R.style.WritingText)
                val coeff = 1.30
                textView.minHeight = (textView.textSize * coeff).toInt()
                textView.minWidth = (textView.textSize * coeff).toInt()
                textView.gravity = Gravity.CENTER
                textView.layoutParams = textParams // TableRow is the parent view
                textView.setBackgroundColor(Color.DKGRAY)
                textView.setOnClickListener{
                    onBlankClick(it,index)
                }
                tableRow.addView(textView)
                blankViews.add(textView)
                isNotEmptyViewList.add(false)
            }
            binding.tlBlankLetters.addView(tableRow)
        }
    }
    fun onBlankClick(notBlankField:View, blankPosition:Int){
        if(!isChecked) {
            if (isNotEmptyViewList[blankPosition]) {

                val letterPosition = positionsLettersToBlank[blankPosition]
                if (letterPosition != null) {
                    (notBlankField as TextView).text = " "
                    lettersViews[letterPosition].visibility = View.VISIBLE
                    isNotEmptyViewList[blankPosition] = false
                    if (!isNotEmptyViewList.reduce { b1, b2 -> b1 and b2 }) {
                        binding.btnSubmit.visibility = View.GONE

                    }
                }
            }
        }
    }
}

