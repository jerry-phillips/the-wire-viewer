package com.sample.wireviewer.characterdetail
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.sample.wireviewer.R
import com.sample.wireviewer.poko.Character
import kotlinx.android.synthetic.main.activity_item_detail.*
import kotlinx.android.synthetic.main.item_detail.view.*


class CharacterDetailFragment : Fragment(){

    private lateinit var selectedCharacter: Character

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        arguments?.let {
            if (it.containsKey(ARG_CHARACTER)) {
                selectedCharacter = it.getParcelable(ARG_CHARACTER)
            } else {
               failedToLoad()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.item_detail, container, false)
        activity?.toolbar_layout?.title = selectedCharacter.gettName()
        Glide.with(this).load(selectedCharacter.getIcon()!!.getURL())
            .placeholder(R.drawable.ic_sphere_wireframe_10deg_6r).into(rootView.characterImage)
        rootView.characterDescription.text = selectedCharacter.getDescription()
        return rootView
    }

    fun failedToLoad() {
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
        const val ARG_CHARACTER = "character"
    }
}
