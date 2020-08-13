@file:JvmName("ListGenerator")
package com.shmagins.superbrain

import kotlin.random.Random

fun generateResourceList(size: Int, imageResources: IntArray, limit: Int): List<Int>{
    val indexes = MutableList(limit) { i -> i}
    indexes.shuffle()
    val list = MutableList(size) { i -> imageResources[indexes[i/2 % limit]]}
    return list.shuffled()
}

fun generateImageIndexList(length: Int, numImages: Int): List<Int> = MutableList(numImages) { Random.nextInt(numImages)}.distinct().take(length)

fun generateImageList(imageIndexes: List<Int>, imageResources: IntArray): List<Int> = imageIndexes.map { imageResources[it] }

fun List<Int>.appendAndShuffle(list: List<Int>, limit: Int) = this.plus(list).distinct().take(limit).shuffled()