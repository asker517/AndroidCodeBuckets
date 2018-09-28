## Why Coroutines

- 简化异步编程

异步编程常见场景: 在后台线程执行一个复杂任务A，下一个任务B依赖于任务A的执行结果，所以必须等待任务A执行完成后才能开始执行B

NBA项目中类似场景:

```kotlin
//请求栏目index数据
private fun getColumnIndex(column: String): String {

    //make request for column index & change it to string

    return "column index string result"
}

//根据index数据,请求对应index的数据条目
private fun getItems(columnIndex:String):List<Any>{

    //make request for items with column index parameters

    return arrayListOf()
}
```
以上两个函数的操作都是需要网络请求的耗时操作,因此不能直接在UI线程中运行,而且`getItems`函数需要依赖`getColumnIndex`函数的执行结果,所以两个函数不能同时并发运行,通常处理方案如下:

1. 青铜玩家 -- Callback嵌套回调

```kotlin
 private fun getData() {

    //先请求index
    requestColumnIndexAsync(object : Callback {

      override onSuccess(columnIndex:String){

        //请求成功后回调,再继续请求列表数据
        requestItemsAsync(columnIndex,object :Callback{

          override onItemsGetSuccess(items:List<Any>){
            //成功后处理列表数据结果
          }

          override onItemsGetFailed(ex:Excepption){
            //列表数据请求出错
          }
        })
      }

      override onFailed(err:Err){
        //index数据请求出错
      }
    })
  }
```
2. 钻石玩家 -- Rx编程

```
private fun getData(){

    getColumnIndexRx()

        .compose(...)

        .map(...) { ... }

        .flatMap(...) { getItemsRx(columnIndex) }

        .subscribe({ ... })
}
```
3. 王者玩家 -- kotlin Coroutines

```kotlin
 private fun getData() {

    GlobalScope.launch(Dispatchers.Main) {

      val columnIndex = getColumnIndexKt("column")

      val items = getItemsKt(columnIndex)

      //then use items to do some thing....
      // update UI Here ??

    }
  }
```
