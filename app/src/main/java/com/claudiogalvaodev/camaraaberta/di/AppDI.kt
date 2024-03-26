package com.claudiogalvaodev.camaraaberta.di

import com.claudiogalvaodev.camaraaberta.ui.screens.events.EventsViewModel
import com.claudiogalvaodev.camaraaberta.data.EventsRepository
import com.claudiogalvaodev.camaraaberta.data.client.Client
import com.claudiogalvaodev.camaraaberta.ui.screens.eventDetails.EventDetailsViewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val retrofitModule = module {
    single {
        Retrofit.Builder()
            .baseUrl("https://dadosabertos.camara.leg.br/api/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    single<Client> { get<Retrofit>().create(Client::class.java) }
}

val dataModule = module {
    single {
        EventsRepository(get())
    }
}

val viewModelModule = module {
    single {
        EventsViewModel(get())
    }

    single {
        EventDetailsViewModel(
            eventsRepository = get()
        )
    }
}

val appModules = listOf(
    retrofitModule,
    dataModule,
    viewModelModule
)