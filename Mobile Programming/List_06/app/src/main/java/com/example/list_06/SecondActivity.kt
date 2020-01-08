package com.example.list_06

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_second.*
import kotlinx.android.synthetic.main.content_second.*

class SecondActivity : AppCompatActivity() {

    private lateinit var mListAdapter: ItemListAdapter

    val client by lazy {
        ArticlesApiClient.create()
    }

    var disposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        setSupportActionBar(toolbar)

        buttonToMap.setOnClickListener{
            val intent = Intent(this, MapsActivity::class.java)
            startActivity(intent)
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        showArticles()
    }

    /* get list of articles */
    private fun showArticles() {

        disposable = client.getArticles()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result -> setupRecycler(result) },
                { error -> Log.e("ERROR", error.message) }
            )

    }

    private fun showArticle(id: Int) {

        disposable = client.getArticle(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result -> Log.v("ARTICLE ID ${id}: ", "" + result) },
                { error -> Log.e("ERROR", error.message) }
            )

    }

    private fun postArticle(article: Article) {

        disposable = client.addArticle(article)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result -> Log.v("POSTED ARTICLE", "" + article ) },
                { error -> Log.e("ERROR", error.message ) }
            )
    }

    fun setupRecycler(articleList: List<Article>) {
        articles_recycler.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        articles_recycler.layoutManager = layoutManager
        articles_recycler.adapter = ItemListAdapter(articleList){
            Log.v("Article", it.id.toString())
        }
    }

    override fun onPause() {
        super.onPause()
        disposable?.dispose()
    }

}
