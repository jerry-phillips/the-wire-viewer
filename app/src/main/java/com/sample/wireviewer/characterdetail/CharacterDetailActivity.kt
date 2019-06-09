package com.sample.wireviewer.characterdetail

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.sample.wireviewer.R
import com.sample.wireviewer.characterlist.CharacterListActivity
import kotlinx.android.synthetic.main.activity_item_detail.*


class CharacterDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_detail)
        setSupportActionBar(detail_toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (savedInstanceState == null) {
            val fragment = CharacterDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(
                        CharacterDetailFragment.ARG_CHARACTER_ID,
                        intent.getStringExtra(CharacterDetailFragment.ARG_CHARACTER_ID)
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
