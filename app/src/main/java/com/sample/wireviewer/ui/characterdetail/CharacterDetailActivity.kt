package com.sample.wireviewer.ui.characterdetail

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.sample.wireviewer.R
import com.sample.wireviewer.databinding.ActivityItemDetailBinding
import com.sample.wireviewer.ui.CharacterListActivity

const val ARG_CHARACTER_NAME = "character_name"
const val ARG_CHARACTER_DESCRIPTION = "character_description"
const val ARG_CHARACTER_URL = "character_url"

class CharacterDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityItemDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityItemDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.detailToolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (savedInstanceState == null) {
            val fragment = CharacterDetailFragment.newInstance(
                intent.extras?.getString(ARG_CHARACTER_NAME) ?: "",
                intent.extras?.getString(ARG_CHARACTER_URL) ?: "",
                intent.extras?.getString(ARG_CHARACTER_DESCRIPTION) ?: ""

            )

            supportFragmentManager.beginTransaction()
                .add(R.id.item_detail_container, fragment)
                .commit()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {
            android.R.id.home -> {
                navigateUpTo(Intent(this, CharacterListActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
}
