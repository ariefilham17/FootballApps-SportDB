package com.example.footballapps.views.fragments


import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.gson.Gson
import com.example.footballapps.R
import com.example.footballapps.R.array.league
import com.example.footballapps.helpers.coroutines.TestContextProvider
import com.example.footballapps.helpers.networks.ApiRepository
import com.example.footballapps.helpers.utils.invisible
import com.example.footballapps.helpers.utils.visible
import com.example.footballapps.models.dataclass.Match
import com.example.footballapps.presenters.MatchPresenter
import com.example.footballapps.views.activities.DetailMatchActivity
import com.example.footballapps.views.adapters.MatchRecyclerViewAdapter
import com.example.footballapps.views.interfaces.MatchView
import kotlinx.android.synthetic.main.fragment_match_next.view.*
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.runOnUiThread
import org.jetbrains.anko.support.v4.startActivity

class MatchNextFragment : androidx.fragment.app.Fragment(), MatchView {

    private var matches: MutableList<Match> = mutableListOf()
    private lateinit var presenter: MatchPresenter
    private lateinit var adapter: MatchRecyclerViewAdapter
    private lateinit var recyclerView: androidx.recyclerview.widget.RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: androidx.swiperefreshlayout.widget.SwipeRefreshLayout
    private lateinit var spinner: Spinner
    private lateinit var leagueName: String
    private lateinit var leagueIdApi: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        // -----------------------------------------------------------------------------------------
        val rootView = inflater.inflate(R.layout.fragment_match_next, container, false)
        val request = ApiRepository()
        val gson = Gson()
        val mLayoutManager = androidx.recyclerview.widget.LinearLayoutManager(activity)
        val divider = androidx.recyclerview.widget.DividerItemDecoration(context, mLayoutManager.orientation)
        val api = "eventsnextleague.php"
        // -----------------------------------------------------------------------------------------
        progressBar = rootView.progressBar_NextMatch
        recyclerView = rootView.recyclerView_NextMatch
        swipeRefresh = rootView.swipeRefresh_NextMatch
        spinner = rootView.spinner_NextMatch
        // -----------------------------------------------------------------------------------------
        val connectivityManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        val connected: Boolean = activeNetwork?.isConnected == true
        // -----------------------------------------------------------------------------------------
        if (connected) {
            presenter = MatchPresenter(this, request, gson, TestContextProvider())
            val spinnerItems = resources.getStringArray(league)
            // ---------------------------------------------------------------------------------------------------------
            val spinnerAdapter = ArrayAdapter(requireContext(), R.layout.support_simple_spinner_dropdown_item, spinnerItems)
            spinner.adapter = spinnerAdapter
            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                    leagueName = spinner.selectedItem.toString()
                    when(leagueName){
                        "English Premier League" -> leagueIdApi = "4328"
                        "German Bundesliga" -> leagueIdApi = "4331"
                        "Italian Serie A" -> leagueIdApi = "4332"
                        "French Ligue 1" -> leagueIdApi = "4334"
                        "Spanish La Liga" -> leagueIdApi = "4335"
                        "Netherlands Eredivisie" -> leagueIdApi = "4337"
                    }
                    presenter.getMatchList(api,leagueIdApi)
                }

                override fun onNothingSelected(parent: AdapterView<*>) {}
            }
        } else {
            Toast.makeText(context, "No Internet Connection", Toast.LENGTH_LONG).show()
        }
        adapter = MatchRecyclerViewAdapter(context, matches){
            startActivity<DetailMatchActivity>(DetailMatchActivity.STRING_EXTRA_MATCH to it)
        }
        // -----------------------------------------------------------------------------------------
        recyclerView.adapter = adapter
        recyclerView.layoutManager = mLayoutManager
        recyclerView.addItemDecoration(divider)
        // -----------------------------------------------------------------------------------------
        swipeRefresh.onRefresh {
            progressBar.invisible()
            if (connected) {
                presenter = MatchPresenter(this, request, gson, TestContextProvider())
                presenter.getMatchList(api, leagueIdApi)
            } else {
                Toast.makeText(context, "No Internet Connection", Toast.LENGTH_LONG).show()
            }
        }

        return rootView
    }

    override fun showLoading() {
        runOnUiThread {
            progressBar.visible()
        }
    }

    override fun hideLoading() {
        runOnUiThread {
            progressBar.invisible()
        }
    }

    override fun showData(dataMatch: List<Match>) {
        runOnUiThread {
            adapter.notifyDataSetChanged()
            swipeRefresh.isRefreshing = false
            matches.clear()
            matches.addAll(dataMatch)
        }
    }

}
