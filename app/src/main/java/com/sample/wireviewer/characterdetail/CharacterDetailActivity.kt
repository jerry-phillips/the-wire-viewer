package com.sample.wireviewer.characterdetail

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
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
                    putParcelable(
                        CharacterDetailFragment.ARG_CHARACTER,
                        intent.getParcelableExtra(CharacterDetailFragment.ARG_CHARACTER)
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
