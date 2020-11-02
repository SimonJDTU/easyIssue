package com.example.easyissue.koin

import com.example.easyissue.StateManager
import org.koin.dsl.module

val appModule = module {
    single { StateManager( get() ) }
}