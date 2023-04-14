package com.example.zemoga.di.helper

import com.example.zemoga.data.database.DBHelper
import com.example.zemoga.data.repositories.posts.PostsRepository
import dagger.Component

@Component(modules = [HelperModule::class])
interface IHelperComponent {
    fun inject(databaseHelper: DBHelper)
    fun inject(authRepository: PostsRepository)
}