package com.example.zemoga.views.home

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import com.example.zemoga.data.interactors.PostsInteractor
import com.example.zemoga.data.models.ApiErrorModel
import com.example.zemoga.data.models.PostModel
import com.example.zemoga.di.posts.DaggerIPostsComponent
import com.example.zemoga.di.posts.PostsModule
import com.example.zemoga.livedata.SingleLiveEvent
import com.example.zemoga.utils.network.ApiError
import javax.inject.Inject

@SuppressLint("CheckResult, DefaultLocale")
class HomeViewModel : ViewModel() {

    @Inject
    lateinit var postsInteractor: PostsInteractor

    init {
        DaggerIPostsComponent.builder().postsModule(PostsModule()).build()
            .inject(this)
    }

    var singleLiveEvent: SingleLiveEvent<ViewEvent> = SingleLiveEvent()

    sealed class ViewEvent {
        class ResponsePosts(val posts: List<PostModel>) : ViewEvent()
        class ResponsePost(val post: PostModel) : ViewEvent()
        class ResponseDeleteTransaction(val result: Boolean) : ViewEvent()
        class ResponseDeleteAllTransactions(val result: Boolean) : ViewEvent()
        class ResponseError(val apiError: ApiErrorModel) : ViewEvent()
    }

    fun getPosts() {
        postsInteractor.getPosts()?.subscribe({
            singleLiveEvent.value = ViewEvent.ResponsePosts(it)
        }, {
            singleLiveEvent.value = ViewEvent.ResponseError(ApiError(it).apiErrorModel)
        })
    }

    fun getPost(id: Int) {
        postsInteractor.getPost(id)?.subscribe({
            singleLiveEvent.value = ViewEvent.ResponsePost(it)
        }, {
            singleLiveEvent.value = ViewEvent.ResponseError(ApiError(it).apiErrorModel)
        })
    }

    fun deleteTransaction(id: Int) {
        postsInteractor.deletePost(id)?.subscribe({
            singleLiveEvent.value = ViewEvent.ResponseDeleteTransaction(it)
        }, {
            singleLiveEvent.value = ViewEvent.ResponseError(ApiError(it).apiErrorModel)
        })
    }

    fun deleteAllTransaction() {
        postsInteractor.deleteAllPosts()?.subscribe({
            singleLiveEvent.value = ViewEvent.ResponseDeleteAllTransactions(it)
        }, {
            singleLiveEvent.value = ViewEvent.ResponseError(ApiError(it).apiErrorModel)
        })
    }
}