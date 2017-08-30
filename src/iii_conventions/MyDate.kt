package iii_conventions

import java.util.*

data class MyDate(val year: Int, val month: Int, val dayOfMonth: Int): Comparable<MyDate> {
    override fun compareTo(other: MyDate): Int {
        return this.transformToCalendar().compareTo(other.transformToCalendar())
    }

    private fun transformToCalendar(): Calendar {
        val thisDate = Calendar.getInstance()
        thisDate.set(year, month, dayOfMonth)
        return thisDate
    }
}

operator fun MyDate.rangeTo(other: MyDate): DateRange = DateRange(this, other)

enum class TimeInterval {
    DAY,
    WEEK,
    YEAR
}

class DateRange(val start: MyDate, val endInclusive: MyDate):Iterable<MyDate> {
    override fun iterator(): Iterator<MyDate> {
        return object : Iterator<MyDate> {
            var current = start

            override fun hasNext() = current <= endInclusive

            override fun next(): MyDate {
                val currentStart = current
                current = current.nextDay()
                return currentStart
            }

        }
    }

    operator fun contains(date: MyDate) = this.start <= date && this.endInclusive >= date
}

//operator fun MyDate.compareTo(other: MyDate) = this.transformToCalendar().compareTo(other.transformToCalendar())

//private fun MyDate.transformToCalendar(): Calendar {
//    val thisDate = Calendar.getInstance()
//    thisDate.set(year, month, dayOfMonth)
//    return thisDate
//}