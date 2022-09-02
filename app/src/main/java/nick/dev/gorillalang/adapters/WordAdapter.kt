package nick.dev.gorillalang.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import nick.dev.gorillalang.databinding.ItemWordHeaderBinding
import nick.dev.gorillalang.models.WordRemote


class WordAdapter :RecyclerView.Adapter<WordAdapter.WordViewHolder>(){

    inner class WordViewHolder(val binding: ItemWordHeaderBinding) : RecyclerView.ViewHolder(binding.root)
    private val differCallback = object :DiffUtil.ItemCallback<WordRemote>(){
        override fun areItemsTheSame(oldItem: WordRemote, newItem: WordRemote): Boolean {
            return newItem.remoteId == oldItem.remoteId
        }

        override fun areContentsTheSame(oldItem: WordRemote, newItem: WordRemote): Boolean {
            return newItem == oldItem
        }
    }
    val differ = AsyncListDiffer(this, differCallback)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemWordHeaderBinding.inflate(layoutInflater,parent,false)
        return WordViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val word= differ.currentList[position]
        holder.binding.apply {

            tvLearnWord.text = word.moduleLearnLang
            tvUserWord.text = word.moduleUserLang
            if(word.isRemote){
                ibDelete.visibility = View.GONE
            }
            ibDelete.setOnClickListener{
                onDeleteClickListener?.let {
                    it(word)
                }
            }
            holder.binding.root.setOnClickListener {
                onClickListener?.let {
                    it(word)
                }
            }

        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
    private var onDeleteClickListener: ((WordRemote)->Unit)?= null
    fun setOnDeleteClickListener(listener :(WordRemote)->Unit){
        onDeleteClickListener = listener
    }
    private var onClickListener: ((WordRemote)->Unit)?= null
    fun setOnClickListener(listener :(WordRemote)->Unit){
        onClickListener = listener
    }

}