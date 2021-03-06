package chuck.com.challenge.di.modules

import chuck.com.challenge.data.remote.JokesRemoteDataSource
import chuck.com.challenge.data.remote.network.ChuckNorrisAPI
import chuck.com.challenge.data.repositories.JokesRepository
import chuck.com.challenge.helpers.SharedPreferencesHelper
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import javax.inject.Named

@Module
object RepositoryModule {

    @JvmStatic
    @Provides
    fun provideJokeRepository(remoteDataSource: JokesRemoteDataSource, sharedPreferencesHelper: SharedPreferencesHelper) = JokesRepository(remoteDataSource, sharedPreferencesHelper)

    @JvmStatic
    @Provides
    fun provideJokesRemoteDataSource(chuckNorrisAPI: ChuckNorrisAPI, @Named(NAME_ANDROID_SCHEDULER_MAIN_THREAD) uiScheduler: Scheduler) = JokesRemoteDataSource(chuckNorrisAPI, uiScheduler)
}