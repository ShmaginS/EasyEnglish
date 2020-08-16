package com.shmagins.superbrain.common

operator fun Number.minus(other: Number): Number {
    return when (this) {
        is Long   -> this.toLong() - other.toLong()
        is Int    -> this.toInt()  - other.toInt()
        is Short  -> this.toShort() - other.toShort()
        is Byte   -> this.toByte() - other.toByte()
        is Double -> this.toDouble() - other.toDouble()
        is Float  -> this.toFloat() - other.toFloat()
        else      -> throw RuntimeException("Unknown numeric type")
    }
}
operator fun Number.plus(other: Number): Number {
    return when (this) {
        is Long   -> this.toLong() + other.toLong()
        is Int    -> this.toInt()  + other.toInt()
        is Short  -> this.toShort() + other.toShort()
        is Byte   -> this.toByte() + other.toByte()
        is Double -> this.toDouble() + other.toDouble()
        is Float  -> this.toFloat() + other.toFloat()
        else      -> throw RuntimeException("Unknown numeric type")
    }
}

operator fun Number.times(other: Number): Number {
    return when (this) {
        is Long   -> this.toLong() * other.toLong()
        is Int    -> this.toInt()  * other.toInt()
        is Short  -> this.toShort() * other.toShort()
        is Byte   -> this.toByte() * other.toByte()
        is Double -> this.toDouble() * other.toDouble()
        is Float  -> this.toFloat() * other.toFloat()
        else      -> throw RuntimeException("Unknown numeric type")
    }
}
operator fun Number.div(other: Number): Number {
    return when (this) {
        is Long   -> this.toLong() / other.toLong()
        is Int    -> this.toInt()  / other.toInt()
        is Short  -> this.toShort() / other.toShort()
        is Byte   -> this.toByte() / other.toByte()
        is Double -> this.toDouble() / other.toDouble()
        is Float  -> this.toFloat() / other.toFloat()
        else      -> throw RuntimeException("Unknown numeric type")
    }
}

operator fun Number.unaryMinus(): Number {
    return when (this) {
        is Long   -> -this.toLong()
        is Int    -> -this.toInt()
        is Short  -> -this.toShort()
        is Byte   -> -this.toByte()
        is Double -> -this.toDouble()
        is Float  -> -this.toFloat()
        else      -> throw RuntimeException("Unknown numeric type")
    }
}

operator fun Number.compareTo(o: Number): Int{
    return when (this) {
        is Long   -> this.compareTo(o as Long)
        is Int    -> this.compareTo(o as Int)
        is Short  -> this.compareTo(o as Short)
        is Byte   -> this.compareTo(o as Byte)
        is Double -> this.compareTo(o as Double)
        is Float  -> this.compareTo(o as Float)
        else      -> throw RuntimeException("Unknown numeric type")
    }
}
