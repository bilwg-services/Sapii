package com.deucate.sapii.home


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.deucate.sapii.R
import com.deucate.sapii.spinner.SpinActivity
import com.deucate.sapii.util.Utils
import kotlinx.android.synthetic.main.fragment_home.view.*

class HomeFragment : Fragment() {

    private lateinit var viewModel: HomeViewModel

    private lateinit var utils: Utils

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_home, container, false)
        utils = Utils(activity as Context)

        viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)

        val adapter = ModuleAdapter(viewModel)
        adapter.listner = object : ModuleAdapter.OnCallBack {
            override fun onClickCard(module: Module) {
                startActivity(Intent(activity, module.intent))
            }

        }

        val recyclerView = rootView.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter

        viewModel.modules.observe(this, Observer {
            adapter.notifyDataSetChanged()
        })

        addModule()

        viewModel.activityToStart.observe(this, Observer {
            activity!!.startActivity(it)
        })

        viewModel.error.observe(this, Observer {
            if (!it.isNullOrEmpty()) {
                utils.showAlertDialog("Error", it)
            } else {

            }
        })

        return rootView
    }

    private fun addModule() {
        viewModel.modules.value!!.add(
            Module(
                "Spin to earn credits",
                "Per hour get unlimited points with spins.",
                R.drawable.spin_win,
                SpinActivity::class.java as Class<*>
            )
        )
        viewModel.modules.value!!.add(
            Module(
                "Scratch to earn credits",
                "Per hour get unlimited points with scratch..",
                R.drawable.scartch_icon,
                SpinActivity::class.java as Class<*>
            )
        )
        viewModel.modules.value!!.add(
            Module(
                "Watch video",
                "Every 30 min to watch full video and get full credit.",
                R.drawable.spin_win,
                SpinActivity::class.java as Class<*>
            )
        )

        viewModel.modules.value!!.add(
            Module(
                "Share to earn credit",
                "Every share get 30 credit",
                R.drawable.spin_win,
                SpinActivity::class.java as Class<*>
            )
        )

        viewModel.modules.value!!.add(
            Module(
                "Share on Facebook Timeline",
                "Share on Facebook with your friends",
                R.drawable.spin_win,
                SpinActivity::class.java as Class<*>
            )
        )
    }


}
