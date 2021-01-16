package com.example.footballapps.views.fragments


import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import com.google.gson.Gson
import com.example.footballapps.R
import com.example.footballapps.helpers.coroutines.TestContextProvider
import com.example.footballapps.helpers.networks.ApiRepository
import com.example.footballapps.helpers.utils.BundleHelper
import com.example.footballapps.helpers.utils.invisible
import com.example.footballapps.helpers.utils.visible
import com.example.footballapps.models.dataclass.Player
import com.example.footballapps.presenters.PlayerPresenter
import com.example.footballapps.views.activities.DetailPlayerActivity
import com.example.footballapps.views.adapters.PlayerRecyclerViewAdapter
import com.example.footballapps.views.interfaces.PlayerView
import kotlinx.android.synthetic.main.fragment_team_player.view.*
import org.jetbrains.anko.support.v4.runOnUiThread
import org.jetbrains.anko.support.v4.startActivity


class TeamPlayerFragment : androidx.fragment.app.Fragment(), PlayerView {

    private var players: MutableList<Player> = mutableListOf()
    private lateinit var presenter: PlayerPresenter
    private lateinit var adapter: PlayerRecyclerViewAdapter
    private lateinit var recyclerView: androidx.recyclerview.widget.RecyclerView
    private lateinit var progressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        // -----------------------------------------------------------------------------------------
        val rootView = inflater.inflate(R.layout.fragment_team_player, container, false)
        val request = ApiRepository()
        val gson = Gson()
        val mLayoutManager = androidx.recyclerview.widget.GridLayoutManager(activity, 3)
        val divider = androidx.recyclerview.widget.DividerItemDecoration(context, mLayoutManager.orientation)
        // -----------------------------------------------------------------------------------------
        progressBar = rootView.progressBar_player
        recyclerView = rootView.recyclerView_player
        // -----------------------------------------------------------------------------------------
        val connectivityManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        val connected: Boolean = activeNetwork?.isConnected == true
        // -----------------------------------------------------------------------------------------
        if (connected) {
            presenter = PlayerPresenter(this, request, gson, TestContextProvider())
            presenter.getPlayerList(BundleHelper.teamIdHelper)
        } else {
            Toast.makeText(context, "No Internet Connection", Toast.LENGTH_LONG).show()
        }
        adapter = PlayerRecyclerViewAdapter(context, players){
            startActivity<DetailPlayerActivity>(DetailPlayerActivity.STRING_EXTRA_PLAYER to it)
        }
        // -----------------------------------------------------------------------------------------
        recyclerView.adapter = adapter
        recyclerView.layoutManager = mLayoutManager
        recyclerView.addItemDecoration(divider)
        // -----------------------------------------------------------------------------------------
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

    override fun showData(dataPlayer: List<Player>) {
        runOnUiThread {
            players.clear()
            players.addAll(dataPlayer)
            adapter.notifyDataSetChanged()
        }
    }
}
