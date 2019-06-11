package com.sample.wireviewer.characterlist

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.sample.wireviewer.R
import com.sample.wireviewer.characterdetail.CharacterDetailActivity
import com.sample.wireviewer.characterdetail.CharacterDetailFragment
import com.sample.wireviewer.poko.Character
import kotlinx.android.synthetic.main.item_list_content.view.*

class CharacterListAdapter(
    private val parentActivity: CharacterListActivity,
    private val characters: List<Character>,
    private val twoPane: Boolean
) :
    RecyclerView.Adapter<CharacterListAdapter.ViewHolder>() {

    private val onClickListener: View.OnClickListener

    init {
        onClickListener = View.OnClickListener { v ->
            val wireCharacter = v.tag as Character

            if (twoPane) {
                val fragment = CharacterDetailFragment().apply {
                    arguments = Bundle().apply {
                        putParcelable(CharacterDetailFragment.ARG_CHARACTER, wireCharacter)
                    }
                }
                parentActivity.supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.item_detail_container, fragment)
                    .commit()
            } else {
                val intent = Intent(v.context, CharacterDetailActivity::class.java).apply {
                    putExtra(CharacterDetailFragment.ARG_CHARACTER, wireCharacter)
                }
                v.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_list_content, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val wireFrame = characters[position]
        holder.idView.text = wireFrame?.gettName()

        with(holder.itemView) {
            tag = wireFrame
            setOnClickListener(onClickListener)
        }
    }

    override fun getItemCount() = characters.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val idView: TextView = view.id_text
    }
}