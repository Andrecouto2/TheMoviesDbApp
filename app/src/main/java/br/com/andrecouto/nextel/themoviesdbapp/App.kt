package br.com.andrecouto.nextel.themoviesdbapp

import android.app.Application
import br.com.andrecouto.nextel.themoviesdbapp.data.component.DaggerNetComponent
import br.com.andrecouto.nextel.themoviesdbapp.data.component.NetComponent
import br.com.andrecouto.nextel.themoviesdbapp.data.module.AppModule
import br.com.andrecouto.nextel.themoviesdbapp.data.module.NetModule
import br.com.andrecouto.nextel.themoviesdbapp.util.Constants

class App : Application() {
    var netComponent: NetComponent? = null
    override fun onCreate() {
        super.onCreate()
        netComponent = DaggerNetComponent.builder()
                .appModule(AppModule(this))
                .netModule(NetModule(Constants.BASE_URL))
                .build()
        // Salva a instância
        appInstance = this
    }

    companion object {
        // Singleton da classe Application
        private var appInstance: App? = null

        fun getInstance(): App {
            if (appInstance == null) {
                throw IllegalStateException("Configure a classe de Application no AndroidManifest.xml")
            }
            return appInstance!!
        }
    }
}