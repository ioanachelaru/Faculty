package com.example.list_06

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_second.*
import kotlinx.android.synthetic.main.content_second.*

class SecondActivity : AppCompatActivity() {

    private lateinit var mListAdapter: ItemListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        setSupportActionBar(toolbar)

        setupList()

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setupList() {
        val items = listOf(
            Item("1", "BLABLA"),
            Item("2", "sdgl;shdalsddjjsfs"),
            Item("3", "GFGHJKLKJLK::LNM")
        )
        mListAdapter = ItemListAdapter(items)
        itemList.adapter = mListAdapter
    }

}
