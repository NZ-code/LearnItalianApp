package nick.dev.gorillalang.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import nick.dev.gorillalang.R
import nick.dev.gorillalang.databinding.FragmentAddModuleBinding
import nick.dev.gorillalang.databinding.FragmentAddWordBinding
import nick.dev.gorillalang.models.Module
import nick.dev.gorillalang.models.Word
import nick.dev.gorillalang.ui.MainActivity
import nick.dev.gorillalang.ui.viewModels.LanguageViewModel

class AddWordFragment:Fragment(R.layout.fragment_add_word) {
    private val args : AddWordFragmentArgs by navArgs()
    private lateinit var binding: FragmentAddWordBinding
    lateinit var languageViewModel: LanguageViewModel
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        languageViewModel =(activity as MainActivity).languageViewModel
        binding = FragmentAddWordBinding.bind(view)

        binding.btnAddWord.setOnClickListener {
            val learnWord = binding.etLearnLangWord.text.toString()
            val userWord = binding.etUserLangWord.text.toString()
            val module = args.selectedModule
            languageViewModel.saveWord(Word(userWord, learnWord, module.id))
            val action = AddWordFragmentDirections.actionAddWordFragmentToModuleFragment(module)
            Navigation.findNavController(view).navigate(action)
        }

    }
}