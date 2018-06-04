# Hello, Dagger2
> A fast dependency injector for Android and Java.

##### 1. 什么是Dagger2?
> Google: Dagger 2 is a compile-time evolution approach to dependency injection. Taking the approach started in Dagger 1.x to its ultimate conclusion, Dagger 2.x eliminates all reflection, and improves code clarity by removing the traditional ObjectGraph/Injector in favor of user-specified @Component interfaces.

- Android和java的快速依赖注入，在编译期间自动生成代码，负责依赖对象的创建。另外，以Dagger 1.x开始的方法达到其最终结论，Dagger 2.x消除了所有反射，并且通过移除传统的ObjectGraph / Injector而改进了代码清晰度，而改为用户指定的@Component接口。大概意思就是Dagger2使用过程中性能消耗上控制的很好，没使用反射，所有的注解均停留在编译时期。

##### 2. 为什么使用Dagger2?
> Dependency injection (as a pattern not a library) benefits almost all code. Both performance / extending / testable and etc.

- 增加开发效率、省去重复的简单体力劳动
> 首先new一个实例的过程是一个重复的简单体力劳动，dagger2完全可以把new一个实例的工作做了，因此我们把主要精力集中在关键业务上、同时也能增加开发效率上。
   省去写单例的方法，并且也不需要担心自己写的单例方法是否线程安全，自己写的单例是懒汉模式还是饿汉模式。因为dagger2都可以把这些工作做了。
- 更好的管理类实例
> 每个app中的ApplicationComponent管理整个app的全局类实例，所有的全局类实例都统一交给ApplicationComponent管理，并且它们的生命周期与app的生命周期一样。
  每个页面对应自己的Component，页面Component管理着自己页面所依赖的所有类实例。
  因为Component，Module，整个app的类实例结构变的很清晰。
- 解耦
> 假如不用dagger2的话，一个类的new代码是非常可能充斥在app的多个类中的，假如该类的构造函数发生变化，那这些涉及到的类都得进行修改。设计模式中提倡把容易变化的部分封装起来。在使用Dagger2后,通过用注解标注的构造函数创建类实例，则即使构造函数变的天花乱坠，我们基本上都不需要修改任何代码。

