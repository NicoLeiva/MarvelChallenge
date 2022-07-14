package com.example.marvelapp.ui

import com.example.marvelapp.CharacterViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object UIModules {
    private val module = module {
        viewModel{
            CharacterViewModel(get())
        }
    }
    val all = arrayOf(module)
}