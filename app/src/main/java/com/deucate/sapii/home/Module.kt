package com.deucate.sapii.home

data class Module(
        val title: String,
        val detail: String,
        val resourceID: Int,
        val intent: Class<*>
)