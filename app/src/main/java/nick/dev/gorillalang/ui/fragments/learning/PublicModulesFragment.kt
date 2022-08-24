package nick.dev.gorillalang.ui.fragments.learning

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
import nick.dev.gorillalang.R
import nick.dev.gorillalang.adapters.ModuleAdapter
import nick.dev.gorillalang.adapters.WordAdapter
import nick.dev.gorillalang.databinding.*
import nick.dev.gorillalang.models.Module
import nick.dev.gorillalang.ui.MainActivity
import nick.dev.gorillalang.ui.viewModels.LanguageViewModel

class PublicModulesFragment:Fragment(R.layout.fragment_public_modules) {

   
    private lateinit var binding: FragmentPublicModulesBinding
    lateinit var languageViewModel: LanguageViewModel
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        languageViewModel =(activity as MainActivity).languageViewModel
        binding = FragmentPublicModulesBinding.bind(view)



    }

}