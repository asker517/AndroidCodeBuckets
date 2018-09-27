package com.example.vincent.hellokotlin

import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

  @Before
  fun setUp(){
    println("Starting to test")
  }

  @Test
  fun addition_isCorrect() {
    assertEquals(4, 2 + 2)
  }

  @After
  fun tearDown(){
    println("Testing is finish")
  }
}
