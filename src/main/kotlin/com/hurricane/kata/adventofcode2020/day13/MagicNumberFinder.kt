package com.hurricane.kata.adventofcode2020.day13

import java.math.BigInteger

class MagicNumberFinder {

    fun find(buses: List<Bus>): Long {
        val vals = buses.map { -it.offset.toLong() }
        val modVals = buses.map { it.busId.toLong() }

        return crm(vals, modVals)
    }

}

//source: https://github.com/andrewass/kalgos/blob/master/src/main/kotlin/algorithms/number/chineseRemainderTheorem.kt

fun crm(vals: List<Long>, modVals: List<Long>): Long {
    val modProduct = modVals.product()
    var res = 0L
    for (i in vals.indices) {
        val gcd = extendedEuclid(modVals[i], modProduct / modVals[i])
        val a = BigInteger(vals[i].toString())
        val b = BigInteger(gcd[2].toString())
        val c = BigInteger((modProduct / modVals[i]).toString())
        val mod = BigInteger(modProduct.toString())
        res += a.multiply(b).multiply(c).remainder(mod).toLong()
        res = (res.rem(modProduct) + modProduct).rem(modProduct)
    }
    return res
}

fun <E : Number> List<E>.product(): Long {
    if (this.isEmpty()) {
        return 0L
    }
    var product = 1L
    for (numb in this) {
        when (numb) {
            is Long -> product *= numb
            is Int -> product *= numb
        }
    }
    return product
}

fun extendedEuclid(a: Long, b: Long): LongArray {
    return if (b == 0L) {
        longArrayOf(a, 1L, 0L)
    } else {
        val res = extendedEuclid(b, a.rem(b))
        longArrayOf(res[0], res[2], res[1] - (a / b) * res[2])
    }
}