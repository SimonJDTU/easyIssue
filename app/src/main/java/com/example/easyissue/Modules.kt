package com.example.easyissue

import org.koin.dsl.module

val appModule = module {
    single { StateManager( get() ) }
}