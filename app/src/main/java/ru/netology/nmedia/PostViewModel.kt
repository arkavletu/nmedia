package ru.netology.nmedia

import SingleLiveEvent
import android.content.Intent
import android.net.Uri
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.net.URI

class PostViewModel: ViewModel(), PostActionListener {
    private val repo: PostRepo = PostRepoInMemoryImpl()
    val data by repo::data
    val currentPost = MutableLiveData<Post?>(null)
    val sharePost = SingleLiveEvent<String>()
    val navigateToEditScreenEvent = SingleLiveEvent<Unit>()
    val playVideoEvent = SingleLiveEvent<String>()

    fun onSaveClicked(content: String){
        if (content.isBlank()) return

        val post = currentPost.value?.copy(
            content = content
        )?:Post(
            id = PostRepo.NEWPOSTID,
            content = content,
            author = "Smbd",
            date = "12.07",
        )
        repo.save(post)
        currentPost.value = null
    }

    override fun onLikeClicked(post: Post) =
       repo.like(post.id)


    override fun onShareClicked(post: Post) {
        sharePost.value = post.content
        repo.share(post.id)
    }


    override fun onFabClicked() {
        navigateToEditScreenEvent.call()
    }


    override fun onDeleteClicked(post: Post) =
        repo.delete(post.id)

    override fun onEditClicked(post: Post) {
        currentPost.value = post
        navigateToEditScreenEvent.call()

    }

    override fun onPlayClicked(post: Post) {
        playVideoEvent.value = post.video

    }





}