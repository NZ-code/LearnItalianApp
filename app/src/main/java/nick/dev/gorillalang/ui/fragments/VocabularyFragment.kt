package nick.dev.gorillalang.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import nick.dev.gorillalang.R
import nick.dev.gorillalang.adapters.ModuleAdapter
import nick.dev.gorillalang.databinding.FragmentVocabularyBinding
import nick.dev.gorillalang.ui.MainActivity
import nick.dev.gorillalang.ui.viewModels.LanguageViewModel


class VocabularyFragment : Fragment(R.layout.fragment_vocabulary) {
    lateinit var languageViewModel: LanguageViewModel
    lateinit var moduleAdapter: ModuleAdapter
    private lateinit var binding: FragmentVocabularyBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        languageViewModel =(activity as MainActivity).languageViewModel
        binding = FragmentVocabularyBinding.bind(view)
        setupRecyclerView()
        binding.btnAddModule.setOnClickListener {
            val addModuleFragment = AddModuleFragment()
            (activity as (MainActivity)).setCurrentFragment(addModuleFragment)
        }
        languageViewModel.getUserModules().observe(viewLifecycleOwner, Observer {
            moduleAdapter.differ.submitList(it)
        })

    }
    fun setupRecyclerView(){

        moduleAdapter = ModuleAdapter()
        binding.rvModule.apply {
            layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
            adapter = moduleAdapter
            layoutManager = LinearLayoutManager(activity)
        }

    }


}