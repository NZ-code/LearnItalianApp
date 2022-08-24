package nick.dev.gorillalang.adapters

import android.content.res.Resources
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import nick.dev.gorillalang.ui.fragments.learning.PublicModulesFragment
import nick.dev.gorillalang.ui.fragments.learning.UserModulesFragment

class PagerAdapter(mainFragment:Fragment) :FragmentStateAdapter(mainFragment) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0->{PublicModulesFragment()}
            1->{UserModulesFragment()}
            else->{throw Resources.NotFoundException("Position of fragment not found")}
        }
    }

}