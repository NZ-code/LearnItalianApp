package nick.dev.gorillalang.ui.fragments.training

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import nick.dev.gorillalang.R
import nick.dev.gorillalang.databinding.FragmentResultTrainingBinding
import nick.dev.gorillalang.ui.TrainingActivity
import nick.dev.gorillalang.ui.viewModels.TrainingViewModel


class TrainingResultFragment():Fragment(R.layout.fragment_result_training) {


    private lateinit var binding: FragmentResultTrainingBinding
    lateinit var trainingViewModel: TrainingViewModel
    private lateinit var trainingActivity: TrainingActivity
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        trainingViewModel = (activity as TrainingActivity).trainingViewModel
        trainingActivity = activity as TrainingActivity
        val quizGame = trainingActivity.quizGame
        binding = FragmentResultTrainingBinding.bind(view)
        val score = quizGame.score
        val numQuestion = quizGame.gameQuestionsNum
        binding.tvResult.text  = "$score/$numQuestion"
        binding.tvResultPercentage.text = "${(100*score)/numQuestion}%"
        }
    }
