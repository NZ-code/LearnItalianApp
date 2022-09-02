package nick.dev.gorillalang.ui.fragments.mistakes

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import nick.dev.gorillalang.R
import nick.dev.gorillalang.databinding.FragmentMistakesBinding
import nick.dev.gorillalang.ui.MainActivity
import nick.dev.gorillalang.ui.viewModels.LanguageViewModel

class MistakesFragment:Fragment(R.layout.fragment_mistakes) {


    private lateinit var binding: FragmentMistakesBinding
    lateinit var languageViewModel: LanguageViewModel
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        languageViewModel =(activity as MainActivity).languageViewModel
        binding =FragmentMistakesBinding.bind(view)



    }

}