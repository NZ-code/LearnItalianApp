package nick.dev.gorillalang.ui.fragments.vocabulary

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import nick.dev.gorillalang.R
import nick.dev.gorillalang.databinding.FragmentAddModuleBinding
import nick.dev.gorillalang.models.Module
import nick.dev.gorillalang.ui.MainActivity
import nick.dev.gorillalang.ui.viewModels.LanguageViewModel
import java.util.*

class AddModuleFragment:Fragment(R.layout.fragment_add_module) {
    private lateinit var binding: FragmentAddModuleBinding
    lateinit var languageViewModel: LanguageViewModel
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        languageViewModel =(activity as MainActivity).languageViewModel
        binding = FragmentAddModuleBinding.bind(view)
        val mainActivity = (activity as MainActivity)
        mainActivity.makeBackButtonVisible()
        binding.btnAddModule.setOnClickListener {
            val uniqueID = UUID.randomUUID().toString()
            languageViewModel.saveModule(Module(binding.etModuleName.text.toString().trim(),false,uniqueID))

            Navigation.findNavController(view).apply {

                navigate(R.id.navigateToVocabularyFromAddModule)
            }
        }

    }
}