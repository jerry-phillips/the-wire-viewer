package com.sample.wireviewer.ui.characterdetail

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
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
        setCharacterImage(imageURL)
        binding.characterDescription.text = characterDesctiption
        return binding.root
    }

    private fun setCharacterImage(url: String) {
        Glide.with(this)
            .load(url)
            .addListener((object: RequestListener<Drawable> {
                override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean) =
                    false

                override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                    resource?.let {
                        val width = resource.intrinsicWidth
                        val height = resource.intrinsicHeight
                        if (height > 1 && width > 1) {
                            binding.characterImage.setImageDrawable(resource)
                        } else {
                            binding.characterImage.setImageResource(R.drawable.ic_sphere_wireframe_10deg_6r)
                        }
                    }
                    return false
                }
            })
            ).preload()

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
