package ru.netology.nmedia

import androidx.lifecycle.ViewModel

class PostViewModel: ViewModel() {
    private val repo: PostRepo = PostRepoInMemoryImpl()
    val data by repo::data
    fun likePost(post: Post) = repo.like(post.id)
    fun sharePost(post: Post) = repo.share(post.id)
}