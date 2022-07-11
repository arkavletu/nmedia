package ru.netology.nmedia

import androidx.lifecycle.ViewModel

class PostViewModel: ViewModel(), PostActionListener {
    private val repo: PostRepo = PostRepoInMemoryImpl()
    val data by repo::data
   // fun likePost(post: Post) =
   // fun sharePost(post: Post) =
   // fun deletePost(post: Post) =
    override fun onLikeClicked(post: Post) =
       repo.like(post.id)


    override fun onShareClicked(post: Post) =
        repo.share(post.id)


    override fun onDeleteClicked(post: Post) =
        repo.delete(post.id)


}