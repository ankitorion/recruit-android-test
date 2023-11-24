package nz.co.test.transactions.services

import java.math.BigDecimal
import java.time.OffsetDateTime

data class Transaction (
    val id: Int,
    val transactionDate: String,
    val summary: String,
    val debit: BigDecimal,
    val credit: BigDecimal
): Comparator<Transaction> {
    override fun compare(o1: Transaction?, o2: Transaction?): Int {
        TODO("Not yet implemented")
    }
}