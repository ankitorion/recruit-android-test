package nz.co.test.transactions.activities

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import nz.co.test.transactions.R
import nz.co.test.transactions.services.Transaction
import java.text.ParseException
import java.text.SimpleDateFormat

class TransactionAdapter(public val listener: MainActivity) : ListAdapter<Transaction, TransactionAdapter.ViewHolder>(DiffTransactionCallBack()){
    lateinit var context: Context

    interface itemClicklistener {
        fun onCellClickListener(position: Int)
    }

    inner class ViewHolder(private val containerView : View) : RecyclerView.ViewHolder(containerView){
        init {
            containerView.setOnClickListener {
                listener.onCellClickListener(adapterPosition)
            }
        }

        fun bind(Transaction : Transaction){
            containerView.findViewById<TextView>(R.id.summery_name).text = Transaction.summary

            val input = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            val output = SimpleDateFormat("MM/dd/yyyy hh:mm a")
            try {
                val oneWayTripDate = input.parse(Transaction.transactionDate) // parse input
                containerView.findViewById<TextView>(R.id.date_time_format).text = output.format(oneWayTripDate)
            } catch (e: ParseException) {
                e.printStackTrace()
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemLayout = LayoutInflater.from(parent.context).inflate(R.layout.transaction_layout, parent, false)
        return ViewHolder(itemLayout)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))


    }
}

class DiffTransactionCallBack : DiffUtil.ItemCallback<Transaction>(){
    override fun areItemsTheSame(oldItem: Transaction, newItem: Transaction): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Transaction, newItem: Transaction): Boolean {
        return oldItem == newItem
    }

}