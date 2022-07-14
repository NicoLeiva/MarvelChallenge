package com.example.marvelapp.ui.adapter

import org.koin.dsl.module

object PagingModules {
    private val pagingModule = module {
        single {
            CharacterPagingSource(get())
        }
        single {
            CharacterRemoteDataSource(get())
        }
    }
    val all = arrayOf(pagingModule)
}