package com.phone

object Main extends App {

  if (args.length == 0) {
    println("Read calls.log from classpath")

  } else {
    val filename = args.head
    println(s"Read $filename")

  }

}
