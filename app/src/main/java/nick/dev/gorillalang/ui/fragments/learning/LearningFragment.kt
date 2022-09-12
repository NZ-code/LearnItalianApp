package nick.dev.gorillalang.ui.fragments.learning

import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import nick.dev.gorillalang.R
import nick.dev.gorillalang.adapters.LearningModuleAdapter
import nick.dev.gorillalang.adapters.PagerAdapter
import nick.dev.gorillalang.databinding.FragmentLearningBinding
import nick.dev.gorillalang.ui.MainActivity
import nick.dev.gorillalang.ui.TrainingActivity
import nick.dev.gorillalang.ui.viewModels.LanguageViewModel

class LearningFragment:Fragment(R.layout.fragment_learning) {


    private lateinit var binding: FragmentLearningBinding
    lateinit var languageViewModel: LanguageViewModel
    lateinit var publicAdapter: LearningModuleAdapter
    lateinit var userAdapter: LearningModuleAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        languageViewModel =(activity as MainActivity).languageViewModel
        binding = FragmentLearningBinding.bind(view)

        setupPublicRecyclerView()
        languageViewModel.getPublicModules()
        //public modules
        languageViewModel.publicModules.observe(viewLifecycleOwner) {
            publicAdapter.differ.submitList(it)
        }

        setupUserRecyclerView()

        languageViewModel.getUserModules().observe(viewLifecycleOwner) {
           userAdapter.differ.submitList(it)
        }

    }
    fun setupPublicRecyclerView(){

        publicAdapter = LearningModuleAdapter()
        publicAdapter.setOnClickListener {
            val intent = Intent(activity, TrainingActivity::class.java)
            intent.putExtra("selectedModule",it)
            startActivity(intent)

        }
        binding.rvPublicModules.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false)
            adapter =  publicAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
    fun setupUserRecyclerView(){

        userAdapter = LearningModuleAdapter()
        userAdapter.setOnClickListener {
            val intent = Intent(activity, TrainingActivity::class.java)
            intent.putExtra("selectedModule",it)
            startActivity(intent)

        }
        binding.rvUserModules.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false)
            adapter =   userAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
}