package ru.netology.nmedia

data class Post(
    val id: Int,
    val author: String,
    val content: String,
    val date: String,
    val liked: Boolean = false,
    //var count_likes: Int = 0,
    var count_reposts: Int = 0
)
