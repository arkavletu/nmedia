package ru.netology.nmedia

import androidx.lifecycle.LiveData

interface PostRepo {
    val data: LiveData<Post>
    fun like()
    fun share()
}