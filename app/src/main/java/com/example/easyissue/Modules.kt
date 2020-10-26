package com.example.easyissue

import android.content.Context
import org.koin.dsl.module

val appModule = module {
    single { ResourceContext( get() ) }
    single { StateManager( get() ) }
}

data class ResourceContext(val context: Context)