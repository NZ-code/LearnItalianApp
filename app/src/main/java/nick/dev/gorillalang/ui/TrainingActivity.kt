package nick.dev.gorillalang.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
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
import nick.dev.gorillalang.ui.viewModels.*
import nick.dev.gorillalang.util.Constants
import java.util.*

class TrainingActivity : AppCompatActivity() , TextToSpeech.OnInitListener{
    lateinit var trainingViewModel: TrainingViewModel
    lateinit var binding : ActivityTrainingBinding
    lateinit var  selectedModule:Module
    lateinit var quizGame : QuizGame
    private var tts: TextToSpeech? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        selectedModule = intent.getSerializableExtra("selectedModule") as Module
        val languageRepository = LanguageRepository(LanguageDatabase(this),
            RemoteLanguageDatabase()
        )


        val trainingViewModelProviderFactory = TrainingViewModelProviderFactory(application, languageRepository,selectedModule)
        trainingViewModel = ViewModelProvider(this, trainingViewModelProviderFactory)[TrainingViewModel::class.java]
        super.onCreate(savedInstanceState)
        binding =ActivityTrainingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        tts = TextToSpeech(this, this)


        getWords()



    }
    private fun getWords(){

        if(selectedModule.isRemote){
            trainingViewModel.getWordByRemoteModule(selectedModule).addOnSuccessListener {
                val wordsFromModule = it.toListOfWords(selectedModule)
                wordsFromModule.forEach {
                        word -> getProgress(word)
                }



                if(wordsFromModule.size >= Constants.MIN_WORDS_TO_LEARN ){
                    quizGame = trainingViewModel.getNewQuizGame(wordsFromModule)
                    goToFragment(quizGame.getCurrentQuestion())
                }
                else{
                    Toast.makeText(this,
                        "Module is not ready.",Toast.LENGTH_LONG).show()
                }

            }
        }
        else{
            trainingViewModel.getWordByLocalModule(selectedModule).observe(this){
                val words = it[0].words
                words.forEach {
                    word -> getProgress(word)
                }
                if( words.size >= Constants.MIN_WORDS_TO_LEARN ){
                    quizGame = trainingViewModel.getNewQuizGame( words)
                    goToFragment(quizGame.getCurrentQuestion())
                }
                else{
                    Toast.makeText(this,
                        "Module need minimum ${Constants.MIN_WORDS_TO_LEARN} words.",Toast.LENGTH_LONG).show()
                }
            }
        }


    }

    private fun getProgress(word: Word) {

        //val progress = trainingViewModel.getWordProgress(word.remoteId)[0].level
        //word.progress = progress
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
    fun addMistake(word:Word, mistake: String){
        trainingViewModel.addMistake(word,mistake)
    }

    fun updateScore(wasRight:Boolean){
        quizGame.changeScore(wasRight)
    }
    fun finishQuiz() {
        // show result fragment
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment, TrainingResultFragment())
            commit()
        }
        // update word and module progress
        trainingViewModel.updateProgress()

    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            // set US English as language for tts
            val result = tts!!.setLanguage(Locale("pl"))

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