package iii_conventions

import iii_conventions.TimeInterval.*
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

    operator fun plus(timeInterval: TimeInterval): MyDate {
        return this.addTimeIntervals(timeInterval, 1)
    }

    operator fun plus(multipliedTimeInterval: MultipliedTimeInterval): MyDate {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, dayOfMonth)
        when (multipliedTimeInterval.timeInterval) {
            DAY -> calendar.add(Calendar.DAY_OF_MONTH, multipliedTimeInterval.numberOfTimes)
            WEEK -> calendar.add(Calendar.WEEK_OF_MONTH, multipliedTimeInterval.numberOfTimes)
            YEAR -> calendar.add(Calendar.YEAR, multipliedTimeInterval.numberOfTimes)
        }
        return MyDate(year = calendar.get(Calendar.YEAR), month = calendar.get(Calendar.MONTH), dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH))
    }
}

operator fun MyDate.rangeTo(other: MyDate): DateRange = DateRange(this, other)

enum class TimeInterval {
    DAY,
    WEEK,
    YEAR;

    operator fun times(number: Int) = MultipliedTimeInterval(this, number)
}

class MultipliedTimeInterval(val timeInterval: TimeInterval, val numberOfTimes: Int)

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