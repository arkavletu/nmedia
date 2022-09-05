package ru.netology.nmedia

import SingleLiveEvent
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class SinglePostViewModel(
    application: Application
): AndroidViewModel(application), PostActionListener{
    private val repo: PostRepo = PostRepoImplFiles(application)
    val data by repo::data



    override fun onLikeClicked(post: Post) {
    }

    override fun onShareClicked(post: Post) {
    }

    override fun onFabClicked() {
    }

    override fun onDeleteClicked(post: Post) {
    }

    override fun onEditClicked(post: Post) {
    }

    override fun onPlayClicked(post: Post) {
    }

    override fun onPostClicked(id: Long) {
    }


}