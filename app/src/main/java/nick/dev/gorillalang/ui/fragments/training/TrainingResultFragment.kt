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
import nick.dev.gorillalang.databinding.FragmentResultTrainingBinding
import nick.dev.gorillalang.databinding.FragmentWritingBinding
import nick.dev.gorillalang.etraining.WritingQuestion
import nick.dev.gorillalang.ui.TrainingActivity
import nick.dev.gorillalang.ui.viewModels.LanguageViewModel
import kotlin.math.max
import kotlin.math.min


class TrainingResultFragment():Fragment(R.layout.fragment_result_training) {


    private lateinit var binding: FragmentResultTrainingBinding
    lateinit var languageViewModel: LanguageViewModel
    private lateinit var trainingActivity: TrainingActivity
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        languageViewModel = (activity as TrainingActivity).languageViewModel
        trainingActivity = activity as TrainingActivity
        val quizGame = trainingActivity.quizGame
        binding = FragmentResultTrainingBinding.bind(view)
        val score = quizGame.score
        val numQuestion = quizGame.gameQuestionsNum
        binding.tvResult.text  = "$score/$numQuestion"
        binding.tvResultPercentage.text = "${(100*score)/numQuestion}%"
        }
    }
