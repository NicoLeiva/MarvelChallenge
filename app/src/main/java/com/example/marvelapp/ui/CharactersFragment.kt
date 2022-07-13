package com.example.marvelapp.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.marvelapp.CharacterViewModel
import com.example.marvelapp.R
import com.example.marvelapp.ui.adapter.RecyclerViewAdapter
import com.example.marvelapp.ViewModelFactory
import com.example.marvelapp.model.CharacterData
import com.example.marvelapp.databinding.FragmentCharactersBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class CharactersFragment : Fragment() {

    private lateinit var binding: FragmentCharactersBinding
    private lateinit var recyclerViewAdapter: RecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCharactersBinding.inflate(inflater, container, false)
        recyclerViewAdapter = RecyclerViewAdapter { data -> showCharacterDetails(data) }
        binding.recyclerView.apply {
            binding.recyclerView.layoutManager = LinearLayoutManager(activity)
            val decoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
            addItemDecoration(decoration)
            adapter = recyclerViewAdapter
        }
        initViewModel()
        return binding.root
    }


    private fun initViewModel() {
        val viewModelFactory = ViewModelFactory()
        val viewModel = viewModelFactory.create(CharacterViewModel::class.java)
        lifecycleScope.launchWhenCreated {
            viewModel.getListData().collectLatest {
                recyclerViewAdapter.submitData(it)
            }
        }
    }

    private fun showCharacterDetails(data: CharacterData) {
        /*val action = CharactersFragmentDirections
            .actionCharactersFragmentToDetailsCharacterFragment(data)
        NavHostFragment.findNavController(this).navigate(action)*/
    }


}