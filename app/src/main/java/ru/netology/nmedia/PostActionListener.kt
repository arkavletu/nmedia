package ru.netology.nmedia

interface PostActionListener {
    fun onLikeClicked(post: Post)
    fun onShareClicked(post: Post)
    fun onDeleteClicked(post: Post)
}