package nick.dev.gorillalang.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import nick.dev.gorillalang.R
import nick.dev.gorillalang.databinding.ActivityTrainingBinding
import nick.dev.gorillalang.db.LanguageDatabase
import nick.dev.gorillalang.etraining.*
import nick.dev.gorillalang.models.ModuleRemote
import nick.dev.gorillalang.remote.RemoteLanguageDatabase
import nick.dev.gorillalang.repository.LanguageRepository
import nick.dev.gorillalang.ui.fragments.training.MatchFragment
import nick.dev.gorillalang.ui.fragments.training.QuizFragment
import nick.dev.gorillalang.ui.fragments.training.TrainingResultFragment
import nick.dev.gorillalang.ui.fragments.training.WritingFragment
import nick.dev.gorillalang.ui.viewModels.*
import java.util.*

class TrainingActivity : AppCompatActivity() , TextToSpeech.OnInitListener{
    lateinit var trainingViewModel: TrainingViewModel
    lateinit var binding : ActivityTrainingBinding
    lateinit var  selectedModuleRemote:ModuleRemote
    lateinit var quizGame : QuizGame
    private var tts: TextToSpeech? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        selectedModuleRemote = intent.getSerializableExtra("selectedModule") as ModuleRemote
        val languageRepository = LanguageRepository(LanguageDatabase(this),
            RemoteLanguageDatabase()
        )
        val trainingViewModelProviderFactory = TrainingViewModelProviderFactory(application, languageRepository,selectedModuleRemote)
        trainingViewModel = ViewModelProvider(this, trainingViewModelProviderFactory)[TrainingViewModel::class.java]
        super.onCreate(savedInstanceState)
        binding =ActivityTrainingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        tts = TextToSpeech(this, this)

        getWords()



    }

    fun getWords(){

        trainingViewModel.getWordByRemoteModule(selectedModuleRemote).addOnSuccessListener {
            val wordsFromModule = it.toListOfWords(selectedModuleRemote)
            quizGame = trainingViewModel.getNewQuizGame(wordsFromModule)
            goToFragment(quizGame.getCurrentQuestion())

        }
    }
    fun goToFragment(question:Question){

        when(question.type){
            Question.TYPE_QUIZ->{
                supportFragmentManager.beginTransaction().apply {
                    replace(R.id.flFragment, QuizFragment(quizGame.getCurrentQuestion() as QuizQuestion))
                    commit()
                }
            }
            Question.TYPE_MATCH->{
                supportFragmentManager.beginTransaction().apply {
                    replace(R.id.flFragment, MatchFragment(quizGame.getCurrentQuestion() as MatchQuestion))
                    commit()
                }
            }
            Question.TYPE_WRITING->{
                supportFragmentManager.beginTransaction().apply {
                    replace(R.id.flFragment, WritingFragment(quizGame.getCurrentQuestion() as WritingQuestion))
                    commit()
                }
            }
        }

    }
    fun playWord(word:String){
        tts!!.speak(word, TextToSpeech.QUEUE_FLUSH, null,"")
    }
    fun nextQuestion() {
        quizGame.nextQuestion()
        goToFragment(quizGame.getCurrentQuestion())
    }
    fun updateScore(wasRight:Boolean){
        quizGame.changeScore(wasRight)
    }
    fun showResults() {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment, TrainingResultFragment())
            commit()
        }

        trainingViewModel.updateProgress()

    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            // set US English as language for tts
            val result = tts!!.setLanguage(Locale.ITALIAN)

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS","The Language specified is not supported!")
            } else {

            }

        } else {
            Log.e("TTS", "Initilization Failed!")
        }
    }
    public override fun onDestroy() {
        // Shutdown TTS
        if (tts != null) {
            tts!!.stop()
            tts!!.shutdown()
        }
        super.onDestroy()
    }

}