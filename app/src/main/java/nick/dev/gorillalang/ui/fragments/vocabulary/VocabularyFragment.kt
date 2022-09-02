package nick.dev.gorillalang.ui.fragments.vocabulary

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import nick.dev.gorillalang.R
import nick.dev.gorillalang.adapters.ModuleAdapter
import nick.dev.gorillalang.databinding.FragmentVocabularyBinding
import nick.dev.gorillalang.ui.MainActivity
import nick.dev.gorillalang.ui.viewModels.LanguageViewModel


class VocabularyFragment : Fragment() {
    lateinit var languageViewModel: LanguageViewModel
    lateinit var moduleAdapter: ModuleAdapter
    private lateinit var binding: FragmentVocabularyBinding

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
        setupRecyclerView()
        binding.btnAddModule.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.navigateToAddModuleFromVocabulary)
        }

        // public
        languageViewModel.getPublicModules()
        languageViewModel.publicModules.observe(viewLifecycleOwner, Observer {

            moduleAdapter.differ.submitList((moduleAdapter.differ.currentList + it).distinct())
        })


        // user
        languageViewModel.getUserModules().observe(viewLifecycleOwner, Observer {

            moduleAdapter.differ.submitList((moduleAdapter.differ.currentList + it).distinct())

        })

        moduleAdapter.setOnDeleteClickListener {
            moduleAdapter.differ.currentList
            languageViewModel.deleteModule(moduleRemote = it)

        }
        moduleAdapter.setOnClickListener {
            val action =
                nick.dev.gorillalang.ui.fragments.vocabulary.VocabularyFragmentDirections.actionVocabularyFragmentToModuleFragment(
                    it
                )
            Navigation.findNavController(view).navigate(action)
        }
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