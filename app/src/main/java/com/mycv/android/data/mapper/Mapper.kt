package com.mycv.android.data.mapper

interface Mapper<T, R> {

    fun map(item: T): R

}