package com.sample.wireviewer.characterlist

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.SearchView
import com.sample.wireviewer.R
import com.sample.wireviewer.poko.Character
import kotlinx.android.synthetic.main.activity_item_list.*
import kotlinx.android.synthetic.main.item_list.*


class CharacterListActivity : AppCompatActivity(){

    private var twoPane: Boolean = false
    private lateinit var modelList:CharacterListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_list)

        setSupportActionBar(toolbar)
        toolbar.title = title

        if (item_detail_container != null) {
            twoPane = true
        }
        showProgress(true)
        modelList = ViewModelProviders.of(this).get(CharacterListViewModel::class.java)
        getCharactersFromViewModel()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search, menu)
        val searchView = menu?.findItem(R.id.action_search)?.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                if (query.length > 2) {
                    modelList.queryCharacters(query).observe(this@CharacterListActivity, Observer<List<Character>>{ characters ->
                        if (characters!!.isEmpty()){
                            noResults()
                        } else{
                            setupRecyclerView(characters)
                        }
                    })
                }
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.isEmpty()) {
                   getCharactersFromViewModel()
                }
                return false
            }
        })
        return true
    }
    private fun setupRecyclerView( characters:List<Character>) {
        item_list.adapter = CharacterListAdapter(
            this,
            characters,
            twoPane
        )
    }
    private fun showProgress(isShowingProgress: Boolean) {
        if(isShowingProgress) {
            progressBar.visibility = View.VISIBLE
        } else{
            progressBar.visibility = View.GONE}

    }


     private fun failedResponse() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.failure))
        builder.setMessage(getString(R.string.somethingwentwrong))
        builder.setNeutralButton(getString(android.R.string.ok)){dialog,_ ->
            dialog.dismiss()
        }
        val dialog: AlertDialog? = builder.create()
        dialog?.show()
    }

   private fun noResults() {
        val wiggle = AnimationUtils.loadAnimation(this, R.anim.wiggle)
        val search = window.decorView.findViewById<View>(android.R.id.content).findViewById<View>(R.id.action_search)
        search.startAnimation(wiggle)
    }

    private fun getCharactersFromViewModel(){
        modelList.getCharacters().observe(this, Observer<List<Character>>{ characters ->
            showProgress(false)
            if (characters?.isNotEmpty() as Boolean) {
                 setupRecyclerView(characters)
            } else {
                failedResponse()
            }
        })
    }


}
