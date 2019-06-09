package com.sample.wireviewer.characterlist

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.SearchView
import android.widget.TextView
import com.sample.wireviewer.R

import com.sample.wireviewer.poko.RelatedTopic
import com.sample.wireviewer.characterdetail.CharacterDetailActivity
import com.sample.wireviewer.characterdetail.CharacterDetailFragment
import io.realm.RealmList
import kotlinx.android.synthetic.main.activity_item_list.*
import kotlinx.android.synthetic.main.item_list_content.view.*
import kotlinx.android.synthetic.main.item_list.*


class CharacterListActivity : AppCompatActivity(), CharacterListContract.WireListView {


    private var twoPane: Boolean = false
    private lateinit var presenter: CharacterListPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_list)

        setSupportActionBar(toolbar)
        toolbar.title = title

        if (item_detail_container != null) {
            twoPane = true
        }
        presenter = CharacterListPresenter(this, this)
        presenter.getData()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search, menu)
        val searchView = menu?.findItem(R.id.action_search)?.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                if (query.length > 2) {
                    presenter.queryCharacters(query)
                } else if (query.isEmpty()) {
                    presenter.getData()
                }
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.isEmpty()) {
                    presenter.getData()
                }
                return false
            }
        })
        return true
    }
    private fun setupRecyclerView( wireFrames: RealmList<RelatedTopic>) {
        item_list.adapter = SimpleItemRecyclerViewAdapter(
            this,
            wireFrames,
            twoPane
        )
    }
    override fun showProgress(isShowingProgress: Boolean) {
        if(isShowingProgress) {
            progressBar.visibility = View.VISIBLE
        } else{
            progressBar.visibility = View.GONE}

    }

    override fun updateDataSource(wireFrames: RealmList<RelatedTopic>) {
        setupRecyclerView(wireFrames)
    }

    override fun failedResponse() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.failure))
        builder.setMessage(getString(R.string.somethingwentwrong))
        builder.setNeutralButton(getString(android.R.string.ok)){dialog,_ ->
            dialog.dismiss()
        }
        val dialog: AlertDialog? = builder.create()
        dialog?.show()
    }

    override fun noResults() {
        val wiggle = AnimationUtils.loadAnimation(this, R.anim.wiggle)
        val search = window.decorView.findViewById<View>(android.R.id.content).findViewById<View>(R.id.action_search)
        search.startAnimation(wiggle)
    }

    class SimpleItemRecyclerViewAdapter(
        private val parentActivity: CharacterListActivity,
        private val wireFrames: RealmList<RelatedTopic>,
        private val twoPane: Boolean
    ) :
        RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder>() {

        private val onClickListener: View.OnClickListener

        init {
            onClickListener = View.OnClickListener { v ->
                val wireFrame = v.tag as RelatedTopic
                if (twoPane) {
                    val fragment = CharacterDetailFragment().apply {
                        arguments = Bundle().apply {
                            putString(CharacterDetailFragment.ARG_CHARACTER_ID, wireFrame.getFirstURL())
                        }
                    }
                    parentActivity.supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.item_detail_container, fragment)
                        .commit()
                } else {
                    val intent = Intent(v.context, CharacterDetailActivity::class.java).apply {
                        putExtra(CharacterDetailFragment.ARG_CHARACTER_ID, wireFrame.getFirstURL())
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
            val wireFrame = wireFrames[position]
            holder.idView.text = wireFrame?.gettName()

            with(holder.itemView) {
                tag = wireFrame
                setOnClickListener(onClickListener)
            }
        }

        override fun getItemCount() = wireFrames.size

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val idView: TextView = view.id_text
        }
    }
}
