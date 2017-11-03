package br.com.andrecouto.nextel.themoviesdbapp.data.module

import br.com.andrecouto.nextel.themoviesdbapp.ui.contract.MainScreenContract
import br.com.andrecouto.nextel.themoviesdbapp.util.CustomScope
import dagger.Module
import dagger.Provides

@Module
class MainScreenModule(mView: MainScreenContract.View) {
    private val mView: MainScreenContract.View

    init {
        this.mView = mView
    }

    @Provides
    @CustomScope
    internal fun providesMainScreenContractView(): MainScreenContract.View {
        return mView
    }
}