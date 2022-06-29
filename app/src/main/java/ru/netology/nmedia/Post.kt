package ru.netology.nmedia

data class Post(
    val id: Long,
    val author: String,
    val content: String,
    val date: String,
    val liked: Boolean = false,
    val count_likes: Int = 0,
    val count_reposts: Int = 0
)
