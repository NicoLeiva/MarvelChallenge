package com.example.marvelapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.marvelapp.CharacterViewModel
import com.example.marvelapp.R
import com.example.marvelapp.databinding.FragmentCharactersBinding
import com.example.marvelapp.model.CharacterData
import com.example.marvelapp.ui.adapter.RecyclerViewAdapter
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.viewmodel.ext.android.viewModel

class CharactersFragment : Fragment() {

    private val viewModel by viewModel<CharacterViewModel>()
    private lateinit var binding: FragmentCharactersBinding
    private lateinit var recyclerViewAdapter: RecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCharactersBinding.inflate(inflater, container, false)
        createView()
        initViewModel()
        return binding.root
    }

    private fun createView(){
        recyclerViewAdapter = RecyclerViewAdapter { data -> showCharacterDetails(data) }
        binding.recyclerView.apply {
            binding.recyclerView.layoutManager = LinearLayoutManager(activity)
            val decoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
            addItemDecoration(decoration)
            adapter = recyclerViewAdapter
        }
    }
    private fun initViewModel() {
        lifecycleScope.launchWhenCreated {
            viewModel.getListData().collectLatest {
                recyclerViewAdapter.submitData(it)
            }
        }
    }

    private fun showCharacterDetails(data: CharacterData) {
        val transaction = activity?.supportFragmentManager?.beginTransaction()
        transaction?.replace(R.id.content_placeholder, DetailsCharacterFragment.newInstance(data))
        transaction?.addToBackStack("")
        transaction?.commit()
    }
}