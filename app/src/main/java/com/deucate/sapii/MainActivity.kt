package com.deucate.sapii

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.deucate.sapii.home.HomeFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    @SuppressLint("InflateParams")
    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                supportActionBar!!.title = "Home"
                val fragment = HomeFragment()
                checkAndLoadFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_invite -> {
                supportActionBar!!.title = "Invite"
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_payout -> {
                supportActionBar!!.title = "Payout"
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_settings -> {
                supportActionBar!!.title = "Settings"
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    private fun checkAndLoadFragment(fragment: Fragment) {
        val savedFragment = supportFragmentManager.findFragmentByTag(fragment::class.java.name)
        if (savedFragment != null) {
            loadFragment(savedFragment)
            Log.d("---->", "Loading fragment from stack ${fragment::class.java}")
        } else {
            loadFragment(fragment)
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment, fragment::class.java.name)
            .addToBackStack(null)
            .commit()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

}
