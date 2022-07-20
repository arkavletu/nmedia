package ru.netology.nmedia

import androidx.lifecycle.LiveData

interface PostRepo {
    val data: LiveData<List<Post>>
    fun like(postId: Long)
    fun share(postId: Long)
    fun delete(postId: Long)
    fun save(post: Post)
   // fun cansel(post: Post)

    companion object{
        const val NEWPOSTID = 0L
    }
}