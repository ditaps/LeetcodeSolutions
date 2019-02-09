package com.leetcode

/**
  * Leetcode的提交代码
  *
  * @author lirongrong
  * @since 2019-2-8
  **/
object SolutionScala {
  def main(args: Array[String]): Unit = {
    //    val a = Array(7, 2, 17, 11, 15)
    //    val b = 9
    val a = Array(3, 3)
    val b = 6
    val ans = twoSum(a, b)
    println(ans.mkString(", "))
  }

  /**
    * 两数之和
    *
    * @param nums   数组
    * @param target 目标值
    * @return
    */
  def twoSum(nums: Array[Int], target: Int): Array[Int] = {
    val map = collection.mutable.Map[Int, Int]()
    for (i <- nums.indices) {
      map.get(target - nums(i)) match {
        case Some(x) => return Array(x, i)
        case None => map.put(nums(i), i)
      }
    }
    Array[Int]()
  }
}
