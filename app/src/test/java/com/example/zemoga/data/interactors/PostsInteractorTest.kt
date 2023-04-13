package com.example.zemoga.data.interactors

import android.annotation.SuppressLint
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.zemoga.data.dto.PostDTO
import com.example.zemoga.data.models.PostModel
import com.example.zemoga.data.repositories.posts.IPostsRepository
import com.example.zemoga.utils.BaseTest
import com.example.zemoga.utils.ConnectionManager
import io.mockk.impl.platform.Disposable
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.annotations.NonNull
import io.reactivex.internal.schedulers.ExecutorScheduler.ExecutorWorker
import io.reactivex.observers.TestObserver
import io.reactivex.plugins.RxJavaPlugins
import org.junit.Assert.*
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever
import java.util.concurrent.Callable
import java.util.concurrent.TimeUnit


//import kotlinx.coroutines.runBlocking

class PostsInteractorTest : BaseTest() {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var interactor: PostsInteractor

    @Mock
    private lateinit var postRepository: IPostsRepository

    @Mock
    private lateinit var connectionManager: ConnectionManager

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        //   postRepository = mock()
        //   connectionManager = mock()
        connectionManager = ConnectionManager()
        interactor = PostsInteractor()
        //   postRepository = IPostsRepository()
        // interactor.postRepository = postRepository
        //     interactor.connectionManager = connectionManager
    }

    @Test
    fun `getPosts should return data from database when there is no wifi connection`() {
        // Given
        /* val context: Context = Mockito.mock(Context::class.java)
         whenever(connectionManager.validateConnectionWifi(context)).thenReturn(false)*/
        val postDTO: PostDTO = Mockito.mock(PostDTO::class.java)
        //val postRepository: IPostsRepository = Mockito.mock(IPostsRepository::class.java)
        postDTO.id = 1
        postDTO.title = "title"
        postDTO.body = "body"
        val dtoList = listOf(postDTO)
        whenever(postRepository.getPosts()).thenReturn(Observable.just(dtoList))

        // When
        val observer = interactor.getPosts()?.test()

        // Then
        observer?.assertValue(interactor.convertTransactionListDtoToModels(dtoList))
    }
/*
    @Test
    fun `getPosts should return data from API when there is wifi connection`() {
        // Given
        whenever(connectionManager.validateConnectionWifi(any())).thenReturn(true)
        val dtoList = listOf(PostDTO(1, "Title 1", "Body 1"), PostDTO(2, "Title 2", "Body 2"))
        whenever(postRepository.getPosts()).thenReturn(Observable.just(dtoList))

        // When
        val observer = interactor.getPosts()?.test()

        // Then
        observer?.assertValue(convertTransactionListDtoToModels(dtoList))
    }

    @Test
    fun `getPost should return data from repository`() {
        // Given
        val dto = PostDTO(1, "Title", "Body")
        whenever(postRepository.getPost(eq(1))).thenReturn(Observable.just(dto))

        // When
        val observer = interactor.getPost(1)?.test()

        // Then
        observer?.assertValue(convertTransactionDtoToModel(dto))
    }*/

    @SuppressLint("CheckResult")
    @Test
    fun getUsers() {
        interactor.getPosts()?.subscribe({
            if (it.isNotEmpty()) {
                assert(true)
            } else {
                assert(false)
            }
        }, {
            assert(false)
        })
    }

    @Test
    fun `Get own account by Id`() {
        interactor.getPosts()
            ?.test()
            ?.assertSubscribed()
            ?.assertNotComplete()
            ?.assertNoErrors()
            ?.assertValue { it.first().id == 1 }
            ?.assertValue { it is List<PostModel> }
           // .assertValue { it == client.getOwnAccounts().getAccountById(ownAccountId) }
    }

    @Test
    fun testGetPosts() {
        // Create a mock postRepository object
        //val postRepository = mock(PostRepository::class.java)
     //   val postRepository = Mockito.mock(IPostsRepository::class.java)
        val postRepository: IPostsRepository = Mockito.mock(IPostsRepository::class.java)
        val postDTO: PostDTO = Mockito.mock(PostDTO::class.java)
        //val postRepository: IPostsRepository = Mockito.mock(IPostsRepository::class.java)
        postDTO.id = 1
        postDTO.title = "title"
        postDTO.body = "body"

        val postModel: PostModel = Mockito.mock(PostModel::class.java)
        //val postRepository: IPostsRepository = Mockito.mock(IPostsRepository::class.java)
        postDTO.id = 1
        postDTO.title = "title"
        postDTO.body = "body"
        // Create a list of PostDto objects to be returned by the mock postRepository
        val postDtos = listOf(postDTO)

        // Set up the mock postRepository to return the list of PostDto objects when its getPosts() method is called
        `when`(postRepository.getPosts()).thenReturn(Observable.just(postDtos))

        // Create a TestObserver to subscribe to the Observable returned by the getPosts() method
        val testObserver = TestObserver<List<PostModel>>()

        // Call the getPosts() method and subscribe to the resulting Observable with the TestObserver
        val resultObservable = interactor.getPosts()
        resultObservable?.subscribe(testObserver)

        // Verify that the TestObserver received a single onNext event with the expected list of PostModel objects
        testObserver.assertValue(listOf(postModel))
    }
}
