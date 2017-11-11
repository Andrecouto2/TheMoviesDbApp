package br.com.andrecouto.nextel.themoviesdbapp.data.module

import br.com.andrecouto.nextel.themoviesdbapp.ui.contract.MainScreenContract
import br.com.andrecouto.nextel.themoviesdbapp.util.CustomScope
import dagger.Module
import dagger.Provides

@Module
class MainScreenModule(dView: MainScreenContract.MainView) {
    private val dView: MainScreenContract.MainView


    init {
        this.dView = dView
    }

    @Provides
    @CustomScope
    internal fun providesMainScreenContractView(): MainScreenContract.MainView {
        return dView
    }

}