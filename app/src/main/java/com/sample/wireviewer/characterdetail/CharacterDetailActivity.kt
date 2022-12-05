package com.sample.wireviewer.characterdetail

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.sample.wireviewer.R
import com.sample.wireviewer.characterlist.CharacterListActivity
import com.sample.wireviewer.databinding.ActivityItemDetailBinding
import com.sample.wireviewer.model.Character

class CharacterDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityItemDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityItemDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.detailToolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (savedInstanceState == null) {
            val fragment = CharacterDetailFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(
                        ARG_CHARACTER,
                        intent.extras?.getParcelable(ARG_CHARACTER)
                    )
                }
            }

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
