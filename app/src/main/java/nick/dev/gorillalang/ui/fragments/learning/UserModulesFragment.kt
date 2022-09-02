package nick.dev.gorillalang.ui.fragments.learning

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import nick.dev.gorillalang.R
import nick.dev.gorillalang.adapters.ModuleAdapter
import nick.dev.gorillalang.databinding.FragmentUserModulesBinding
import nick.dev.gorillalang.ui.MainActivity
import nick.dev.gorillalang.ui.viewModels.LanguageViewModel

class UserModulesFragment:Fragment(R.layout.fragment_user_modules) {


    private lateinit var binding: FragmentUserModulesBinding
    lateinit var languageViewModel: LanguageViewModel
    lateinit var moduleAdapter: ModuleAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        languageViewModel =(activity as MainActivity).languageViewModel
        binding = FragmentUserModulesBinding.bind(view)
        setupRecyclerView()


        languageViewModel.getUserModules().observe(viewLifecycleOwner,{
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