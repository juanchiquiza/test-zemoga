package com.example.zemoga.views.home

import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.AnimationUtils
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.zemoga.R
import com.example.zemoga.databinding.ActivityMainBinding
import com.example.zemoga.data.models.PostModel
import com.example.zemoga.views.adapters.TransactionRecyclerAdapter
import com.example.zemoga.views.base.BaseActivity

class MainActivity : BaseActivity() {

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
            homeViewModel.deleteAllTransaction()
            transactionRecyclerAdapter?.removeTransactions()
        }
    }

    private fun setupViewModel() {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        showLoading()
        homeViewModel.getTransactions()
    }

    private fun initSubscriptions() {
        homeViewModel.singleLiveEvent.observe(this) {
            when (it) {
                is HomeViewModel.ViewEvent.ResponseTransactions -> {
                    cancelRefresh()
                    hideLoading()
                    setupDataView(it.transaction.toMutableList())
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
        transactionRecyclerAdapter = TransactionRecyclerAdapter(ranking, this)
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
            homeViewModel.getTransactions()
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
}
