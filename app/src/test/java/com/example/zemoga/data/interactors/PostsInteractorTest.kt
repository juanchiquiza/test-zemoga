package com.example.zemoga.data.interactors

import com.example.zemoga.data.dto.PostDTO
import com.example.zemoga.data.entities.PostEntity
import com.example.zemoga.data.models.PostModel
import com.example.zemoga.data.repositories.posts.IPostsRepository
import junit.framework.TestCase.*
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever

class PostsInteractorTest {

    @InjectMocks
    lateinit var postsInteractor: PostsInteractor

    @Mock
    lateinit var postRepository: IPostsRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun convertTransactionListDtoToModels() {
        //Given
        val dtoList = listOf(
            PostDTO().apply {
                id = 1
                title = "Title 1"
                body = "Body 1"
            },
            PostDTO().apply {
                id = 2
                title = "Title 2"
                body = "Body 2"
            }
        )

        val expectedModels = listOf(
            PostModel().apply {
                id = 1
                title = "Title 1"
                body = "Body 1"
                favorite = false
            },
            PostModel().apply {
                id = 2
                title = "Title 2"
                body = "Body 2"
                favorite = false
            }
        )
        //When
        val resultModels = postsInteractor.convertTransactionListDtoToModels(dtoList)

        //Then
        assertEquals(expectedModels.size, resultModels.size)
        for (i in expectedModels.indices) {
            assertEquals(expectedModels[i].id, resultModels[i].id)
            assertEquals(expectedModels[i].title, resultModels[i].title)
            assertEquals(expectedModels[i].body, resultModels[i].body)
            assertEquals(expectedModels[i].favorite, resultModels[i].favorite)
        }
    }

    @Test
    fun getFavoritePostWhenPostIsNotFavorite() {
        //Given
        val postId = 1

        //When
        whenever(postRepository.getFavoritePost(postId)).thenReturn(null)

        val isFavorite = postsInteractor.getFavoritePost(postId)

        //Then
        assertFalse(isFavorite)
    }

    @Test
    fun getFavoritePostWhenPostIsFavorite() {
        //Given
        val postId = 1

        val resultExpected = PostEntity().apply {
            id = postId
        }

        //When
        whenever(postRepository.getFavoritePost(postId)).thenReturn(resultExpected)
        val isFavorite = postsInteractor.getFavoritePost(postId)

        //Then
        assertTrue(isFavorite)
    }

    @Test
    fun convertPostEntityToModel() {
        //Given
        val jsonString = "{\"id\":1,\"title\":\"Sample Title\",\"body\":\"Sample Body\"}"
        val expectedPostDTO = PostDTO().apply {
            id = 1
            title = "Sample Title"
            body = "Sample Body"
        }
        //When
        val resultPostDTO = postsInteractor.convertPostEntityToModel(jsonString)

        //Then
        assertEquals(expectedPostDTO.id, resultPostDTO.id)
        assertEquals(expectedPostDTO.title, resultPostDTO.title)
        assertEquals(expectedPostDTO.body, resultPostDTO.body)
    }
}
