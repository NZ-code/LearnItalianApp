package nick.dev.gorillalang.ui.viewModels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import nick.dev.gorillalang.repository.LanguageRepository

class LanguageViewModelProviderFactory(val app:Application,
                                       val languageRepository: LanguageRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LanguageViewModel(app, languageRepository) as T
    }
}