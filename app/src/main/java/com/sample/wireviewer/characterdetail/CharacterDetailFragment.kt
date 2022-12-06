package com.sample.wireviewer.characterdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.sample.wireviewer.R
import com.sample.wireviewer.characterlist.failureMessage
import com.sample.wireviewer.databinding.ViewItemDetailBinding
import com.sample.wireviewer.model.Character

const val ARG_CHARACTER = "character"

class CharacterDetailFragment : Fragment() {

    private lateinit var binding: ViewItemDetailBinding
    private var selectedCharacter: Character? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            if (it.containsKey(ARG_CHARACTER)) {
                selectedCharacter = it.getParcelable(ARG_CHARACTER)
                activity?.title = selectedCharacter?.getCharacterName()
            } else {
                val alertDialog = AlertDialog.Builder(requireContext())
                alertDialog.failureMessage()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ViewItemDetailBinding.inflate(layoutInflater)
        val imageURL = "https://www.duckduckgo.com" + selectedCharacter?.icon?.uRL
        Glide.with(this).load(imageURL)
            .placeholder(R.drawable.ic_sphere_wireframe_10deg_6r).into(binding.characterImage)
        binding.characterDescription.text = selectedCharacter?.getCharacterDescription()
        return binding.root
    }

}
