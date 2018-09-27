## Kotlin Coroutines

`https://github.com/Kotlin/kotlinx.coroutines`

#### 基本概念

`一些 API 启动长时间运行的操作（例如网络 IO、文件 IO、CPU 或 GPU 密集型任务等），并要求调用者阻塞直到它们完成。协程提供了一种避免阻塞线程并用更廉价、更可控的操作替代线程阻塞的方法：协程挂起。`

`协程通过将复杂性放入库来简化异步编程。程序的逻辑可以在协程中顺序地表达，而底层库会为我们解决其异步性。该库可以将用户代码的相关部分包装为回调、订阅相关事件、在不同线程（甚至不同机器！）上调度执行，而代码则保持如同顺序执行一样简单。`

#### Your first coroutine

```kotlin
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
````

#### 阻塞 VS 挂起

`基本上，协程计算可以被挂起而无需阻塞线程。线程阻塞的代价通常是昂贵的，尤其在高负载时，因为只有相对少量线程实际可用，因此阻塞其中一个会导致一些重要的任务被延迟。`

`另一方面，协程挂起几乎是无代价的。不需要上下文切换或者 OS 的任何其他干预。最重要的是，挂起可以在很大程度上由用户库控制：作为库的作者，我们可以决定挂起时发生什么并根据需求优化/记日志/截获。`

`另一个区别是，协程不能在随机的指令中挂起，而只能在所谓的挂起点挂起，这会调用特别标记的函数。`

 `runBlocking`方法的操作是创建一个新的协程运行，并且阻塞当前线程，直到协程中的工作结束
```kotlin
private fun blockingTest() {
    runBlocking {

      //1 blockingTest: main
      Log.d(TAG, "blockingTest: " + Thread.currentThread().name)

      delay(2000)

      //2 blockingTest: main after delay codes
      Log.d(TAG, "blockingTest: " + Thread.currentThread().name + " after delay codes")
    }

    //3 blockingTest: main out of coroutines
    Log.d(TAG, "blockingTest: " + Thread.currentThread().name + " out of coroutines")

  }
```
#### Job
当调用launch之后，会返回一个Job类型的对象来表示这项工作，我们可以使用job的join方法等待这个工作完成
```kotlin
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
  ```

#### suspend
当我们调用标记有特殊修饰符 suspend 的函数时，会发生挂起:
```kotlin
suspend fun doSomething(foo: Foo): Bar { …… }
```
这样的函数称为挂起函数，因为调用它们可能挂起协程（如果相关调用的结果已经可用，库可以决定继续进行而不挂起）。挂起函数能够以与普通函数相同的方式获取参数与返回值，但它们只能从协程、其他挂起函数以及内联到其中的函数字面值中调用。

#### 轻量级异步
开启10000个协程:
```kotlin
private fun coroutinesTest() {
    runBlocking {
      val jobs = List(10000) {
        launch {

          Log.d(TAG, "coroutinesTest1: " + Thread.activeCount())

          delay(1000)

          Log.d(TAG, "..")
        }
      }
      jobs.forEach { it.join() }
    }
  }
