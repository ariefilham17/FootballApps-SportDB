package com.example.footballapps.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.footballapps.R
import com.example.footballapps.helpers.utils.BundleHelper
import kotlinx.android.synthetic.main.fragment_team_overview.view.*

class TeamOverviewFragment : androidx.fragment.app.Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_team_overview, container, false)
        // -----------------------------------------------------------------------------------------
        rootView.textview_team_overview.text = BundleHelper.teamOverviewHelper
        // -----------------------------------------------------------------------------------------
        return rootView
    }

}