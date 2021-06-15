package aeroxr1.di

import aeroxr1.platform.PushUtility
import aeroxr1.platformhms.HmsPushUtility
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class PlatformHmsModule {

    @Binds
    abstract fun bindHmsPushUtility(impl: HmsPushUtility): PushUtility

}