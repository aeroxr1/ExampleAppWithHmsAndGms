package aeroxr1.di

import aeroxr1.platform.PushUtility
import aeroxr1.platformgms.GmsPushUtility
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class PlatformGmsModule {

    @Binds
    abstract fun bindGmsPushUtility(impl:GmsPushUtility): PushUtility

}