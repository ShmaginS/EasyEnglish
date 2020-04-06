@file:JvmName("MatrixManager")
package com.shmagins.easyenglish

import java.lang.Exception
import java.lang.IllegalArgumentException
import kotlin.random.Random

fun generateImageIndexMatrixWithDuplicates(width: Int, height: Int, numImages: Int): List<Int>{
    if (width * height % 2 != 0) {
        throw IllegalArgumentException("Illegal sizes, must have even number of cells")
    }
    val indexes = MutableList(numImages) {i -> i}
    indexes.shuffle()
    val list = MutableList(width * height) { i -> indexes[i/2]}
    return list.shuffled()
}
fun generateImageStates(imageIndexes: List<Int>, imageResources: IntArray): List<ImageState> = imageIndexes.map { ImageState(imageResources[it], true) }

fun generateImageIndexList(length: Int, numImages: Int): List<Int>{
    val indexes = MutableList(numImages) {i -> i}
    indexes.shuffle()
    return  indexes.take(length)
}
fun generateImageList(imageIndexes: List<Int>, imageResources: IntArray): List<Int> = imageIndexes.map { imageResources[it] }

fun List<Int>.appendAndShuffle(list: List<Int>) = this.plus(list).shuffled()