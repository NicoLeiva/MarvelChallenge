package com.example.marvelapp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.marvelapp.model.CharacterData
import com.example.marvelapp.model.ResponseEvent
import com.example.marvelapp.repository.ICharacterRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CharacterViewModel(private var repository: ICharacterRepository): ViewModel() {

    private lateinit var responseEvent : MutableLiveData<EventState>

    fun getListData(): Flow<PagingData<CharacterData>>{
        return repository.getCharacterData().cachedIn(viewModelScope)
    }

    private fun onError(message: String) {
        responseEvent.postValue(EventState.Error(message))
    }

    fun getResponseEvent(): MutableLiveData<EventState>{
        if(!::responseEvent.isInitialized){
            responseEvent = MutableLiveData<EventState>()
            responseEvent.postValue(EventState.Loading(true))
        }
        return responseEvent
    }
    fun getEventData() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val response = repository.getEventData()
                if (response.isSuccessful) {
                    responseEvent.postValue(response.body()?.let { EventState.Success(it) })
                } else {
                    onError("Error : ${response.message()} ")
                }
            }
        }
    }

    sealed class EventState{
        class Success(val response: ResponseEvent):EventState()
        class Error(val error:String):EventState()
        class Loading(val loading:Boolean):EventState()
    }
}

