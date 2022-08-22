package nick.dev.gorillalang.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.room.migration.Migration
import nick.dev.gorillalang.R
import nick.dev.gorillalang.databinding.FragmentAddModuleBinding
import nick.dev.gorillalang.databinding.FragmentModuleBinding
import nick.dev.gorillalang.models.Module
import nick.dev.gorillalang.ui.MainActivity
import nick.dev.gorillalang.ui.viewModels.LanguageViewModel

class ModuleFragment:Fragment(R.layout.fragment_module) {
    private lateinit var binding:FragmentModuleBinding
    lateinit var languageViewModel: LanguageViewModel
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        languageViewModel =(activity as MainActivity).languageViewModel
        binding = FragmentModuleBinding.bind(view)


    }
}