```
使用协程，虽然我们起了N多协程，但是如果打印的activeCount大概只有6个，不会再增多。而如果这里将协程替换为线程，那么……绝大多数情况会出现out of memory的错误

#### 停止协程 cancel
- 我们可以在job上调用cancel() 或者cancelAndJoin停止一个协程
- 协程的cancel和线程的cancel非常类似，它并不会强制停止协程，而是在协程运算结束的时候停止
```kotlin
fun cancelTest()= runBlocking<Unit>{
    val startTime = System.currentTimeMillis()
    val job = launch {
        var nextPrintTime = startTime
        var i = 0
        while (i < 5) { // computation loop, just wastes CPU
            // print a message twice a second
            if (System.currentTimeMillis() >= nextPrintTime) {
                println("I'm sleeping ${i++} ...")
                nextPrintTime += 500L
            }
        }
    }
    delay(1300L) // delay a bit
    println("main: I'm tired of waiting!")
    job.cancelAndJoin() // cancels the job and waits for its completion
    println("main: Now I can quit.")
}
```
上面代码，协程重复循环，并且根据时间打印，此时，协程一直在进行工作占用cpu，当调用cancel之后不会立即停止，而是会等到运算结束。循环5次
```kotlin
private fun cancelTest2() {
    runBlocking {
      val startTime = System.currentTimeMillis()
      val job = launch(Dispatchers.Default) {
        var nextPrintTime = startTime
        var i = 0
        while (isActive) { // cancellable computation loop
          // 每秒打印2次信息
          if (System.currentTimeMillis() >= nextPrintTime) {
            println("I'm sleeping ${i++} ...")
            nextPrintTime += 500L
          }
        }
      }
      delay(1300L) // delay a bit
      println("cancelTest1: I'm tired of waiting!")
      job.cancelAndJoin() // cancels the job and waits for its completion
      println("cancelTest1: Now I can quit.")
    }
  }
```
循环3次:
```
I'm sleeping 0 ...
I'm sleeping 1 ...
I'm sleeping 2 ...
cancelTest1: I'm tired of waiting!
cancelTest1: Now I can quit.
```

#### 超时
对于一个协程，我们可以设置它的超时时间，所以相比于手动cancel，timeout或许拥有更好的效果。另外timeout和cancel一样，同样需要协程配合，在运算过程中无法停止。
```kotlin
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
```
以上代码会repeat 4次后停止,并且抛出`TimeoutCancellationException`

#### 编写 suspending functions
- 默认顺序执行
```kotlin
private suspend fun oneThingNeedTime(): Int {
    Log.d(TAG, "oneThingNeedTime: one " + Thread.currentThread().name)
    delay(2000)
    return 20
  }
```
```kotlin
private suspend fun moreThingNeedTime(): Int {
    Log.d(TAG, "oneThingNeedTime: more " + Thread.currentThread().name)
    delay(3000)
    return 40
  }
```
```kotlin
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
      Log.d(TAG, "suspendTest: cost $time ms " + Thread.currentThread().name)
    }

    //1 main
    Log.d(TAG, "suspendTest: out of launch " + Thread.currentThread().name)

  }
```
上述代码最终会耗时5s+

- 异步并发
```kotlin
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
```
上述代码运行时间1s+

从概念上讲，async就像launch。它启动一个单独的协程，它是一个与所有其他协程同时工作的轻量级线程。不同之处在于启动会返回一个Job，而不会带来任何结果值，而异步返回Deferred  - 一个轻量级的非阻塞对象，表示稍后提供结果。你可以使用延迟值的.await（）来得到它的最终结果，但是Deferred也是一个Job，所以你可以根据需要取消它。

#### 协程context & Dispatcher
1. Dispatchers and threads

协程`context`包括`dispatcher`程序，它确定相应的协程用于执行的线程。
协程`dispatcher`r程序可以将协程执行限制在特定线程，将其分派给线程池，或让它无限制地运行。

所有协程构建器，如launch和async 接受一个可选的CoroutineContext参数，可用于显式指定调度程序的新协程和其他上下文元素
```kotlin
private fun dispatcherTest() {
    runBlocking {
      val jobs = arrayListOf<Job>()


      jobs += launch {
        println("none: " + Thread.currentThread().name)
      }

      jobs += launch(Dispatchers.Unconfined) {
        println("Unconfined: " + Thread.currentThread().name)
      }

      jobs += launch(Dispatchers.Default) {
        println("Default: " + Thread.currentThread().name)
      }

      jobs += launch(Dispatchers.IO) {
        println("IO: " + Thread.currentThread().name)
      }

      jobs += launch(coroutineContext) {
        println("coroutineContext: " + Thread.currentThread().name)
      }

      jobs += launch(newSingleThreadContext("dispatchSingleThread")) {
        println("newSingleThreadContext: " + Thread.currentThread().name)
      }

      jobs.forEach { it.join() }
    }
  }
