package com.deucate.sapii

import android.annotation.SuppressLint
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.deucate.sapii.home.HomeFragment
import com.deucate.sapii.invite.InviteFragment
import com.deucate.sapii.payout.PayoutFragment
import com.deucate.sapii.settings.SettingsFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val currentFragment = MutableLiveData<Fragment?>()
    private val currentTitle = MutableLiveData<String?>()
    private var title = String()
    private lateinit var viewModel: ViewModel

    private val fragments = listOf(HomeFragment(), InviteFragment(), PayoutFragment(), SettingsFragment())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        viewModel = ViewModelProviders.of(this).get(ViewModel::class.java)
        currentFragment.value = fragments[0]

        viewModel.points.observe(this, Observer {
            viewModel.updatePoints(it!!)
        })

        currentTitle.observe(this, Observer {
            supportActionBar!!.title = it!!
        })

        currentFragment.observe(this, Observer {
            checkAndLoadFragment(it!!)
        })
    }

    @SuppressLint("InflateParams")
    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                currentTitle.value = "Home"
                currentFragment.value = fragments[0]
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_invite -> {
                currentTitle.value = "Invite"
                currentFragment.value = fragments[1]
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_payout -> {
                currentTitle.value = "Payout"
                currentFragment.value = fragments[2]
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_settings -> {
                currentTitle.value = "Settings"
                currentFragment.value = fragments[3]
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    private fun checkAndLoadFragment(fragment: Fragment) {
        if (currentTitle.value != title) {
            loadFragment(fragment)
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment, fragment::class.java.name)
            .addToBackStack(null)
            .commit()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getPoints()
    }

}
