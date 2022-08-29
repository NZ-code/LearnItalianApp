package nick.dev.gorillalang.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import nick.dev.gorillalang.R
import nick.dev.gorillalang.databinding.ActivityTrainingBinding
import nick.dev.gorillalang.db.LanguageDatabase
import nick.dev.gorillalang.etraining.*
import nick.dev.gorillalang.models.Module
import nick.dev.gorillalang.models.Word
import nick.dev.gorillalang.remote.RemoteLanguageDatabase
import nick.dev.gorillalang.repository.LanguageRepository
import nick.dev.gorillalang.ui.fragments.training.MatchFragment
import nick.dev.gorillalang.ui.fragments.training.QuizFragment
import nick.dev.gorillalang.ui.fragments.training.TrainingResultFragment
import nick.dev.gorillalang.ui.fragments.training.WritingFragment
import nick.dev.gorillalang.ui.viewModels.LanguageViewModel
import nick.dev.gorillalang.ui.viewModels.LanguageViewModelProviderFactory
import nick.dev.gorillalang.ui.viewModels.toListOfWords
import java.util.*

class TrainingActivity : AppCompatActivity() , TextToSpeech.OnInitListener{
    lateinit var languageViewModel: LanguageViewModel
    lateinit var binding : ActivityTrainingBinding
    lateinit var  selectedModule:Module
    lateinit var quizGame : QuizGame
    private var tts: TextToSpeech? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        selectedModule = intent.getSerializableExtra("selectedModule") as Module
        val languageRepository = LanguageRepository(LanguageDatabase(this),
            RemoteLanguageDatabase()
        )
        val languageViewModelProviderFactory = LanguageViewModelProviderFactory(application, languageRepository)
        languageViewModel = ViewModelProvider(this, languageViewModelProviderFactory)[LanguageViewModel::class.java]
        super.onCreate(savedInstanceState)
        binding =ActivityTrainingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        tts = TextToSpeech(this, this)

        getWords()



    }

    fun getWords(){

        languageViewModel.getWordByRemoteModule(selectedModule).addOnSuccessListener {
            val wordsFromModule = it.toListOfWords(selectedModule)
            quizGame = languageViewModel.getNewQuizGame(wordsFromModule)
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
        for(q in quizGame.wasRightList){
            languageViewModel.upsertRemoteProgress(q)
        }

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