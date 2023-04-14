package com.example.zemoga.views.home

import android.app.AlertDialog
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AnimationUtils
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.zemoga.R
import com.example.zemoga.databinding.ActivityMainBinding
import com.example.zemoga.data.models.PostModel
import com.example.zemoga.views.adapters.TransactionRecyclerAdapter
import com.example.zemoga.views.base.BaseActivity
import kotlinx.android.synthetic.main.dialog_question.view.*

class MainActivity : BaseActivity(), TransactionRecyclerAdapter.OnPostsListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var homeViewModel: HomeViewModel
    private var transactionRecyclerAdapter: TransactionRecyclerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()
    }

    private fun setupView() {
        setupToolbar()
        setupViewModel()
        initSubscriptions()
        setupRefreshLayout()
    }

    private fun setupToolbar() {
        //   setupToolbar(binding.contentToolbarId, rString(R.string.lbl_transactions))
        binding.contentToolbarId.imgClear.setOnClickListener {
            animate()
            homeViewModel.deleteAllPosts()
            transactionRecyclerAdapter?.removeTransactions()
        }
    }

    private fun setupViewModel() {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        showLoading()
        homeViewModel.getPosts()
    }

    private fun initSubscriptions() {
        homeViewModel.singleLiveEvent.observe(this) {
            when (it) {
                is HomeViewModel.ViewEvent.ResponsePosts -> {
                    cancelRefresh()
                    hideLoading()
                    setupDataView(it.posts.toMutableList())
                }
                is HomeViewModel.ViewEvent.ResponsePost -> {
                    cancelRefresh()
                    hideLoading()
                    showDialogAction(it.post)
                }
                is HomeViewModel.ViewEvent.ResponseError -> {
                    cancelRefresh()
                    hideLoading()
                    showDialogOnError(it.apiError.mesage.toString())
                }
                else -> {}
            }
        }
    }

    private fun setupDataView(ranking: MutableList<PostModel>) {
        binding.contentMainId.recyclerItems.layoutManager = LinearLayoutManager(this)
        transactionRecyclerAdapter = TransactionRecyclerAdapter(ranking, this, this)
        binding.contentMainId.recyclerItems.adapter = transactionRecyclerAdapter

        val itemTouchHelperCallback: ItemTouchHelper.SimpleCallback =
            object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }

                override fun onSwiped(
                    viewHolder: RecyclerView.ViewHolder,
                    direction: Int
                ) {
                    if (transactionRecyclerAdapter != null) {
                        transactionRecyclerAdapter?.updateTransactions(
                            viewHolder.adapterPosition,
                            homeViewModel
                        )
                    }
                }
            }
        ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(binding.contentMainId.recyclerItems)
    }

    private fun setupRefreshLayout() {
        binding.swipeLayout.setColorSchemeResources(
            R.color.colorPrimary,
            R.color.colorPrimaryLight,
            R.color.colorPrimary,
            R.color.colorPrimaryLight
        )
        binding.swipeLayout.setOnRefreshListener {
            homeViewModel.getPosts()
        }
    }

    private fun animate() {
        binding.contentMainId.imgLeal.visibility = View.VISIBLE
        binding.contentMainId.recyclerItems.visibility = View.GONE
        val animationBounce = AnimationUtils.loadAnimation(this, R.anim.rotate)
        binding.contentMainId.imgLeal.startAnimation(animationBounce)
        val handler = Handler()
        handler.postDelayed({
            binding.contentMainId.imgLeal.visibility = View.GONE
            binding.contentMainId.recyclerItems.visibility = View.VISIBLE
        }, 2000)
    }

    private fun cancelRefresh() {
        if (binding.swipeLayout.isRefreshing) {
            binding.swipeLayout.isRefreshing = false
        }
    }

    private fun showDialogAction(post: PostModel?) {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_question, null)
        val builder = AlertDialog.Builder(this).setView(dialogView).setTitle("")
        val alertDialog = builder.show()
        dialogView.lblTitle.text = post?.title
        dialogView.lblDescription.text = post?.body

        if (post != null) {
            if (post.favorite)
                dialogView.imgFavorite.background =
                    ContextCompat.getDrawable(this, R.drawable.ic_favorite_selected)
            else
                dialogView.imgFavorite.background =
                    ContextCompat.getDrawable(this, R.drawable.ic_favorite)
        }

        dialogView.butCancel.setOnClickListener {
            alertDialog.dismiss()
        }
        dialogView.imgClose.setOnClickListener {
            alertDialog.dismiss()
        }
        dialogView.imgFavorite.setOnClickListener {
            if (post != null) {
                if (post.favorite)
                    post.id?.let { id -> homeViewModel.deleteTransaction(id) }
                else
                    homeViewModel.saveFavoritePost(post)

            }
            alertDialog.dismiss()
        }
    }

    override fun getPost(id: Int) {
        showLoading()
        homeViewModel.getPost(id)
    }
}
