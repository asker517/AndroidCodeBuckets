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
