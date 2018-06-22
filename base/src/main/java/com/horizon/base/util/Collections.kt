package com.horizon.base.util

fun Map<String, Any>?.getString(key: String): String? {
    return this?.get(key) as String?
}