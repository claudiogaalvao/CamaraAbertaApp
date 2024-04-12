package com.claudiogalvaodev.camaraaberta.di

import com.claudiogalvaodev.camaraaberta.ui.screens.events.EventsViewModel
import com.claudiogalvaodev.camaraaberta.data.repository.EventsRepository
import com.claudiogalvaodev.camaraaberta.data.client.Client
import com.claudiogalvaodev.camaraaberta.data.repository.PropositionsRepository
import com.claudiogalvaodev.camaraaberta.ui.screens.eventDetails.EventDetailsViewModel
import com.claudiogalvaodev.camaraaberta.ui.screens.propositionDetails.PropositionDetailsViewModel
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
    single {
        PropositionsRepository(get())
    }
}

val viewModelModule = module {
    factory {
        EventsViewModel(get())
    }

    factory { (eventId: Int) ->
        EventDetailsViewModel(
            eventId = eventId,
            eventsRepository = get()
        )
    }

    factory { (propositionId: Int) ->
        PropositionDetailsViewModel(
            propositionId = propositionId,
            repository = get()
        )
    }
}

val appModules = listOf(
    retrofitModule,
    dataModule,
    viewModelModule
)