package nick.dev.gorillalang.ui.fragments.learning

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import nick.dev.gorillalang.R
import nick.dev.gorillalang.adapters.LearningModuleAdapter
import nick.dev.gorillalang.adapters.VocabularyModuleAdapter
import nick.dev.gorillalang.databinding.*
import nick.dev.gorillalang.ui.MainActivity
import nick.dev.gorillalang.ui.TrainingActivity
import nick.dev.gorillalang.ui.viewModels.LanguageViewModel

class PublicModulesFragment:Fragment(R.layout.fragment_public_modules) {

   
    private lateinit var binding: FragmentPublicModulesBinding
    lateinit var languageViewModel: LanguageViewModel
    lateinit var learningModuleAdapter: LearningModuleAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        languageViewModel =(activity as MainActivity).languageViewModel
        binding = FragmentPublicModulesBinding.bind(view)
        setupRecyclerView()
        languageViewModel.getPublicModules()

        languageViewModel.publicModules.observe(viewLifecycleOwner,{
            learningModuleAdapter.differ.submitList(it)
        })


    }
    fun setupRecyclerView(){

        learningModuleAdapter = LearningModuleAdapter()
        learningModuleAdapter.setOnClickListener {
            val intent = Intent(activity, TrainingActivity::class.java)
            intent.putExtra("selectedModule",it)
            startActivity(intent)

        }
        binding.rvModule.apply {
            layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
            adapter = learningModuleAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
}