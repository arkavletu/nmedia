package ru.netology.nmedia

import android.net.Uri

interface PostActionListener {
    fun onLikeClicked(post: Post)
    fun onShareClicked(post: Post)
    fun onFabClicked()
    fun onDeleteClicked(post: Post)
    fun onEditClicked(post: Post)
    fun onPlayClicked(post: Post)
    fun onPostClicked(id:Long)
}