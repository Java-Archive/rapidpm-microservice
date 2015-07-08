package org.rapidpm.microservice.demo.model

/**
 * Created by svenruppert on 08.06.15.
 */


data class Response(val txt: String, val name: String);

fun MutableList<Int>.swap(x: Int, y: Int) {
  val tmp = this[x] // 'this' corresponds to the list this[x] = this[y]
  this[y] = tmp
}