package br.com.andrecouto.nextel.themoviesdbapp

import android.app.Application
import br.com.andrecouto.nextel.themoviesdbapp.data.component.NetComponent
import br.com.andrecouto.nextel.themoviesdbapp.data.module.AppModule
import br.com.andrecouto.nextel.themoviesdbapp.data.module.NetModule

class App : Application() {
    var netComponent: NetComponent? = null
    override fun onCreate() {
        super.onCreate()
        netComponent = DaggerNetComponent.builder()
                .appModule(AppModule(this))
                .netModule(NetModule("https://api.themoviedb.org/3/movie/now_playing"))
                .build()
    }
}