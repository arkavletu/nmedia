package ru.netology.nmedia


import androidx.lifecycle.MutableLiveData

class PostRepoInMemoryImpl: PostRepo {
    override val data = MutableLiveData(Post(
        1,
        "Vova",
        "Let it crash",
        "21.04.2022",
        countReposts = 1999
    ))


    override fun like() {
        val oldPost = checkNotNull(data.value){"no nullable"}
        data.value  = oldPost.copy(liked = !oldPost.liked, countLikes = if(!oldPost.liked) 1 else  0)

    }
    override fun share(){
        val oldPost = checkNotNull(data.value){"no nullable"}
        val newPost = oldPost.copy(countReposts = oldPost.countReposts + 1)
        data.value = newPost
    }

}