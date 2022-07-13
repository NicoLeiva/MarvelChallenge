package com.example.marvelapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.marvelapp.connection.RetroInstance
import com.example.marvelapp.connection.RetroService
import com.example.marvelapp.repository.CharacterRepositoryImpl
import com.example.marvelapp.ui.adapter.CharacterRemoteDataSourceImpl

class ViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when(modelClass) {
            CharacterViewModel::class.java -> {
                val apiService = RetroInstance.getRetroInstance().create(RetroService::class.java)
                            CharacterViewModel (
                            CharacterRepositoryImpl(apiService,
                                CharacterRemoteDataSourceImpl(apiService))

                )
            }
            else -> throw IllegalArgumentException("")
        } as T

}