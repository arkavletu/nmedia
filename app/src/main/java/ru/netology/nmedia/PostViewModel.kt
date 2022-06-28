package ru.netology.nmedia

import androidx.lifecycle.ViewModel

class PostViewModel: ViewModel() {
    private val repo: PostRepo = PostRepoInMemoryImpl()
    val data by repo::data
    val likePost = repo.like()
    val sharePost = repo.share()
}