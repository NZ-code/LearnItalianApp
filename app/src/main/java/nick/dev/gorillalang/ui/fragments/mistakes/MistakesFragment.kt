package nick.dev.gorillalang.ui.fragments.mistakes

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import nick.dev.gorillalang.R
import nick.dev.gorillalang.adapters.MistakeAdapter
import nick.dev.gorillalang.adapters.WordAdapter
import nick.dev.gorillalang.databinding.FragmentMistakesBinding
import nick.dev.gorillalang.models.Mistake
import nick.dev.gorillalang.presentation.MistakePresentation
import nick.dev.gorillalang.ui.MainActivity
import nick.dev.gorillalang.ui.viewModels.LanguageViewModel

class MistakesFragment:Fragment(R.layout.fragment_mistakes) {


    private lateinit var binding: FragmentMistakesBinding
    lateinit var languageViewModel: LanguageViewModel
    private lateinit var mistakeAdapter: MistakeAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        languageViewModel =(activity as MainActivity).languageViewModel
        binding =FragmentMistakesBinding.bind(view)


        languageViewModel.getUserMistakes().observe(viewLifecycleOwner){ listOfMistakes ->
            val mistakesPresentation = mutableListOf<MistakePresentation>()
            for(mistake in listOfMistakes){
                languageViewModel.getWordById(mistake.wordId).observe(viewLifecycleOwner){

                    realWord->
                    mistakesPresentation.add(MistakePresentation(mistake, realWord))
                    mistakeAdapter.differ.submitList(mistakesPresentation)
                }
            }

        }
        setupRecyclerView()

    }
    fun setupRecyclerView(){

        mistakeAdapter = MistakeAdapter()
        mistakeAdapter.setOnDeleteClickListener {
            languageViewModel.deleteMistake(it.mistake)
        }
        binding.rvMistakes.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false)
            adapter = mistakeAdapter
            layoutManager = LinearLayoutManager(activity)
        }

    }
}