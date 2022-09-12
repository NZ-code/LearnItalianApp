package nick.dev.gorillalang.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import nick.dev.gorillalang.databinding.ItemMistakeBinding
import nick.dev.gorillalang.presentation.MistakePresentation


class MistakeAdapter :RecyclerView.Adapter<MistakeAdapter.WordViewHolder>(){

    inner class WordViewHolder(val binding: ItemMistakeBinding) : RecyclerView.ViewHolder(binding.root)
    private val differCallback = object :DiffUtil.ItemCallback<MistakePresentation>(){
        override fun areItemsTheSame(oldItem: MistakePresentation, newItem: MistakePresentation): Boolean {
            return newItem.mistake.remoteId == oldItem.mistake.remoteId
        }

        override fun areContentsTheSame(oldItem: MistakePresentation, newItem: MistakePresentation): Boolean {
            return newItem == oldItem
        }
    }
    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding =  ItemMistakeBinding.inflate(layoutInflater,parent,false)
        return WordViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val mistake= differ.currentList[position]
        holder.binding.apply {

            tvMistake.text = mistake.mistake.mistakeText


            tvRealWord.text = mistake.word.moduleLearnLang


            ibDelete.setOnClickListener{
                onDeleteClickListener?.let {
                    it(mistake)
                }
            }
            holder.binding.root.setOnClickListener {
                onClickListener?.let {
                    it(mistake)
                }
            }

        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
    private var onDeleteClickListener: ((MistakePresentation)->Unit)?= null

    fun setOnDeleteClickListener(listener :(MistakePresentation)->Unit){
        onDeleteClickListener = listener
    }
    private var onClickListener: ((MistakePresentation)->Unit)?= null
    fun setOnClickListener(listener :(MistakePresentation)->Unit){
        onClickListener = listener
    }

}