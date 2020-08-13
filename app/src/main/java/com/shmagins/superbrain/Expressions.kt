@file:JvmName("Expressions")
package com.shmagins.superbrain

import android.util.Log
import com.shmagins.superbrain.db.CalcGameLevel
import kotlin.random.Random

enum class Operation(val mnemonic: String) {
    PLUS("+"),
    MINUS("-"),
    MULTIPLY("×"),
    DIVIDE("÷")
}

fun getOperationListFromString(s: String) : List<Operation> {
    val strings = s.trim().split(" ")
    return List(strings.size) { Operation.valueOf(strings[it])}
}

/*
    Суперкласс для всех операндов
*/
abstract class Operand<T : Number> {
    abstract val value: T

    override fun equals(other: Any?): Boolean {
        if (other == null)
            return false
        if (this::class != other::class)
            return false

        return value == (other as Operand<*>).value
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }
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
class Expression<T : Number>(internal val operation: Operation, internal val op1: Operand<T>, internal val op2: Operand<T>) : Operand<T>(){
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

    override fun toString(): String = if (op.value >= 0) String.format("-%s", op.toString()) else String.format("%s", (-op.value).toString())

}

class Brackets<T : Number>(private val op: Operand<T>) : Operand<T>(){
    override val value: T
        get() = op.value

    override fun toString(): String = String.format("(%s)", op.toString())
}

/*
    Создание выражения одиночное / множественное
*/
fun <T : Number> createExpressions(operations: MutableList<OperationDescriptor<out T>>, expressionsCount: Int): MutableList<Expression<out T>> {
    Log.d("CreateExpressions", "CreateExpressions")
    val l = MutableList(expressionsCount) { operations.random().createExpression() }
    Log.d("CreateExpressions", "l $l")
    return l
}

@Suppress("Unchecked_cast")
class OperationDescriptor<T>(private val operation: Operation, private val firstOperandMin: T, private val firstOperandMax: T, private val secondOperandMin: T, private val secondOperandMax: T, private val resultMin: T, private val resultMax: T) where T : Number{
    fun createExpression(): Expression<T> {
        var expr: Expression<T>
        when (operation) {
            Operation.PLUS -> {
                do {
                    expr = Expression(operation, Value(rand(firstOperandMin, firstOperandMax)), Value(rand(secondOperandMin, secondOperandMax)))
                } while (expr.value < resultMin || expr.value > resultMax)
            }
            Operation.MINUS -> {
                do {
                    val op1: Value<Number> = Value(rand(secondOperandMin, secondOperandMax))
                    val op2: Value<Number> = Value(rand(resultMin, resultMax))
                    val op3: Value<Number> = Value<Number>(op1.value + op2.value)
                    expr = Expression(operation, op3, op2) as Expression<T>
                } while (expr.value < resultMin || expr.value > resultMax)
            }
            Operation.MULTIPLY -> {
                do {
                    expr = Expression(operation, Value(rand(firstOperandMin, firstOperandMax)), Value(rand(secondOperandMin, secondOperandMax)))
                } while (expr.value < resultMin || expr.value > resultMax)
            }
            Operation.DIVIDE -> {
                do {
                    val op1: Value<Number> = Value(rand(secondOperandMin, secondOperandMax))
                    val op2: Value<Number> = Value(rand(resultMin, resultMax))
                    val op3: Value<Number> = Value<Number>(op1.value * op2.value)
                    expr = Expression(operation, op3, op2) as Expression<T>
                } while (expr.value < resultMin || expr.value > resultMax)
            }
        }
        return expr
    }
}

@Suppress("Unchecked_cast")
fun <T> rand(min: T, max: T) : T where T : Number = when(min) {
    is Long   -> Random.nextLong(min.toLong(), max.toLong()) as T
    is Int    -> Random.nextInt(min.toInt(), max.toInt()) as T
    is Short  -> Random.nextInt(min.toInt(), max.toInt()).toShort() as T
    is Byte   -> Random.nextInt(min.toInt(), max.toInt()).toByte() as T
    is Double -> Random.nextDouble(min.toDouble(),max.toDouble()) as T
    is Float  -> Random.nextDouble(min.toDouble(),max.toDouble()).toFloat() as T
    else      -> throw RuntimeException("Unknown numeric type")
}

fun createOperationDescriptorsForLevel(calcGameLevel: CalcGameLevel): MutableList<OperationDescriptor<out Number>> {
    val operations = getOperationListFromString(calcGameLevel.operations)
    val ret: MutableList<OperationDescriptor<out Number>> = MutableList(operations.size) {
        when (calcGameLevel.type) {
            "Int" -> {
                OperationDescriptor(operations[it],
                        calcGameLevel.firstOperandMin.toInt(),
                        calcGameLevel.firstOperandMax.toInt(),
                        calcGameLevel.secondOperandMin.toInt(),
                        calcGameLevel.secondOperandMax.toInt(),
                        calcGameLevel.resultMin.toInt(),
                        calcGameLevel.resultMax.toInt())
            }
            "Double" -> {
                OperationDescriptor(operations[it],
                        calcGameLevel.firstOperandMin.toDouble(),
                        calcGameLevel.firstOperandMax.toDouble(),
                        calcGameLevel.secondOperandMin.toDouble(),
                        calcGameLevel.secondOperandMax.toDouble(),
                        calcGameLevel.resultMin.toDouble(),
                        calcGameLevel.resultMax.toDouble())
            }
            else -> OperationDescriptor(Operation.PLUS,0,0,0,0,0,0)
        }
    }
    return ret
}
