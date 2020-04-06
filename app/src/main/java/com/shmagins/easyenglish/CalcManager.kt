@file:JvmName("CalcManager")
package com.shmagins.easyenglish

import com.shmagins.easyenglish.db.Calculation
import kotlin.random.Random

enum class Operation(val mnemonic: String) {
    PLUS("+"),
    MINUS("-"),
    MULTIPLY("×"),
    DIVIDE("÷")
}

fun calculate(operation: Operation, operand1: Int, operand2: Int) = when (operation) {
    Operation.PLUS -> operand1 + operand2
    Operation.MINUS -> operand1 - operand2
    Operation.MULTIPLY -> operand1 * operand2
    Operation.DIVIDE -> operand1 / operand2
}

fun randomOperation() =  Operation.values().random();

fun createCalculation(): Calculation{
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