package com.o7solutions.android_6_month.KotlinBasics

data class Student (
    var name: String,
    var roll_no: Int,
) {

}

fun main() {

    var student1 = Student("Jatin", 786)

//    println(student1.name)
////    println(student1.roll_no)
//
//    var student2 = Student("Harsh",787)
//    var student3 = Student("Kartik",365)
//
//    println(student2.name)
////    println(student2.roll_no)
//    println(student3.name)
//    println(student3.roll_no)

    var arrInt = arrayListOf<Int>()
    arrInt.add(12)
    arrInt.add(14)
    arrInt.add(51)

//    arrInt.clear()
    println(arrInt)
    println(arrInt.reversed())

    if(arrInt.isEmpty()) {
        println("Array is empty")
    }
    else {
        println("Array is not empty")

    }

    print(arrInt.size)
//    for(i in arrInt)
//    {
//        println(i)
//    }




}