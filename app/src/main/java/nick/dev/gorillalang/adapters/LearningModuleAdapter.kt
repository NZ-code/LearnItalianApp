package nick.dev.gorillalang.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import nick.dev.gorillalang.databinding.ItemModuleHeaderBinding
import nick.dev.gorillalang.databinding.ItemModuleHeaderVocabularyBinding
import nick.dev.gorillalang.models.ModuleRemote

class LearningModuleAdapter :RecyclerView.Adapter<LearningModuleAdapter.ModuleViewHolder>(){

    inner class ModuleViewHolder(val binding: ItemModuleHeaderBinding) : RecyclerView.ViewHolder(binding.root)
    private val differCallback = object :DiffUtil.ItemCallback<ModuleRemote>(){

        override fun areItemsTheSame(oldItem: ModuleRemote, newItem: ModuleRemote): Boolean {
            return newItem.remoteId == oldItem.remoteId
        }

        override fun areContentsTheSame(oldItem: ModuleRemote, newItem: ModuleRemote): Boolean {
            return newItem == oldItem
        }


    }
    val differ = AsyncListDiffer(this, differCallback)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ModuleViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemModuleHeaderBinding.inflate(layoutInflater,parent,false)
        return ModuleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ModuleViewHolder, position: Int) {
        val module = differ.currentList[position]
        holder.binding.apply {


            tvModule.text = module.moduleName.replaceFirstChar {
                it.uppercase()
            }

            holder.binding.root.setOnClickListener {
                onClickListener?.let {
                    it(module)
                }
            }

        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
    private var onDeleteClickListener: ((ModuleRemote)->Unit)?= null

    private var onClickListener: ((ModuleRemote)->Unit)?= null
    fun setOnClickListener(listener :(ModuleRemote)->Unit){
        onClickListener = listener
    }

}