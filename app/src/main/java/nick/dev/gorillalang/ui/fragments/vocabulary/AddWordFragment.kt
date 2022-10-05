package nick.dev.gorillalang.ui.fragments.vocabulary



import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import nick.dev.gorillalang.R
import nick.dev.gorillalang.databinding.FragmentAddWordBinding
import nick.dev.gorillalang.models.Word
import nick.dev.gorillalang.ui.MainActivity
import nick.dev.gorillalang.ui.viewModels.LanguageViewModel
import java.util.*

class AddWordFragment:Fragment(R.layout.fragment_add_word) {
    private val args : nick.dev.gorillalang.ui.fragments.vocabulary.AddWordFragmentArgs by navArgs()
    private lateinit var binding: FragmentAddWordBinding
    private var clipboard : ClipboardManager?=null
    lateinit var languageViewModel: LanguageViewModel
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        languageViewModel =(activity as MainActivity).languageViewModel
        binding = FragmentAddWordBinding.bind(view)
        val mainActivity = (activity as MainActivity)
        mainActivity.makeBackButtonVisible()

        binding.tlLearnLangWord.setEndIconOnClickListener {
            clipboard = mainActivity.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val item = clipboard?.primaryClip?.getItemAt(0)
            val word = item?.text.toString()
            val editText = binding.etLearnLangWord
            editText.setText(word)
            editText.setSelection(editText.length())
        }
        binding.tlUserLangWord.setEndIconOnClickListener {
            clipboard = mainActivity.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val item = clipboard?.primaryClip?.getItemAt(0)
            val word = item?.text.toString()
            val editText = binding.etUserLangWord
            editText.setText(word)
            editText.setSelection(editText.length())
        }
        binding.btnAddWord.setOnClickListener {
            val learnWord = binding.etLearnLangWord.text.toString().trim()
            val userWord = binding.etUserLangWord.text.toString().trim()
            val module = args.selectedModule
            val uniqueID = UUID.randomUUID().toString()
            languageViewModel.saveWord(Word(userWord, learnWord, module.remoteId,false,uniqueID))
            val action =
                nick.dev.gorillalang.ui.fragments.vocabulary.AddWordFragmentDirections.actionAddWordFragmentToModuleFragment(
                    module
                )
            Navigation.findNavController(view).apply {

                navigate(action)

            }

        }

    }
}