package nick.dev.gorillalang.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import nick.dev.gorillalang.R
import nick.dev.gorillalang.databinding.ActivityMainBinding
import nick.dev.gorillalang.db.LanguageDatabase
import nick.dev.gorillalang.remote.RemoteLanguageDatabase
import nick.dev.gorillalang.repository.LanguageRepository
import nick.dev.gorillalang.ui.fragments.settings.SettingsActivity
import nick.dev.gorillalang.ui.viewModels.LanguageViewModel
import nick.dev.gorillalang.ui.viewModels.LanguageViewModelProviderFactory

class MainActivity : AppCompatActivity() {
    lateinit var languageViewModel: LanguageViewModel
    lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        val languageRepository = LanguageRepository(LanguageDatabase(this),
            RemoteLanguageDatabase()
        )
        val languageViewModelProviderFactory = LanguageViewModelProviderFactory(application, languageRepository)
        languageViewModel = ViewModelProvider(this, languageViewModelProviderFactory)[LanguageViewModel::class.java]
        super.onCreate(savedInstanceState)
        binding =ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.navHostFragment) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavigationView.setupWithNavController(navController)

        setBackButton()
        setSettingsButton()
    }
    fun setBackButton(){
        binding.toolBar.ivBack.setOnClickListener{
            onBackPressed()
            makeBackButtonInvisible()
        }
    }
    fun setSettingsButton(){
        binding.toolBar.ivSettings.setOnClickListener {
            val intent = Intent(this,SettingsActivity::class.java)
            startActivity(intent)
        }
    }
    fun changeTitle(title:String){
        binding.toolBar.tvTitle.text = title
    }
    fun makeBackButtonVisible(){
        binding.toolBar.ivBack.visibility = View.VISIBLE
    }
    fun makeBackButtonInvisible(){
        binding.toolBar.ivBack.visibility = View.GONE
    }
}