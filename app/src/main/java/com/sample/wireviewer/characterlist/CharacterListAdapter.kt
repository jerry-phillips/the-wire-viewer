package com.sample.wireviewer.characterlist


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sample.wireviewer.databinding.ViewListItemBinding
import com.sample.wireviewer.model.Character

class CharacterListAdapter(
    private val characters: List<Character>,
    private val onClick: (character: Character) -> Unit

) : RecyclerView.Adapter<ListItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListItemViewHolder {
        return ListItemViewHolder(
            ViewListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ListItemViewHolder, position: Int) {
        val character = characters[position]
        holder.onBind(character, onClick)
    }

    override fun getItemCount() = characters.size
}

class ListItemViewHolder(private val binding: ViewListItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun onBind(character: Character, onClick: (character: Character) -> Unit) {
        binding.idText.text = character.text
        itemView.setOnClickListener { onClick(character) }
    }
}