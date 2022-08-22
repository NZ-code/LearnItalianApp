package nick.dev.gorillalang.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import nick.dev.gorillalang.R
import nick.dev.gorillalang.databinding.ActivityMainBinding
import nick.dev.gorillalang.db.LanguageDatabase
import nick.dev.gorillalang.repository.LanguageRepository
import nick.dev.gorillalang.ui.viewModels.LanguageViewModel
import nick.dev.gorillalang.ui.viewModels.LanguageViewModelProviderFactory

class MainActivity : AppCompatActivity() {
    lateinit var languageViewModel: LanguageViewModel
    lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        val languageRepository = LanguageRepository(LanguageDatabase(this))
        val languageViewModelProviderFactory = LanguageViewModelProviderFactory(application, languageRepository)
        languageViewModel = ViewModelProvider(this, languageViewModelProviderFactory)[LanguageViewModel::class.java]
        super.onCreate(savedInstanceState)
        binding =ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }

}