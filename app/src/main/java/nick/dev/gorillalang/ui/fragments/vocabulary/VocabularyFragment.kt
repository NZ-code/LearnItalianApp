package nick.dev.gorillalang.ui.fragments.vocabulary

import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import nick.dev.gorillalang.R
import nick.dev.gorillalang.adapters.VocabularyPagerAdapter
import nick.dev.gorillalang.databinding.FragmentVocabularyBinding
import nick.dev.gorillalang.ui.MainActivity
import nick.dev.gorillalang.ui.viewModels.LanguageViewModel


class VocabularyFragment : Fragment() {
    lateinit var languageViewModel: LanguageViewModel

    private lateinit var binding: FragmentVocabularyBinding
    private lateinit var tabLayout : TabLayout
    private lateinit var viewPager2 : ViewPager2

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_vocabulary, container,false)

        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        languageViewModel =(activity as MainActivity).languageViewModel
        binding = FragmentVocabularyBinding.bind(view)

        tabLayout = binding.tlModules
        viewPager2 = binding.vpLearning

        viewPager2.adapter = VocabularyPagerAdapter(this)
        TabLayoutMediator(tabLayout, viewPager2){
                tab,index->tab.text = when(index){
            0->"Public Modules"
            1->"User Modules"
            else->{throw Resources.NotFoundException("Cannot find tab with index $index")}
        }
        }.attach()
    }



}