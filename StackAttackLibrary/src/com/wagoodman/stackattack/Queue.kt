package com.wagoodman.stackattack

//Queue interface
interface Queue<Type> {
    //Insert a new item into the queue.
    fun enqueue(x: Type)

    //Get the least recently inserted item in the queue.
//Does not alter the queue.
    fun peek(): Type

    //Return and remove the least recently inserted item
//from the queue.
    fun dequeue(): Type

    //Test if the queue is logically empty.
    val isEmpty: Boolean

    //Make the queue logically empty.
    fun makeEmpty()
}