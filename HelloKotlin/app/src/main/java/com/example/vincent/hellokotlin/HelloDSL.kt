package com.example.vincent.hellokotlin

/**
 * <P>Created by Vincent on 2018/9/17.</P>
 */
class HelloDSL {

  operator fun invoke(block: HelloDSL.() -> Unit) {
    block()
  }

  private fun kotlinSDL(block: StringBuilder.() -> Unit) {
    StringBuilder("hello").block() //    block(StringBuilder("hello"))
  }

  private fun testDSL() {
    kotlinSDL {
      append("DSL")
      println(this)
    }
  }

  fun testInvoke() {
    val helloDSL = HelloDSL()
    helloDSL {
      testDSL()
    }
  }


}