package nick.dev.gorillalang.ui.fragments.learning

import android.content.res.Resources
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.migration.Migration
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import nick.dev.gorillalang.R
import nick.dev.gorillalang.adapters.ModuleAdapter
import nick.dev.gorillalang.adapters.PagerAdapter
import nick.dev.gorillalang.adapters.WordAdapter
import nick.dev.gorillalang.databinding.FragmentAddModuleBinding
import nick.dev.gorillalang.databinding.FragmentLearningBinding
import nick.dev.gorillalang.databinding.FragmentModuleBinding
import nick.dev.gorillalang.models.Module
import nick.dev.gorillalang.ui.MainActivity
import nick.dev.gorillalang.ui.viewModels.LanguageViewModel

class LearningFragment:Fragment(R.layout.fragment_learning) {

    private lateinit var tabLayout :TabLayout
    private lateinit var viewPager2 :ViewPager2
    private lateinit var binding: FragmentLearningBinding
    lateinit var languageViewModel: LanguageViewModel
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        languageViewModel =(activity as MainActivity).languageViewModel
        binding = FragmentLearningBinding.bind(view)

        tabLayout = binding.tlModules
        viewPager2 = binding.vpLearning

        viewPager2.adapter = PagerAdapter(this)
        TabLayoutMediator(tabLayout, viewPager2){
            tab,index->tab.text = when(index){
                0->"Public Modules"
                1->"User Modules"
                else->{throw Resources.NotFoundException("Cannot find tab with index $index")}
            }
        }.attach()


    }

}