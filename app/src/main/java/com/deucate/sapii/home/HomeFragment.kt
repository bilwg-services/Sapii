package com.deucate.sapii.home


import android.app.Activity
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
import com.deucate.sapii.Constants
import com.deucate.sapii.invite.InviteActivity
import com.deucate.sapii.R
import com.deucate.sapii.ViewModel
import com.deucate.sapii.video.VideoActivity
import com.deucate.sapii.scatch.ScratchActivity
import com.deucate.sapii.spinner.SpinActivity
import com.deucate.sapii.util.Utils
import kotlinx.android.synthetic.main.fragment_home.view.*
import com.deucate.sapii.facebook.FacebookActivity
import java.lang.NullPointerException


class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var viewModel: ViewModel

    private lateinit var utils: Utils
    private val constants = Constants()

    companion object {
        const val requestCode = 100
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_home, container, false)
        utils = Utils(activity as Context)

        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)

        val adapter = ModuleAdapter(homeViewModel)
        adapter.listner = object : ModuleAdapter.OnCallBack {
            override fun onClickCard(module: Module) {
                val intent = Intent(activity, module.intent)
                intent.putExtra(constants.points, viewModel.points.value)
                startActivityForResult(intent, requestCode)
            }

        }

        val recyclerView = rootView.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter

        homeViewModel.modules.observe(this, Observer {
            adapter.notifyDataSetChanged()
        })

        if (homeViewModel.modules.value!!.size == 0) {
            addModule()
        }

        homeViewModel.activityToStart.observe(this, Observer {
            activity!!.startActivity(it)
        })

        homeViewModel.error.observe(this, Observer {
            if (!it.isNullOrEmpty()) {
                utils.showAlertDialog("Error", it)
            } else {

            }
        })

        return rootView
    }

    private fun addModule() {
        homeViewModel.modules.value!!.add(
                Module(
                        "Spin to earn credits",
                        "Per hour get unlimited points with spins.",
                        R.drawable.spin_win,
                        SpinActivity::class.java as Class<*>
                )
        )
        homeViewModel.modules.value!!.add(
                Module(
                        "Scratch to earn credits",
                        "Per hour get unlimited points with scratch..",
                        R.drawable.scartch_icon,
                        ScratchActivity::class.java as Class<*>
                )
        )
        homeViewModel.modules.value!!.add(
                Module(
                        "Watch video",
                        "Every 30 min to watch full video and get full credit.",
                        R.drawable.video,
                        VideoActivity::class.java as Class<*>
                )
        )

        homeViewModel.modules.value!!.add(
                Module(
                        "Share to earn credit",
                        "Every share get 30 credit",
                        R.drawable.credit_bag,
                        InviteActivity::class.java as Class<*>
                )
        )

        homeViewModel.modules.value!!.add(
                Module(
                        "Share on Facebook Timeline",
                        "Share on Facebook with your friends",
                        R.drawable.facebook,
                        FacebookActivity::class.java as Class<*>
                )
        )
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(activity!!).get(ViewModel::class.java)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            requestCode -> {
                try {
                    viewModel.points.value = data!!.getLongExtra(constants.points, viewModel.points.value!!)
                } catch (e: KotlinNullPointerException) {
                    e.printStackTrace()
                }
            }
        }
    }
}
