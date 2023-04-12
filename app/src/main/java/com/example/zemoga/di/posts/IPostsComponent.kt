package com.example.zemoga.di.posts

import com.example.zemoga.data.interactors.PostsInteractor
import com.example.zemoga.data.repositories.posts.PostsRepository
import com.example.zemoga.views.home.HomeViewModel
import dagger.Component

@Component(modules = [PostsModule::class])
interface IPostsComponent {

    fun inject(postsRepository: PostsRepository)
    fun inject(postsInteractor: PostsInteractor)
    fun inject(homeViewModel: HomeViewModel)
}