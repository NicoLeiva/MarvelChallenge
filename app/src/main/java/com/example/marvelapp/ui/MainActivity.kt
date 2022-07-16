package com.example.marvelapp.ui

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.marvelapp.R
import com.example.marvelapp.databinding.ActivityMainBinding
import com.example.marvelapp.ui.adapter.ViewPagerAdapter
import com.facebook.login.LoginManager
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity(){

    private lateinit var binding: ActivityMainBinding
    private val adapter by lazy { ViewPagerAdapter(this ) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        saveDataSession()
        binding.viewPager.adapter = adapter
        createTabs()
    }

    private fun createTabs(){
        val tabLayoutMediator = TabLayoutMediator(binding.tabLayout,binding.viewPager
        ) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = getString(R.string.text_character)
                    tab.icon = getDrawable(R.drawable.ic_character)
                }
                1 -> {
                    tab.text = getString(R.string.text_events)
                    tab.icon = getDrawable(R.drawable.ic_event)
                }

            }
        }
        tabLayoutMediator.attach()
    }

   override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater:MenuInflater = menuInflater
        inflater.inflate(R.menu.close_session_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.close_session -> closeSession()
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {
        if(supportFragmentManager.backStackEntryCount>0){
            supportFragmentManager.popBackStack()
        }
    }
    private fun saveDataSession() {
        val bundle = intent.extras
        val email = bundle?.getString(getString(R.string.text_email))
        val provider = bundle?.getString(getString(R.string.text_provider))
        val prefs =
            getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
        prefs.putString(getString(R.string.text_email), email)
        prefs.putString(getString(R.string.text_provider), provider)
        prefs.apply()
    }

    private fun closeSession():  Boolean{
        val bundle = intent.extras
        val provider = bundle?.getString(getString(R.string.text_provider))
        val prefs =
            getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
        prefs.clear()
        prefs.apply()
        if (LoginActivity.ProviderType.FACEBOOK.name == provider)
            LoginManager.getInstance().logOut()
        FirebaseAuth.getInstance().signOut()
        finish()
        return true
    }
}