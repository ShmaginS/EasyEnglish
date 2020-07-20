@file:JvmName("CalcManager")
package com.shmagins.superbrain

import io.reactivex.Observable
import java.lang.StringBuilder
import kotlin.random.Random

enum class Operation(val mnemonic: String) {
    PLUS("+"),
    MINUS("-"),
    MULTIPLY("×"),
    DIVIDE("÷")
}

/*
    Суперкласс для всех операндов
*/
abstract class Operand<T : Number> {
    abstract val value: T
}

/*
    Инкапсулирует одиночное значение
*/
class Value<T : Number>(private val t: T) : Operand<T>(){
    override val value: T
        get() = t

    override fun toString(): String {
        return t.toString()
    }
}

/*
    Инкапсулирует выражение
*/
@Suppress("Unchecked_cast")
class Expression<T : Number>(private val operation: Operation, private val op1: Operand<T>, private val op2: Operand<T>) : Operand<T>(){
    override val value: T
        get() = evaluate()
    private fun evaluate(): T  = when (operation) {
        Operation.PLUS -> (op1.value + op2.value) as T
        Operation.MINUS -> (op1.value - op2.value) as T
        Operation.MULTIPLY -> (op1.value * op2.value) as T
        Operation.DIVIDE -> (op1.value / op2.value) as T
    }

    override fun toString(): String = String.format("%s %s %s", op1, operation.mnemonic, op2)
}

/*
    Декораторы для операндов, такие как скобки или знак минус
*/
@Suppress("Unchecked_cast")
class Inversion<T : Number>(private val op: Operand<T>) : Operand<T>(){
    override val value: T
        get() = -op.value as T

    override fun toString(): String = if (op.value.compareTo(0) >= 0) String.format("-%s", op.toString()) else String.format("%s", (-op.value).toString())

}

class Brackets<T : Number>(private val op: Operand<T>) : Operand<T>(){
    override val value: T
        get() = op.value

    override fun toString(): String = String.format("(%s)", op.toString())
}

private fun <T : Number>rand(max: T) : T = when(max) {
    is Long   -> Random.nextLong(max.toLong()) as T
    is Int    -> Random.nextInt(max.toInt()) as T
    is Short  -> Random.nextInt(max.toInt()) as T
    is Byte   -> Random.nextInt(max.toInt()) as T
    is Double -> Random.nextDouble(max.toDouble()) as T
    is Float  -> Random.nextFloat() as T
    else      -> throw RuntimeException("Unknown numeric type")
}

fun <T : Number> createExpression(operations: List<Operation>, maximum: T): Expression<T> {
    do {
        val expr: Expression<T> = Expression(operations.random(), Value(rand(maximum)), Value(rand(maximum)))
    } while (expr.value > maximum)
}



fun calculate(operation: Operation, operand1: Int, operand2: Int) = when (operation) {
    Operation.PLUS -> operand1 + operand2
    Operation.MINUS -> operand1 - operand2
    Operation.MULTIPLY -> operand1 * operand2
    Operation.DIVIDE -> operand1 / operand2
}

fun randomOperation() =  Operation.values().random();

fun createCalculation(): Calculation {
    val op = randomOperation();
    val first = Random.nextInt(1, 10)
    val second = Random.nextInt(1, 10)
    return when (op) {
        Operation.PLUS -> Calculation(0, first, second, op.mnemonic, first + second)
        Operation.MINUS -> Calculation(0, first + second, first, op.mnemonic, second)
        Operation.MULTIPLY -> Calculation(0, first, second, op.mnemonic, first * second)
        Operation.DIVIDE -> Calculation(0, first * second, first, op.mnemonic, second)
    }
}

fun createCalculations(limit: Int): Observable<List<Calculation>> = Observable.create {
    emitter -> run {
        val ret: MutableList<Calculation> = ArrayList()
        for (i in 1..limit)
            ret.add(createCalculation())
        emitter.onNext(ret)
        emitter.onComplete()
    }
}