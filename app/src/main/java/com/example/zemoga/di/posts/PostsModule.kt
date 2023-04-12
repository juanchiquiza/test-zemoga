package com.example.zemoga.di.posts

import com.example.zemoga.data.database.DBHelper
import com.example.zemoga.data.interactors.PostsInteractor
import com.example.zemoga.data.repositories.posts.IPostsRepository
import com.example.zemoga.data.repositories.posts.PostsRepository
import dagger.Module
import dagger.Provides

@Module
class PostsModule {
    @Provides
    fun providePostsRepository(): IPostsRepository {
        return PostsRepository()
    }

    @Provides
    fun providePostsInteractor(): PostsInteractor {
        return PostsInteractor()
    }

    @Provides
    fun provideDBHelper(): DBHelper {
        return DBHelper()
    }
}