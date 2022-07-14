package com.example.marvelapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.marvelapp.*
import com.example.marvelapp.databinding.FragmentEventsBinding
import com.example.marvelapp.model.Event
import com.example.marvelapp.ui.adapter.ExpansibleListViewAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class EventsFragment : Fragment() {

    private lateinit var listViewAdapter: ExpansibleListViewAdapter
    private  var headerList: List<Event> = ArrayList()
    private  var detailsList: HashMap<String, List<Event>> = HashMap()
    private lateinit var binding: FragmentEventsBinding

    private val viewModel by viewModel<CharacterViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEventsBinding.inflate(inflater, container, false)
        initViewModel()
        return binding.root
    }

    private fun initViewModel(){
        viewModel.responseEvent.observe(viewLifecycleOwner){
            when(it) {
                is CharacterViewModel.EventState.Error -> showErrorAlert(it.error)
                is CharacterViewModel.EventState.Loading -> binding.progressBar.isVisible = it.loading
                is CharacterViewModel.EventState.Success -> createExpansibleList(it.response.data.results)
            }
        }
        viewModel.getEventData()
    }

    private fun createExpansibleList(listEvent: List<Event>){
        for ((i, e: Event) in listEvent.withIndex()){
            (headerList as ArrayList<Event> ).add(e)
            val details1: MutableList<Event> = ArrayList()
            details1.add(e)
            detailsList[headerList[i].id] = details1
        }
        listViewAdapter = ExpansibleListViewAdapter(requireContext(), sortList(headerList), detailsList)
        binding.eListView.visibility = View.VISIBLE
        binding.progressBar.visibility = View.INVISIBLE
        binding.eListView.setAdapter(listViewAdapter)
    }

    private fun sortList(headerList: List<Event>): List<Event>{
        var headerListSort: List<Event> = headerList
        if(headerListSort.size > 25)
            headerListSort = headerListSort.subList(0,24)
        return headerListSort.sortedBy { it.modified }
    }

    private fun showErrorAlert(msj:String) {
        val builder = context?.let { AlertDialog.Builder(it) }
        builder?.setTitle(getString(R.string.title_error))
        builder?.setMessage(msj)
        builder?.setPositiveButton(getString(R.string.btn_accept),null)
        val dialog: AlertDialog = builder?.create()!!
        dialog.show()
    }
}