package nick.dev.gorillalang.ui.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.launch
import nick.dev.gorillalang.models.Module
import nick.dev.gorillalang.repository.LanguageRepository

class LanguageViewModel(app:Application, private val languageRepository:
LanguageRepository):AndroidViewModel(app)
{
    fun saveModule(module: Module) = viewModelScope.launch {
        languageRepository.upsertModule(module)
    }
    fun deleteModule(module: Module) = viewModelScope.launch {
        languageRepository.deleteModule(module)
    }
    fun getUserModules() = languageRepository.getUserModules()
}