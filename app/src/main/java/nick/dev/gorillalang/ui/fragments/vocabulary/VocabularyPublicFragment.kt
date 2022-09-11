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
import nick.dev.gorillalang.adapters.VocabularyModuleAdapter
import nick.dev.gorillalang.databinding.FragmentVocabularyPublicBinding
import nick.dev.gorillalang.ui.MainActivity
import nick.dev.gorillalang.ui.viewModels.LanguageViewModel


class VocabularyPublicFragment : Fragment() {
    lateinit var languageViewModel: LanguageViewModel
    lateinit var vocabularyModuleAdapter: VocabularyModuleAdapter
    private lateinit var binding: FragmentVocabularyPublicBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_vocabulary_public, container,false)

        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        languageViewModel =(activity as MainActivity).languageViewModel
        binding = FragmentVocabularyPublicBinding.bind(view)
        setupRecyclerView()


        // public
        languageViewModel.getPublicModules()
        languageViewModel.publicModules.observe(viewLifecycleOwner, Observer {

            vocabularyModuleAdapter.differ.submitList(it)
        })




        vocabularyModuleAdapter.setOnDeleteClickListener {
            vocabularyModuleAdapter.differ.currentList
            languageViewModel.deleteModule(moduleRemote = it)

        }
        vocabularyModuleAdapter.setOnClickListener {
            val action =
                nick.dev.gorillalang.ui.fragments.vocabulary.VocabularyFragmentDirections.actionVocabularyFragmentToModuleFragment(
                    it
                )
            Navigation.findNavController(view).navigate(action)
        }
    }
    fun setupRecyclerView(){

        vocabularyModuleAdapter = VocabularyModuleAdapter()

        binding.rvModule.apply {
            layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
            adapter = vocabularyModuleAdapter
            layoutManager = LinearLayoutManager(activity)
        }

    }


}