package com.example.zemoga.views.adapters

//import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.zemoga.R

import com.leal.data.models.TransactionModel
import com.example.zemoga.views.home.HomeViewModel
import kotlinx.android.synthetic.main.dialog_question.view.*
import kotlinx.android.synthetic.main.item_transaction.view.*

//@SuppressLint("SetTextI18n")
class TransactionRecyclerAdapter(
    private var items: MutableList<TransactionModel>,
    private val context: Context
) : RecyclerView.Adapter<ViewHolderItemTransactions>() {

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
        if (position < 20) {
            holder.imgCircle.background =
                ContextCompat.getDrawable(context, R.drawable.circle_yellow)
        } else {
            holder.imgCircle.background = ContextCompat.getDrawable(context, R.drawable.circle_gray)
        }
        holder.lblValuePoints.text = item.commerce?.valueToPoints.toString()
        holder.lblNameBranch.text = item.commerce?.branches?.first()?.name
        holder.cardItem.setOnClickListener {
            showDialogAction(context, item)
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

    private fun showDialogAction(context: Context, transaction: TransactionModel?) {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_question, null)
        val builder = AlertDialog.Builder(context).setView(dialogView).setTitle("")
        val alertDialog = builder.show()
        dialogView.lblName.text = transaction?.commerce?.name
        dialogView.lblPoints.text = transaction?.commerce?.valueToPoints.toString()
        dialogView.branch.text = transaction?.commerce?.branches?.get(0)?.name.toString()
        dialogView.branch1.text = transaction?.commerce?.branches?.get(1)?.name.toString()
        dialogView.butCancel.setOnClickListener {
            alertDialog.dismiss()
        }
        dialogView.imgClose.setOnClickListener {
            alertDialog.dismiss()
        }
    }
}

class ViewHolderItemTransactions(view: View) : RecyclerView.ViewHolder(view) {
    val imgCircle: ImageView = view.imgCircle
    val lblValuePoints: TextView = view.lblValuePoints
    val lblNameBranch: TextView = view.lblBranch
    val cardItem: CardView = view.cardItem
}