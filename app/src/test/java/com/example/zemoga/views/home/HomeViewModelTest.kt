package com.example.zemoga.views.home

import com.example.zemoga.data.interactors.PostsInteractor
import com.example.zemoga.data.models.PostModel
import com.example.zemoga.data.repositories.posts.PostsRepository
import com.example.zemoga.utils.ConnectionManager
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import io.reactivex.Observable
import org.mockito.Mockito.*

@RunWith(MockitoJUnitRunner::class)
class HomeViewModelTest {

    @Mock
    lateinit var postsInteractor: PostsInteractor

    @Mock
    lateinit var connectionManager: ConnectionManager

    @Mock
    lateinit var postsRepository: PostsRepository

    @InjectMocks
    lateinit var homeViewModel: HomeViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun getPostsWhenIsValid() {
        //Given
        val expectedResult = listOf<PostModel>()

        //When
        `when`(postsInteractor.getPosts()).thenReturn(Observable.just(expectedResult))

        homeViewModel.getPosts()

        //Then
        verify(postsInteractor).getPosts()
    }

    @Test
    fun getPostWhenIsValid() {
        //Given
        val expectedResult = mock(PostModel::class.java)
        `when`(postsInteractor.getPost(1)).thenReturn(Observable.just(expectedResult))

        //When
        homeViewModel.getPost(1)

        //Then
        verify(postsInteractor).getPost(1)
    }

    @Test
    fun saveFavoritePostIsValid() {
        //Given
        val expectedResult = true
        val post = PostModel()

        //When
        `when`(postsInteractor.saveFavoritePost(post)).thenReturn(
            Observable.just(
                expectedResult
            )
        )
        homeViewModel.saveFavoritePost(post)

        //Then
        verify(postsInteractor).saveFavoritePost(post)
    }

    @Test
    fun deleteTransactionWhenIsValid() {
        //Given
        val postId = 1
        val expectedResult = true

        //When
        `when`(postsInteractor.deleteFavoritePost(postId)).thenReturn(Observable.just(expectedResult))
        homeViewModel.deleteTransaction(postId)

        //Then
        verify(postsInteractor).deleteFavoritePost(postId)
    }

    @Test
    fun deleteAllTransactionWhenIsValid() {
        //Given
        val expectedResult = true

        //When
        `when`(postsInteractor.deleteAllPosts()).thenReturn(Observable.just(expectedResult))
        homeViewModel.deleteAllPosts()

        //Then
        verify(postsInteractor).deleteAllPosts()
    }
}
