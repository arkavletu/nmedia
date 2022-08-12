package ru.netology.nmedia

interface PostActionListener {
    fun onLikeClicked(post: Post)
    fun onShareClicked(post: Post)
    fun onFabClicked()
    fun onDeleteClicked(post: Post)
    fun onEditClicked(post: Post)
}