package ru.netology.nmedia

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
        val oldPost = checkNotNull(data.value){"not nullable"}
        val newPost = oldPost.copy(liked = !oldPost.liked)
        data.value = newPost
    }
}