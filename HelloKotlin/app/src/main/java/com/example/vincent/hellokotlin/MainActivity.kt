package com.example.vincent.hellokotlin

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Button
import android.widget.TextView
import kotlinx.coroutines.experimental.*
import kotlin.system.measureTimeMillis

/**
 * https://my.oschina.net/zzxzzg/blog/1612992
 */
class MainActivity : AppCompatActivity() {

  private val TAG = MainActivity::class.java.simpleName

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    findViewById<TextView>(R.id.tv_test).setOnClickListener {
      //      simpleTest()
      //      blockingTest()
      //      jobTest()
      //      diffThreadTest()
      cancelTest()
    }


    findViewById<Button>(R.id.btn_test).setOnClickListener {
      //      suspendTest()
      asyncTest()
    }
  }

  /**
   * simple launch test
   */
  private fun simpleTest() {
    GlobalScope.launch {

      //2 ForkJoinPool.commonPool-worker-1
      Log.d(TAG, "simpleTest: " + Thread.currentThread().name)

      delay(2000)

      //3 ForkJoinPool.commonPool-worker-1
      Log.d(TAG, "simpleTest: " + Thread.currentThread().name + " after delay codes")
    }

    // 1 main thread
    Log.d(TAG, "simpleTest: " + Thread.currentThread().name + " out of coroutines")
  }

  /**
   * blocking test
   */
  private fun blockingTest() {
    runBlocking {

      //1 blockingTest: main
      Log.d(TAG, "blockingTest: " + Thread.currentThread().name)

      delay(2000)

      //2 blockingTest: main after delay codes
      Log.d(TAG, "blockingTest: " + Thread.currentThread().name + " after delay codes")
    }

    //blockingTest: main out of coroutines
    Log.d(TAG, "blockingTest: " + Thread.currentThread().name + " out of coroutines")

  }

  /**
   * launch job test
   */
  private fun jobTest() {

    runBlocking {

      //0 main
      Log.d(TAG, "jobTest: " + Thread.currentThread().name)

      val job = GlobalScope.launch {

        //1 ForkJoinPool.commonPool-worker-1
        Log.d(TAG, "jobTest: " + Thread.currentThread().name)

        delay(2000)

        //2 ForkJoinPool.commonPool-worker-1
        Log.d(TAG, "jobTest: " + Thread.currentThread().name + " after delay codes")
      }

      job.join()

      //3 main out of launch
      Log.d(TAG, "jobTest: " + Thread.currentThread().name + " out of launch")

    }

    // 4 main out of runBlocking
    Log.d(TAG, "jobTest: " + Thread.currentThread().name + " out of runBlocking")

  }

  private fun diffThreadTest() {

    runBlocking {
      //1 main
      Log.d(TAG, "diffThreadTest: run blocking " + Thread.currentThread().name)

      val jobs = List(10000) {
        //2 main
        Log.d(TAG, "diffThreadTest: $it " + Thread.currentThread().name)

        GlobalScope.launch {

          //3 loop ForkJoinPool.commonPool-worker
          Log.d(TAG, "diffThreadTest: launch " + Thread.currentThread().name)

          delay(2000)

          //4 loop ForkJoinPool.commonPool-worker-
          Log.d(TAG, "diffThreadTest: after delay " + Thread.currentThread().name)
        }

      }

      //5 main
      Log.d(TAG, "diffThreadTest: out of List " + Thread.currentThread().name)

      jobs.forEach { it.join() }

    }

    //last main
    Log.d(TAG, "diffThreadTest: out of blocking " + Thread.currentThread().name)

  }

  /**
   * time out test
   */
  private fun cancelTest() {

    runBlocking {

      try {
        withTimeout(2000) {
          try {
            repeat(10) {

              // 1 main repeat 4 times
              Log.d(TAG, "cancelTest: $it " + Thread.currentThread().name)
              delay(500)
            }
          } catch (e: Exception) {

            // 2 main
            Log.d(TAG, "cancelTest: exception IN " + Thread.currentThread().name)
          }

          // 3 main
          Log.d(TAG, "cancelTest: in timeout " + Thread.currentThread().name)
        }
      } catch (e: Exception) {

        //4 main
        Log.d(TAG, "cancelTest: exception OUT")
      }

      //5 main
      Log.d(TAG, "cancelTest: out of withTimeOut " + Thread.currentThread().name)
    }
  }


  //----------------------------------------------------


  private suspend fun oneThingNeedTime(): Int {
    Log.d(TAG, "oneThingNeedTime: one " + Thread.currentThread().name)
    delay(2000)
    return 20
  }

  private suspend fun moreThingNeedTime(): Int {
    Log.d(TAG, "oneThingNeedTime: more " + Thread.currentThread().name)
    delay(3000)
    return 40
  }

  /**
   * suspend test cost 5000ms +
   */
  private fun suspendTest() {

    GlobalScope.launch {

      val time = measureTimeMillis {
        //2 Thread[ForkJoinPool.commonPool-worker-1,5,main]
        Log.d(TAG, "suspendTest: launch " + Thread.currentThread())

        val one = oneThingNeedTime()
        val more = moreThingNeedTime()

        //3 ForkJoinPool.commonPool-worker-2
        Log.d(TAG, "suspendTest: ${one + more} " + Thread.currentThread().name)
      }

      //4 cost 5006 msForkJoinPool.commonPool-worker-2
      Log.d(TAG, "suspendTest: cost $time ms " + Thread.currentThread().name);
    }

    //1 main
    Log.d(TAG, "suspendTest: out of launch " + Thread.currentThread().name)

  }

  /**
   * async test
   */
  private fun asyncTest() {

    runBlocking {

      val time = measureTimeMillis {

        val one = GlobalScope.async { oneThingNeedTime() }
        val more = GlobalScope.async { moreThingNeedTime() }

        //1 main
        Log.d(TAG,
            "asyncTest: result is ${one.await() + more.await()} " + Thread.currentThread().name)
      }

      //2 30001 main
      Log.d(TAG, "asyncTest: cost $time " + Thread.currentThread().name)
    }

  }


}
