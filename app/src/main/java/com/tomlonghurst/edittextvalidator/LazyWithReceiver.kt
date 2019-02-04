package com.tomlonghurst.edittextvalidator

import java.util.*
import kotlin.reflect.KProperty

internal class LazyWithReceiver<This,Return>(val initializer:This.()->Return)
{
    private val values = WeakHashMap<This,Return>()

    @Suppress("UNCHECKED_CAST")
    internal operator fun getValue(thisRef:Any,property: KProperty<*>):Return = synchronized(values)
    {
        thisRef as This
        return values.getOrPut(thisRef) {thisRef.initializer()}
    }
}