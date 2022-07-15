package com.example.marvelapp.repository

import org.koin.dsl.module

object RepositoryModules {
    private val repositoryModule = module {
        single<ICharacterRepository>{
            CharacterRepositoryImpl(get())
        }
    }
    val all = arrayOf(repositoryModule)
}