package nick.dev.gorillalang.adapters

import android.content.res.Resources
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import nick.dev.gorillalang.ui.fragments.learning.PublicModulesFragment
import nick.dev.gorillalang.ui.fragments.learning.UserModulesFragment
import nick.dev.gorillalang.ui.fragments.vocabulary.VocabularyPublicFragment
import nick.dev.gorillalang.ui.fragments.vocabulary.VocabularyUserFragment

class VocabularyPagerAdapter(mainFragment:Fragment) :FragmentStateAdapter(mainFragment) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0->{
                VocabularyPublicFragment()
            }
            1->{
                VocabularyUserFragment()
            }
            else->{throw Resources.NotFoundException("Position of fragment not found")}
        }
    }

}