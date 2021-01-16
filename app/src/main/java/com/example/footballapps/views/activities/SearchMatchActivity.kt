package com.example.footballapps.views.activities

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import com.google.gson.Gson
import com.example.footballapps.R
import com.example.footballapps.helpers.coroutines.TestContextProvider
import com.example.footballapps.helpers.networks.ApiRepository
import com.example.footballapps.helpers.utils.invisible
import com.example.footballapps.helpers.utils.visible
import com.example.footballapps.models.dataclass.Match
import com.example.footballapps.presenters.SearchMatchPresenter
import com.example.footballapps.views.adapters.MatchRecyclerViewAdapter
import com.example.footballapps.views.interfaces.SearchMatchView
import kotlinx.android.synthetic.main.activity_match_search.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.support.v4.onRefresh

class SearchMatchActivity : AppCompatActivity(), SearchMatchView {

    private var match: MutableList<Match> = mutableListOf()
    private lateinit var presenter: SearchMatchPresenter
    private lateinit var adapter: MatchRecyclerViewAdapter
    // -----------------------------------------------------------------------------------------------------------------
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match_search)
        // -------------------------------------------------------------------------------------------------------------
        recyclerview_searchmatch.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        textview_searchmatch_null.invisible()
        // -------------------------------------------------------------------------------------------------------------
        val request = ApiRepository()
        val gson = Gson()
        presenter = SearchMatchPresenter(this, request, gson, TestContextProvider())
        // -------------------------------------------------------------------------------------------------------------

        edittext_searchmatch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                if (edittext_searchmatch.text.isNotEmpty()){
                    swiperefresh_searchmatch.isRefreshing = true
                    presenter.getSearchMatchesList(edittext_searchmatch.text.toString())
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })

        adapter = MatchRecyclerViewAdapter(this, match){
            startActivity<DetailMatchActivity>(DetailMatchActivity.STRING_EXTRA_MATCH to it)
        }
        recyclerview_searchmatch.adapter = adapter

        // -------------------------------------------------------------------------------------------------------------
        iv_back.onClick {
            onBackPressed()
        }

        iv_close.onClick {
            edittext_searchmatch.text.clear()
            recyclerview_searchmatch.invisible()
            textview_searchmatch_null.visible()
        }
        // -------------------------------------------------------------------------------------------------------------
        swiperefresh_searchmatch.onRefresh {
            swiperefresh_searchmatch.isRefreshing = false
        }
        // -------------------------------------------------------------------------------------------------------------
        checkConnection()
    }

    private fun checkConnection() {
        val connectivityManager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        val isConnected: Boolean = activeNetwork?.isConnected == true
        if (!isConnected){
            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_LONG).show()
            swiperefresh_searchmatch.isRefreshing = false
        }
    }

    override fun showSearchMatchList(data: List<Match>) {
        runOnUiThread {
            match.clear()
            match.addAll(data)
            adapter.notifyDataSetChanged()
            recyclerview_searchmatch.visible()
            textview_searchmatch_null.invisible()
            swiperefresh_searchmatch.isRefreshing = false
        }
    }

    override fun showSearchMatchListNull() {
        runOnUiThread {
            recyclerview_searchmatch.invisible()
            textview_searchmatch_null.visible()
            swiperefresh_searchmatch.isRefreshing = false
        }
    }
}