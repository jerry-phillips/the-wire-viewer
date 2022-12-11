package com.sample.wireviewer.ui

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.SearchView
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.sample.wireviewer.R
import com.sample.wireviewer.databinding.ActivityItemListBinding
import com.sample.wireviewer.model.Character
import com.sample.wireviewer.services.CharacterData
import com.sample.wireviewer.ui.characterdetail.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

const val QUERYVALUE = "queryValue"

fun AlertDialog.Builder.failureMessage() {
    val builder = AlertDialog.Builder(context)
    builder.setTitle(context.getString(R.string.failure))
    builder.setMessage(context.getString(R.string.something_went_wrong))
    builder.setNeutralButton(context.getString(android.R.string.ok)) { dialog, _ ->
        dialog.dismiss()
    }
    val dialog: AlertDialog = builder.create()
    dialog.show()
}

@AndroidEntryPoint
class CharacterListActivity : AppCompatActivity() {

    private val viewModel: CharacterListViewModel by viewModels()
    private var searchView: SearchView? = null
    private var searchQuery: CharSequence? = null
    private lateinit var binding: ActivityItemListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityItemListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        binding.toolbar.title = title

        if (savedInstanceState?.getCharSequence(QUERYVALUE) != null) {
            searchQuery = savedInstanceState.getCharSequence(QUERYVALUE) as CharSequence
        } else {
            showProgress(true)
            viewModel.fetchCharacters()
            observeCharactersFromViewModel()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search, menu)
        searchView = menu?.findItem(R.id.action_search)?.actionView as SearchView
        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                searchQuery = searchView?.query
                if (query.length > 2) {
                    viewModel.queryCharacters(query)
                }
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.isEmpty()) {
                    viewModel.fetchCharacters()
                    searchView?.isIconified = true
                }
                return false
            }
        })
        if (searchQuery?.isNotEmpty() == true) {
            searchView?.apply {
                setQuery(searchQuery, true)
                isIconified = false
                clearFocus()
            }
        }
        return true
    }

    private fun setupRecyclerView(characters: List<Character>) {
        binding.include.itemList.adapter = CharacterListAdapter(
            characters
        ) { wireCharacter ->

            binding.toolbar.title = wireCharacter.getCharacterName()

            if (isTablet()) {
                val fragment = CharacterDetailFragment.newInstance(
                    wireCharacter.getCharacterName(),
                    wireCharacter.icon?.url ?: "",
                    wireCharacter.getCharacterDescription()

                )

                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.item_detail_container, fragment)
                    .commit()
            } else {
                val intent = Intent(this, CharacterDetailActivity::class.java).apply {
                    putExtra(ARG_CHARACTER_NAME, wireCharacter.getCharacterName())
                    putExtra(ARG_CHARACTER_URL, wireCharacter.icon?.url ?: "")
                    putExtra(ARG_CHARACTER_DESCRIPTION, wireCharacter.getCharacterDescription())
                }
                this.startActivity(intent)
            }
        }
    }

    private fun showProgress(showProgress: Boolean) {
        if (showProgress) {
            binding.include.progressBar.visibility = View.VISIBLE
        } else {
            binding.include.progressBar.visibility = View.GONE
        }
    }

    private fun noResults() {
        val wiggle = AnimationUtils.loadAnimation(this, R.anim.wiggle)
        val search = window.decorView.findViewById<View>(android.R.id.content)
            .findViewById<View>(R.id.action_search)
        search.startAnimation(wiggle)
    }

    private fun observeCharactersFromViewModel() {
        lifecycleScope.launchWhenStarted {
            viewModel.characters.collectLatest { characterData ->
                showProgress(false)
                when (characterData) {
                    is CharacterData.Empty -> {}
                    is CharacterData.Error -> {
                        AlertDialog.Builder(this@CharacterListActivity).failureMessage()
                    }
                    is CharacterData.Success -> {
                        if (characterData.data.isEmpty()) {
                            noResults()
                        } else {
                            setupRecyclerView(characterData.data)
                        }
                    }
                }
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (searchView?.query?.isNotEmpty() == true) {
            outState.putCharSequence(QUERYVALUE, searchView?.query)
        }
    }

    private fun isTablet(): Boolean {
        return ((resources.configuration.screenLayout
                and Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE)
    }
}
