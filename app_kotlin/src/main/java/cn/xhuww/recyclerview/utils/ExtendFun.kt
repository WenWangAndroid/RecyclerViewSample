package cn.xhuww.recyclerview.utils

import android.content.Context
import android.content.Intent

fun Context.startActivity(clazz: Class<*>) {
    startActivity(Intent(this, clazz))
}

