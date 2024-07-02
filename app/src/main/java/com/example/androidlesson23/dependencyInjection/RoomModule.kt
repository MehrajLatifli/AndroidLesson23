package com.example.androidlesson23.dependencyInjection

import android.app.Application
import androidx.room.Room
import com.example.androidlesson23.source.local.BasketDatabase
import com.example.androidlesson23.source.local.LocalBasketDAO
import com.example.androidlesson23.source.local.LocalProductDAO
import com.example.androidlesson23.source.local.ProductDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    fun provideLocalDatabase(application: Application): ProductDatabase {
        return Room.databaseBuilder(
            application.applicationContext,
            ProductDatabase::class.java,
            "Product_db"
        ).build()
    }

    @Provides
    fun provideProductLocalDao(localDatabase: ProductDatabase): LocalProductDAO {
        return localDatabase.createProductDAO()
    }

    @Provides
    fun provideBasketDatabase(application: Application): BasketDatabase {
        return Room.databaseBuilder(
            application.applicationContext,
            BasketDatabase::class.java,
            "Basket_db"
        ).build()
    }

    @Provides
    fun provideBasketLocalDao(localDatabase: BasketDatabase): LocalBasketDAO {
        return localDatabase.createBasketDAO()
    }
}
