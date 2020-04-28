@file:JvmName("ListGenerator")
package com.shmagins.superbrain

import java.lang.IllegalArgumentException
import kotlin.random.Random

fun generateResourceList(size: Int, imageResources: IntArray): List<Int>{
    if (size % 2 != 0) {
        throw IllegalArgumentException("Illegal size, must have even number of cells")
    }
    val resCount = imageResources.size
    val indexes = MutableList(resCount) { i -> i}
    indexes.shuffle()
    val list = MutableList(size) { i -> imageResources[indexes[i/2 % resCount]]}
    return list.shuffled()
}

fun generateImageIndexList(length: Int, numImages: Int): List<Int> = MutableList(length) {i -> Random.nextInt(numImages)}

fun generateImageList(imageIndexes: List<Int>, imageResources: IntArray): List<Int> = imageIndexes.map { imageResources[it] }

fun List<Int>.appendAndShuffle(list: List<Int>, limit: Int) = this.plus(list).distinct().take(limit).shuffled()