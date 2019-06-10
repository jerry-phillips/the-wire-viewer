package com.sample.wireviewer.characterdetail

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.sample.wireviewer.R
import com.sample.wireviewer.poko.RelatedTopic
import kotlinx.android.synthetic.main.activity_item_detail.*
import kotlinx.android.synthetic.main.item_detail.view.*


class CharacterDetailFragment : Fragment(), CharacterDetailContract.WireDetailView {


    private lateinit var wireCharacterTag: String
    private lateinit var characterDetailPresenter: CharacterDetailPresenter
    private lateinit var characterImageView: ImageView
    private lateinit var characterDescriptionText: TextView

    override fun updateView(wireCharacter: RelatedTopic) {
        activity?.toolbar_layout?.title = wireCharacter.gettName()
        Glide.with(this).load(wireCharacter.getIcon()!!.getURL())
                .placeholder(R.drawable.ic_sphere_wireframe_10deg_6r).into(characterImageView)

        characterDescriptionText.text = wireCharacter.getDescription()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            if (it.containsKey(ARG_CHARACTER_ID)) {
                wireCharacterTag = it.getString(ARG_CHARACTER_ID)
                characterDetailPresenter = activity?.let { it1 -> CharacterDetailPresenter(it1, this)} as CharacterDetailPresenter

            } else {
               failedResponse()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.item_detail, container, false)
        characterImageView = rootView.characterImage
        characterDescriptionText = rootView.characterDescription
        characterDetailPresenter.getCharacter(wireCharacterTag)


        return rootView
    }
    override fun failedResponse() {
        val builder = activity?.let { it1 -> AlertDialog.Builder(it1) }
        builder?.setTitle(getString(R.string.failure))
        builder?.setMessage(getString(R.string.somethingwentwrong))
        builder?.setNeutralButton(getString(android.R.string.ok)){dialog,_ ->
            dialog.dismiss()
        }
        val dialog: AlertDialog? = builder?.create()
        dialog?.show()
    }


    companion object {
        const val ARG_CHARACTER_ID = "character_id"
    }
}
