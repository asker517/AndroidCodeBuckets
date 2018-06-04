package me.onez.androiddagger2.deps

/**
 * <P>Created by Vincent on 2018/5/29.</P>
 */
class DepB {

  private var depA: DepA?

  constructor(depA: DepA) {
    this.depA = depA
  }

  fun testDepA() {
    depA?.showName()
  }

}