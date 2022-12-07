package com.sample.wireviewer.characterdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.sample.wireviewer.R
import com.sample.wireviewer.databinding.ViewItemDetailBinding

class CharacterDetailFragment : Fragment() {

    private lateinit var binding: ViewItemDetailBinding
    private var characterDesctiption: String? = null
    private var url: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { bundle ->
            activity?.title = bundle.getString(ARG_CHARACTER_NAME)
            characterDesctiption = bundle.getString(ARG_CHARACTER_DESCRIPTION)
            url = bundle.getString(ARG_CHARACTER_URL)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ViewItemDetailBinding.inflate(layoutInflater)
        val imageURL = "https://www.duckduckgo.com${url}"
        Glide.with(this).load(imageURL)
            .placeholder(R.drawable.ic_sphere_wireframe_10deg_6r).into(binding.characterImage)
        binding.characterDescription.text = characterDesctiption
        return binding.root
    }

    companion object {

        fun newInstance(characterName: String, url: String, characterDescription: String) =
            CharacterDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_CHARACTER_NAME, characterName)
                    putString(ARG_CHARACTER_DESCRIPTION, characterDescription)
                    putString(ARG_CHARACTER_URL, url)
                }
            }
    }

}
