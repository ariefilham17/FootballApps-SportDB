package com.example.footballapps.views.fragments

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.view.*
import android.widget.*
import com.google.gson.Gson
import com.example.footballapps.R
import com.example.footballapps.R.array.league
import com.example.footballapps.helpers.coroutines.TestContextProvider
import com.example.footballapps.helpers.networks.ApiRepository
import com.example.footballapps.helpers.utils.invisible
import com.example.footballapps.helpers.utils.visible
import com.example.footballapps.models.dataclass.Team
import com.example.footballapps.presenters.TeamPresenter
import com.example.footballapps.views.activities.DetailTeamActivity
import com.example.footballapps.views.activities.SearchTeamActivity
import com.example.footballapps.views.adapters.TeamRecyclerViewAdapter
import com.example.footballapps.views.interfaces.TeamView
import kotlinx.android.synthetic.main.fragment_team_home.view.*
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.runOnUiThread
import org.jetbrains.anko.support.v4.startActivity


class TeamHomeFragment : androidx.fragment.app.Fragment(), TeamView {

    private var teams: MutableList<Team> = mutableListOf()
    private lateinit var leagueNameAPI: String
    private lateinit var presenter: TeamPresenter
    private lateinit var adapter: TeamRecyclerViewAdapter
    private lateinit var recyclerView: androidx.recyclerview.widget.RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: androidx.swiperefreshlayout.widget.SwipeRefreshLayout
    private lateinit var spinner: Spinner

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_team_home, container, false)
        // -------------------------------------------------------------------------------------------------------------
        val request = ApiRepository()
        val gson = Gson()
        val mLayoutManager = androidx.recyclerview.widget.GridLayoutManager(activity, 3)
        val divider = androidx.recyclerview.widget.DividerItemDecoration(context, mLayoutManager.orientation)
        setHasOptionsMenu(true)
        // -------------------------------------------------------------------------------------------------------------
        progressBar = rootView.progressBar_team
        recyclerView = rootView.recyclerView_team
        swipeRefresh = rootView.swipeRefresh_team
        spinner = rootView.spinner_team
        // -------------------------------------------------------------------------------------------------------------
        val connectivityManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        val connected: Boolean = activeNetwork?.isConnected == true
        // -------------------------------------------------------------------------------------------------------------
        if (connected) {
            presenter = TeamPresenter(this, request, gson,TestContextProvider())
            val spinnerItems = resources.getStringArray(league)
            // ---------------------------------------------------------------------------------------------------------
            val spinnerAdapter = ArrayAdapter(requireContext(), R.layout.support_simple_spinner_dropdown_item, spinnerItems)
            spinner.adapter = spinnerAdapter
            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                    leagueNameAPI = spinner.selectedItem.toString()
                    presenter.getTeamList(leagueNameAPI)
                }

                override fun onNothingSelected(parent: AdapterView<*>) {}
            }
            
        } else {
            Toast.makeText(context, "No Internet Connection", Toast.LENGTH_LONG).show()
        }
        adapter = TeamRecyclerViewAdapter(context, teams) {
            startActivity<DetailTeamActivity>(DetailTeamActivity.STRING_EXTRA_TEAM to it)
        }
        // -------------------------------------------------------------------------------------------------------------
        recyclerView.adapter = adapter
        recyclerView.layoutManager = mLayoutManager
        recyclerView.addItemDecoration(divider)
        // -------------------------------------------------------------------------------------------------------------
        swipeRefresh.onRefresh {
            progressBar.invisible()
            if (connected) {
                presenter.getTeamList(leagueNameAPI)
            } else {
                Toast.makeText(context, "No Internet Connection", Toast.LENGTH_LONG).show()
            }
        }
        // -------------------------------------------------------------------------------------------------------------
        return rootView
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_search, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.app_bar_search -> {
                val intent = Intent(context, SearchTeamActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
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

    override fun showData(dataTeam: List<Team>) {
        runOnUiThread {
            adapter.notifyDataSetChanged()
            swipeRefresh.isRefreshing = false
            teams.clear()
            teams.addAll(dataTeam)
        }}

}