package nz.co.test.transactions.activities

import android.graphics.Color
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import nz.co.test.transactions.R
import java.text.ParseException
import java.text.SimpleDateFormat

class MoreDetailActivity : AppCompatActivity() {

    lateinit var trsummery : TextView
    lateinit var trdate : TextView
    lateinit var trcredeb : TextView
    lateinit var trgstcredeb : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_more_details_view)

        trsummery = findViewById(R.id.summery)
        trdate = findViewById(R.id.date_time)
        trcredeb= findViewById(R.id.credit_debit)
        trgstcredeb= findViewById(R.id.gst_credit_debit)

        val bundle = intent.extras
        if (bundle != null){
            trsummery.text = "${bundle.getString("summery_name")}"

            val input = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            val output = SimpleDateFormat("MM/dd/yyyy hh:mm a")
            try {
                val oneWayTripDate = input.parse("${bundle.getString("transaction_date")}") // parse input
                trdate.text = output.format(oneWayTripDate)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            val credit_check = "${bundle.getString("credit")}"
            val debit_check = "${bundle.getString("debit")}"

            if (credit_check.equals("0")) {
                trcredeb.text = "Debit = " + debit_check
                trcredeb.setTextColor(Color.RED)

                var percentage = (debit_check.toDouble() / 100) * 15
                trgstcredeb.text = "GST 15% on Debit = " + percentage
                // block of code to be executed if the condition is true
            }else if (debit_check.equals("0")) {
                trcredeb.text = "Credit = " + credit_check
                trcredeb.setTextColor(Color.GREEN)

                var percentage = (credit_check.toDouble() / 100) * 15
                trgstcredeb.text = "GST 15% on Credit = " + percentage
            }
        }

    }
}