[Demo Code](file:///Users/Vincent/Github/AndroidCodeBuckets/AndroidDagger2/app/src/main/java/me/onez/androiddagger2/DemoNoDagger2Activity.kt)

##### 3. Dagger2 API
```java
public @interface Component {
    Class<?>[] modules() default {};
    Class<?>[] dependencies() default {};
}

public @interface Subcomponent {
    Class<?>[] modules() default {};
}

public @interface Module {
    Class<?>[] includes() default {};
}

public @interface Provides {
}

public @interface MapKey {
    boolean unwrapValue() default true;
}

public interface Lazy<T> {
    T get();
}
```

- Dagger2中用到的定义在Java依赖注入标准(JSR-330)中的元素
```java
public @interface Inject {
}

public @interface Scope {
}

public @interface Qualifier {
}
```
------------
# @Inject
> 标记那些应该被依赖注入框架提供的依赖。Dagger2中有以下3种方式提供依赖:
1. 构造器注入
    - 直接注入构造器,告诉Dagger2可以使用这个构造器构建对象
    - 注入构造器所需要的参数的依赖
    > 局限性: 如果有多个构造器，我们只能标注其中一个，无法标注多个

    [Demo Code](file:///Users/Vincent/Github/AndroidCodeBuckets/AndroidDagger2/app/src/main/java/me/onez/androiddagger2/Presenter.kt)
2. 方法注入
    > 该方法不能是抽象方法，不能用`private`修饰
    ```
    private lateinit var dependencyA: DependencyA

    @Inject
    fun setDenpendencyA(dependencyA: DependencyA) {
      this.dependencyA = dependencyA
    }
    ```
    > 如果目的是注入属性的话，方法注入和属性注入基本没有区别，属性注入是Dagger2中使用最多的一个注入方式。但是在某些特殊情况下会用到方法注入，如当我们希望传入类的当前实例（this引用）到被注入的依赖中。方法注入会在构造器调用后马上被调用，所以这表示我们可以传入完全被构造的this。

3. 属性注入
    > 注解在属性中表示该属性需要依赖注入，不能使用`private`修饰
    ```kotlin
    @Inject
    lateinit var presenter: Presenter
    ```

*`Dagger2容器先调用属性注入，然后再方法注，如:`*
```kotlin
  @Inject
  lateinit var dependencyA: DependencyA

   @Inject
   fun setDenpendencyA(dependencyA: DependencyA) {
     this.dependencyA = dependencyA
   }
```
*`dependencyA 会注入两次,两次注入的 dependencyA 也不相同`*

# @Module
> 使用@Inject标记构造器提供依赖是有局限性的，比如说我们需要注入的对象是第三方库提供的，我们无法在第三方库的构造器上加上@Inject注解。
  或者，我们使用依赖倒置的时候，因为需要注入的对象是抽象的，@Inject也无法使用，因为抽象的类并不能实例化，所以该注解与`@Provides`结合为Dagger2提供依赖关系，用于不能用@Inject提供依赖的地方。
# @Provides
> `@Provides`仅能注解方法，且方法所在类要有`@Module`注解。注解后的方法表示Dagger2能用该方法实例对象提供依赖。按照惯例，`@Provides`方法的命名以provide为前缀，方便阅读管理。

[Demo Code](file:///Users/Vincent/Github/AndroidCodeBuckets/AndroidDagger2/app/src/main/java/me/onez/androiddagger2/dagger2/DemoModule.kt)

# @Component
> 一般用来注解接口，被注解的接口在编译时会生成相应的实例，实例名称一般以Dagger为前缀，作为所需注入依赖和提供依赖之间的桥梁，把提供的依赖注入到所需注入的依赖中。Component中一般使用两种方式定义方法:
1. `void inject(目标类 obj): `Dagger2会从目标类开始查找@Inject注解，自动生成依赖注入的代码，调用inject可完成依赖的注入。
2. `Object getObj(): `Dagger2会到提供依赖注入类中找被@Inject注解标注的构造器，自动生成提供注入类的依赖的代码，这种方式一般为其他Component提供依赖。
> `Componet`可以指定多个module,也可以依赖其他`Component`

`@Component`中的依赖:

- dependency

    [Demo Code](file:///Users/Vincent/Github/AndroidCodeBuckets/AndroidDagger2/app/src/main/java/me/onez/androiddagger2/DepDagger2Activity.kt)

- @Subcomponent
> dependency和@SubComponent都可以实现相同的依赖结果，@SubComponent更多用于其subcomponent作为component的扩展的时候。
譬如很多工具类都需要使用到Application的Context对象，此时就可以用一个Component负责提供，我们可以命名为AppComponent。需要用到的context对象的SharePreferenceComponent，ToastComponent就可以它作为Subcomponent存在了。而在AppComponent中，我们可以很清晰的看到有哪些子Component。

[Demo Code](file:///Users/Vincent/Github/AndroidCodeBuckets/AndroidDagger2/app/src/main/java/me/onez/androiddagger2/SubDagger2Activity.kt)

##### Component dependencies和SubComponent区别
1. Component dependencies 能单独使用，而Subcomponent必须由Component调用方法获取。
2. Component dependencies 可以很清楚的得知他依赖哪个Component， 而Subcomponent不能

# @Qualifier和@Named
> @Qualifier是限定符，而@Named则是基于String的限定符。当我有两个相同的依赖（都继承某一个父类或者都是先某一个接口）可以提供给高层时，那么程序就不知道我们到底要提供哪一个依赖，因为它找到了两个。这时候我们就可以通过限定符为两个依赖分别打上标记，指定提供某个依赖。
[Demo Code](file:///Users/Vincent/Github/AndroidCodeBuckets/AndroidDagger2/app/src/main/java/me/onez/androiddagger2/dagger2/DemoModule.kt)

* `@Qualifier`的作用和`@Named`是完全一样的，不过更推荐使用`@Qualifier`，因为@Named需要手写字符串，容易出错。
* `@Qualifier`不是直接注解在属性上的，而是用来自定义注解的。[Demo Code](file:///Users/Vincent/Github/AndroidCodeBuckets/AndroidDagger2/app/src/main/java/me/onez/androiddagger2/dagger2/AnnoC.kt)

# @Scope和@Singleton
> @Scope和@Singleton，控制所提供依赖的生命周期，使提供的依赖可以做到与视图的生命周期相同、局部单例或者是全局单例等。而@Singleton则是@Scope的默认实现。[Demo Code](file:///Users/Vincent/Github/AndroidCodeBuckets/AndroidDagger2/app/src/main/java/me/onez/androiddagger2/dagger2/LocalScope.kt)
1. 父Component有scope则子Component也必须有scope，而且不能和父scope一样
2. @Scope是需要成对存在的，在Module的Provide方法中使用了@Scope，那么对应的Component中也必须使用@Scope注解，当两边的@Scope名字一样时（比如同为@Singleton）, 那么该Provide方法提供的依赖将会在Component中保持“局部单例”。
   而在Component中标注@Scope，provide方法没有标注，那么这个Scope就不会起作用，而Component上的Scope的作用也只是为了能顺利通过编译

单例实现方式:

1. 依赖在Component中是单例的（供该依赖的provide方法和对应的Component类使用同一个Scope注解。）
2. 对应的Component在App中只初始化一次，每次注入依赖都使用这个Component对象。（在Application中创建该Component）

使用总结:

1. @Component关联的@Module中的任何一个@Provides有@scope，则该整个@Component要加上这个scope。否则在暴露或者注入时会报错
2. @Component的dependencies与@Component自身的scope不能相同，即组件之间的scope不能相同
3. @Singleton的组件不能依赖其他scope的组件，但是其他scope的组件可以依赖@Singleton组件
4. 没有scope的不能依赖有scope的组件
5. 一个component不能同时有多个scope(Subcomponent除外)