package com.example.zemoga.data.interactors

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.example.zemoga.data.dto.PostDTO
import com.example.zemoga.data.entities.PostEntity
import com.example.zemoga.data.models.PostModel
import com.example.zemoga.data.repositories.posts.IPostsRepository
import com.example.zemoga.di.posts.DaggerIPostsComponent
import com.example.zemoga.di.posts.PostsModule
import com.example.zemoga.utils.ConnectionManager
import com.example.zemoga.utils.RUtil.Companion.context
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class PostsInteractor {

    @Inject
    lateinit var postRepository: IPostsRepository
    private lateinit var connectionManger: ConnectionManager

    init {
        DaggerIPostsComponent.builder().postsModule(PostsModule()).build()
            .inject(this)
    }

    fun getPosts(): Observable<List<PostModel>>? {
        connectionManger = ConnectionManager()
        return if (!connectionManger.validateConnectionWifi(context)) {
            postRepository.getPostsDB()
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.flatMap { dtoList ->
                    Observable.just(convertPostsListEntityToModels(dtoList))
                }
        } else {
            return postRepository.getPosts()
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.flatMap { dtoList ->
                    Observable.just(convertTransactionListDtoToModels(dtoList))
                }
        }
    }

    fun getPost(id: Int): Observable<PostModel>? {
        val isFavorite = getFavoritePost(id)
        return postRepository.getPost(id)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.flatMap { dto ->
                Observable.just(convertTransactionDtoToModel(dto, isFavorite))
            }
    }

    private fun getFavoritePost(id: Int): Boolean {
        return postRepository.getFavoritePost(id) != null
    }

    fun convertTransactionListDtoToModels(dtoList: List<PostDTO>): List<PostModel> {
        val models = mutableListOf<PostModel>()
        dtoList.forEach { dto ->
            val model = convertTransactionDtoToModel(dto)
            //  savePostEntity(convertTransactionDtoToEntity(dto))
            models.add(model)
        }
        return models
    }

    private fun convertTransactionDtoToEntity(post: PostModel?): PostEntity {
        val gson = Gson()
        val jsonString = gson.toJson(post)
        return PostEntity().apply {
            if (post != null) {
                id = post.id
            }
            transactionObj = jsonString
        }
    }

    fun saveFavoritePost(post: PostModel?): Observable<Boolean>? {
        val postEntity = convertTransactionDtoToEntity(post)
        return postRepository.savePost(postEntity)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.flatMap { dto ->
                Observable.just(dto)
            }
    }

    private fun convertTransactionDtoToModel(
        dto: PostDTO?,
        isFavorite: Boolean = false,
    ): PostModel {
        return PostModel().apply {
            id = dto?.id
            title = dto?.title
            body = dto?.body
            favorite = isFavorite
        }
    }

    private fun convertPostsListEntityToModels(dtoList: List<PostEntity>): List<PostModel> {
        val models = mutableListOf<PostModel>()
        dtoList.forEach { dto ->
            val model =
                convertTransactionDtoToModel(convertPostEntityToModel(dto.transactionObj))
            models.add(model)
        }
        return models
    }

    private fun convertPostEntityToModel(json: String?): PostDTO {
        val gson = Gson()
        val transaction = object : TypeToken<PostDTO>() {}.type
        return gson.fromJson(json, transaction)
    }

    fun deleteFavoritePost(id: Int): Observable<Boolean>? {
        return postRepository.deleteFavoritePost(id)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.flatMap { it ->
                Observable.just(it)
            }
    }

    fun deleteAllPosts(): Observable<Boolean>? {
        return postRepository.deleteAllPosts()
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.flatMap { it ->
                Observable.just(it)
            }
    }
}
