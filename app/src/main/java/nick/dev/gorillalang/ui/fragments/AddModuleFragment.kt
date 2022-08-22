package nick.dev.gorillalang.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import nick.dev.gorillalang.R
import nick.dev.gorillalang.databinding.FragmentAddModuleBinding
import nick.dev.gorillalang.models.Module
import nick.dev.gorillalang.ui.MainActivity
import nick.dev.gorillalang.ui.viewModels.LanguageViewModel

class AddModuleFragment:Fragment(R.layout.fragment_add_module) {
    private lateinit var binding: FragmentAddModuleBinding
    lateinit var languageViewModel: LanguageViewModel
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        languageViewModel =(activity as MainActivity).languageViewModel
        binding = FragmentAddModuleBinding.bind(view)

        binding.btnAddModule.setOnClickListener {
            languageViewModel.saveModule(Module(null,binding.etModuleName.text.toString()))
            Navigation.findNavController(view).navigate(R.id.navigateToVocabularyFromAddModule)
        }

    }
}