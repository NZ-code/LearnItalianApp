package nick.dev.gorillalang.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.migration.Migration
import nick.dev.gorillalang.R
import nick.dev.gorillalang.adapters.ModuleAdapter
import nick.dev.gorillalang.adapters.WordAdapter
import nick.dev.gorillalang.databinding.FragmentAddModuleBinding
import nick.dev.gorillalang.databinding.FragmentModuleBinding
import nick.dev.gorillalang.models.Module
import nick.dev.gorillalang.ui.MainActivity
import nick.dev.gorillalang.ui.viewModels.LanguageViewModel

class ModuleFragment:Fragment(R.layout.fragment_module) {
    val args: ModuleFragmentArgs by navArgs()
    private lateinit var wordAdapter: WordAdapter
    private lateinit var binding:FragmentModuleBinding
    lateinit var languageViewModel: LanguageViewModel
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        languageViewModel =(activity as MainActivity).languageViewModel
        binding = FragmentModuleBinding.bind(view)
        setupRecyclerView()

        binding.textView2.text = args.selectedModule.moduleName
        binding.btnAddWord.setOnClickListener {
            val action = ModuleFragmentDirections.actionModuleFragmentToAddWordFragment(args.selectedModule)
            Navigation.findNavController(view).navigate(action)
        }
        languageViewModel.getModuleWithWords(args.selectedModule.id).observe(viewLifecycleOwner,
            Observer {
                wordAdapter.differ.submitList(it[0].words)

            })
    }
    fun setupRecyclerView(){

        wordAdapter = WordAdapter()
        wordAdapter.setOnDeleteClickListener {
            languageViewModel.deleteWord(it)
        }
        binding.rvWords.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false)
            adapter = wordAdapter
            layoutManager = LinearLayoutManager(activity)
        }

    }
}