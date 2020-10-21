package com.example.easyissue

import android.content.Context
import org.koin.dsl.module

val appModule = module {
    single { PreferenceHelper( get() ) }
    single { ResourceContext( get() ) }
}

data class ResourceContext(val context: Context)