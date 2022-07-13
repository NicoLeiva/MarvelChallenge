package com.example.marvelapp.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.marvelapp.ui.CharactersFragment
import com.example.marvelapp.ui.EventsFragment

class ViewPagerAdapter(fragment: FragmentActivity) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> CharactersFragment()
            1 -> EventsFragment()
            2 -> CharactersFragment()
            else -> CharactersFragment()
        }
    }
}