package com.tecadilabs.rfidr6

import com.tecadilabs.rfidr6.interfaces.RFIDService
import com.tecadilabs.rfidr6.services.RFIDServiceBase
import com.tecadilabs.rfidr6.ui.MainViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<RFIDService> {
        RFIDServiceBase()
    }

    viewModel {
        MainViewModel(get(), androidApplication())
    }
}