package com.example.zemoga.views.adapters

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.zemoga.R
import com.example.zemoga.data.models.PostModel
import com.example.zemoga.views.home.HomeViewModel
import kotlinx.android.synthetic.main.item_transaction.view.*

class TransactionRecyclerAdapter(
    private var items: MutableList<PostModel>,
    private val context: Context,
    private val onPostsListener: OnPostsListener
) : RecyclerView.Adapter<ViewHolderItemTransactions>() {

    interface OnPostsListener {
        fun getPost(id: Int)
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderItemTransactions {
        return ViewHolderItemTransactions(
            LayoutInflater.from(context).inflate(R.layout.item_transaction, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolderItemTransactions, position: Int) {
        val item = items[position]
        holder.lblValuePoints.text = item.title
        holder.lblNameBranch.text = item.body
        holder.cardItem.setOnClickListener {
            item.id?.let { it1 -> onPostsListener.getPost(it1) }
        }
    }

    fun updateTransactions(position: Int, homeViewModel: HomeViewModel) {
        if (items.size > 0) {
            items[position].id?.let {
                homeViewModel.deleteTransaction(it)
            }
            items.removeAt(position)
            notifyItemRemoved(position)
            notifyDataSetChanged()
        }
    }

    fun removeTransactions() {
        if (items.size > 0) {
            items = mutableListOf()
            notifyDataSetChanged()
        }
    }
}

class ViewHolderItemTransactions(view: View) : RecyclerView.ViewHolder(view) {
    val lblValuePoints: TextView = view.lblValuePoints
    val lblNameBranch: TextView = view.lblBranch
    val cardItem: CardView = view.cardItem
}
