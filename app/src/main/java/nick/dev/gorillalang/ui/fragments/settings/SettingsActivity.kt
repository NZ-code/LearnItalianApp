package nick.dev.gorillalang.ui.fragments.settings

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import nick.dev.gorillalang.R
import nick.dev.gorillalang.databinding.ActivityMainBinding
import nick.dev.gorillalang.databinding.ActivitySettingsBinding
import nick.dev.gorillalang.db.LanguageDatabase
import nick.dev.gorillalang.remote.RemoteLanguageDatabase
import nick.dev.gorillalang.repository.LanguageRepository
import nick.dev.gorillalang.ui.viewModels.LanguageViewModel
import nick.dev.gorillalang.ui.viewModels.LanguageViewModelProviderFactory

class SettingsActivity : AppCompatActivity() {
    lateinit var languageViewModel: LanguageViewModel
    lateinit var binding : ActivitySettingsBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        val languageRepository = LanguageRepository(LanguageDatabase(this),
            RemoteLanguageDatabase()
        )
        val languageViewModelProviderFactory = LanguageViewModelProviderFactory(application, languageRepository)
        languageViewModel = ViewModelProvider(this, languageViewModelProviderFactory)[LanguageViewModel::class.java]
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolBar.ivBack.apply {
            visibility = View.VISIBLE
            setOnClickListener {
                onBackPressed()
            }
        }

    }


}