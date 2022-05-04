package ru.netology.nmedia

data class Post(
    val id: Int,
    val author: String,
    val content: String,
    val date: String,
    var liked: Boolean = false,
    var count_likes: Int
)
