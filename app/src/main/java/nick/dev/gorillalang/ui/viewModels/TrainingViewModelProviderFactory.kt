package nick.dev.gorillalang.ui.viewModels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import nick.dev.gorillalang.models.ModuleRemote
import nick.dev.gorillalang.repository.LanguageRepository

class TrainingViewModelProviderFactory(val app:Application,
                                       val languageRepository: LanguageRepository,
                                       val moduleRemote:ModuleRemote
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return TrainingViewModel(app, languageRepository, moduleRemote) as T
    }
}