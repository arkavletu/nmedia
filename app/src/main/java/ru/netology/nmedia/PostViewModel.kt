package ru.netology.nmedia

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PostViewModel: ViewModel(), PostActionListener {
    private val repo: PostRepo = PostRepoInMemoryImpl()
    val data by repo::data
    val currentPost = MutableLiveData<Post?>(null)

    fun onSaveClicked(content: String){
        if (content.isBlank()) return

        val post = currentPost.value?.copy(
            content = content
        )?:Post(
            id = PostRepo.NEWPOSTID,
            content = content,
            author = "Smbd",
            date = "12.07"
        )
        repo.save(post)
        currentPost.value = null
    }

    override fun onLikeClicked(post: Post) =
       repo.like(post.id)


    override fun onShareClicked(post: Post) =
        repo.share(post.id)


    override fun onDeleteClicked(post: Post) =
        repo.delete(post.id)

    override fun onEditClicked(post: Post) {
        currentPost.value = post

    }


}