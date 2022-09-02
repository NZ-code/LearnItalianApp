package nick.dev.gorillalang.ui.fragments.vocabulary

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import nick.dev.gorillalang.R
import nick.dev.gorillalang.adapters.WordAdapter
import nick.dev.gorillalang.databinding.FragmentModuleBinding
import nick.dev.gorillalang.ui.MainActivity
import nick.dev.gorillalang.ui.viewModels.LanguageViewModel
import java.util.*

class ModuleFragment:Fragment(R.layout.fragment_module) {
    val args: nick.dev.gorillalang.ui.fragments.vocabulary.ModuleFragmentArgs by navArgs()
    private lateinit var wordAdapter: WordAdapter
    private lateinit var binding:FragmentModuleBinding
    lateinit var languageViewModel: LanguageViewModel
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        languageViewModel =(activity as MainActivity).languageViewModel
        binding = FragmentModuleBinding.bind(view)

        setupRecyclerView()


        var selectedModule = args.selectedModule



        binding.textView2.text = selectedModule.moduleName.replaceFirstChar {
            it -> it.uppercase()
        }
        binding.btnAddWord.setOnClickListener {
            val action =
                ModuleFragmentDirections.actionModuleFragmentToAddWordFragment(
                    selectedModule
                )
            Navigation.findNavController(view).navigate(action)
        }
        if(selectedModule.isRemote){
            binding.rvWords.visibility = View.GONE

            languageViewModel.getWordByRemoteModule(selectedModule).addOnSuccessListener {
                Thread.sleep(100)
                binding.rvWords.visibility = View.VISIBLE

            }
            languageViewModel.words.observe(viewLifecycleOwner,
                Observer {
                    wordAdapter.differ.submitList(it)


                })

        }
        else{
            languageViewModel.getModuleWithWords(selectedModule.remoteId).observe(viewLifecycleOwner,
                Observer {
                    wordAdapter.differ.submitList(it[0].wordRemotes)

                })
        }


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