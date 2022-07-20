package ru.netology.nmedia

data class Post(
    val id: Long,
    val author: String,
    val content: String,
    val date: String,
    val liked: Boolean = false,
    val countLikes: Int = 0,
    val countReposts: Int = 0
)
