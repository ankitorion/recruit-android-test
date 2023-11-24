package nz.co.test.transactions.activities

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import nz.co.test.transactions.R
import nz.co.test.transactions.di.network.RetrofitInstance
import nz.co.test.transactions.services.Transaction

class MainActivity : AppCompatActivity() , TransactionAdapter.itemClicklistener{

    lateinit var sortedAppsList:  List<Transaction>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val loading = findViewById<ProgressBar>(R.id.progress_bar)
        lifecycleScope.launchWhenCreated {
            loading.visibility = View.VISIBLE
            val response = try{
                RetrofitInstance.api.retrieveTransactions()
            }catch (e: Exception){
                Log.e(TAG, "Exception: $e")
                loading.visibility = View.GONE
                return@launchWhenCreated
            }
            if (response.isSuccessful && response.body() != null){
             //   Toast.makeText(this@MainActivity, ""+response.body()?.size+" users loaded!", Toast.LENGTH_SHORT).show()
                sortedAppsList = response.body()?.sortedBy { it.summary?.toString() } as List<Transaction>
                val usersRecyclerView = findViewById<RecyclerView>(R.id.users_list_view).apply {
                    adapter = TransactionAdapter(this@MainActivity)
                    layoutManager = LinearLayoutManager(this@MainActivity)
                    setHasFixedSize(true)
                }

                (usersRecyclerView.adapter as TransactionAdapter).submitList(sortedAppsList)
                loading.visibility = View.GONE
            } else {
               // Toast.makeText(this@MainActivity, "Something went wrong!", Toast.LENGTH_SHORT).show()
                loading.visibility = View.GONE
            }
        }
    }

    override fun onCellClickListener(position: Int) {
        val bundle = Bundle()
        bundle.putString("summery_name", sortedAppsList?.get(position)?.summary)
        bundle.putString("debit", sortedAppsList?.get(position)?.debit?.toString())
        bundle.putString("credit", sortedAppsList?.get(position)?.credit?.toString())
        bundle.putString("transaction_date", sortedAppsList?.get(position)?.transactionDate?.toString())

        val intent = Intent(this@MainActivity, MoreDetailActivity::class.java)
        intent.putExtras(bundle)
        startActivity(intent)

      //  Toast.makeText(this@MainActivity, ""+sortedAppsList?.get(position)?.summary, Toast.LENGTH_SHORT).show()
    }

}