package ru.netology.nmedia

import androidx.annotation.DrawableRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class PostRepoInMemoryImpl: PostRepo {
    override val data = MutableLiveData(
        List(10) { index ->
            Post(
                index + 1L,
                "Vova",
                "Let it crash $index",
                "29.06.2022",
                count_reposts = 1999
            )
        }
    )

    private val posts get() = checkNotNull(data.value){"no nullable"}


    override fun like(postId: Long) {
        data.value = posts.map{
            if(it.id == postId) it.copy(liked = !it.liked, count_likes = if(!it.liked) 1 else  0) else it
        //check me
        }
    }
//    override fun share(){
//        val oldPost = checkNotNull(data.value){"no nullable"}
//        val newPost = oldPost.copy(count_reposts = oldPost.count_reposts + 1)
//        data.value = newPost
//    }

}