package ru.netology.nmedia

import androidx.annotation.DrawableRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class PostRepoInMemoryImpl: PostRepo {
    override val data = MutableLiveData(Post(
        1,
        "Vova",
        "Let it crash",
        "21.04.2022",
        count_reposts = 1999
    ))


    override fun like() {
        val oldPost = checkNotNull(data.value){"no nullable"}
        val newPost = oldPost.copy(liked = !oldPost.liked, count_likes = getLikesCounted(!oldPost.liked))
        data.value = newPost
    }
    override fun share(){
        val oldPost = checkNotNull(data.value){"no nullable"}
        val newPost = oldPost.copy(count_reposts = +1)
    }

    private fun getLikesCounted(isLiked: Boolean): Int = if(isLiked) 1 else  0
}