```
以上代码输出结果:
```
Unconfined: main
Default: ForkJoinPool.commonPool-worker-0
IO: CoroutineScheduler-worker-3
none: main
coroutineContext: main
newSingleThreadContext: dispatchSingleThread
```
- 当使用不带参数的`launch{...}`时，它会从正在启动的CoroutineScope中继承`context`（以及`Dispatcher`）。
  在这种情况下，它继承了在主线程中运行的主runBlocking协同程序的`context`

- 在`GlobalScope`中启动协同程序时使用的默认`dispatcher`由`Dispatchers.Default`表示并使用共享的后台线程池，因此`launch（Dispatchers.Default）{...}`使用与`GlobalScope.launch {..}`相同的`dispatcher`。

- `newSingleThreadContext`创建一个新的线程，这是很耗费资源的。在真正的应用程序中，它必须被释放，不再需要时，使用`close`函数，或者存储在顶层变量中，并在整个应用程序中重用

2. Unconfined vs confined dispatcher

- `Dispatchers.Unconfined`协程调度程序在调用程序线程中启动协同程序，但只在第一个挂起点之前。
  暂停后，它将在线程中恢复，该线程完全由调用的挂起函数确定。
  当协同程序不消耗CPU时间,也不更新任何局限于特定线程的共享数据（如UI）时，无限制调度程序是合适的

- 通过`CoroutineScope`接口在任何协程的块内可用的`coroutineContext`属性是对此特定协程的上下文的引用。这样，可以继承父上下文。 `runBlocking`协同程序的默认调度程序特别限于调用程序线程，因此继承它的作用是通过可预测的FIFO调度将执行限制在该线程中

```kotlin
private fun unconfinedTest() {
    runBlocking {
      val jobs = arrayListOf<Job>()

      jobs += launch {
        // not confined -- will work with main thread
        println("      'none': I'm working in thread ${Thread.currentThread().name}")
        delay(500)
        println("      'none': After delay in thread ${Thread.currentThread().name}")
      }

      jobs += launch(Dispatchers.Unconfined) {
        // not confined -- will work with main thread
        println("      'Unconfined': I'm working in thread ${Thread.currentThread().name}")
        delay(500)
        println("      'Unconfined': After delay in thread ${Thread.currentThread().name}")
      }

      jobs += launch(coroutineContext) {
        // context of the parent, runBlocking coroutine
        println("'coroutineContext': I'm working in thread ${Thread.currentThread().name}")
        delay(1000)
        println("'coroutineContext': After delay in thread ${Thread.currentThread().name}")
      }
      jobs.forEach { it.join() }
    }

  }
```
以上输出结果:
```
'Unconfined': I'm working in thread main
'none': I'm working in thread main
'coroutineContext': I'm working in thread main
'Unconfined': After delay in thread kotlinx.coroutines.DefaultExecutor
'none': After delay in thread main
'coroutineContext': After delay in thread main
```
因此，继承了`runBlocking {...}`的`coroutineContext`的协程在主线程中继续执行，而`unconfined`的协程在延迟函数使用的默认执行程序线程中恢复。

#### 协程线程跳转

```kotlin
private fun coroutinesJumping() {
    newSingleThreadContext("ctx1").use { ctx1 ->
      newSingleThreadContext("ctx2").use { ctx2 ->
        runBlocking(ctx1) {
          log("start in ctx1")
          withContext(ctx2) {
            log("working in ctx2")
          }
          log("back to ctx1")
        }
      }
    }
  }
```
它演示了几种新技术。
一个是使用带有明确指定上下文的`runBlocking`，另一个是使用`withContext`函数来更改协程的上下文，同时仍然保持在相同协程：
```
 [ctx1] start in ctx1
 [ctx2] working in ctx2
 [ctx1] back to ctx1
```