package ru.netology.nmedia


import androidx.core.util.Preconditions.checkNotNull
import androidx.lifecycle.MutableLiveData

class PostRepoInMemoryImpl : PostRepo {
    private var nextId = GENERATEDAMMOUNT.toLong()

    override val data = MutableLiveData(
        List(GENERATEDAMMOUNT) { index ->
            Post(
                index + 1L,
                "Vova",
                "Let it crash $index",// не видать
                "29.06.2022",
                countReposts = 1999
            )
        }
    )

    private val posts get() = checkNotNull(data.value) { "no nullable" }


    override fun like(postId: Long) {
        data.value = posts.map {
            if (it.id == postId) it.copy(
                liked = !it.liked,
                countLikes = if (!it.liked) 1 else 0
            ) else it
        }
    }

    override fun share(postId: Long) {
        data.value =
            posts.map { if (it.id == postId) it.copy(countReposts = it.countReposts + 1) else it }

    }

    override fun delete(postId: Long) {
        data.value = posts.filter { it.id != postId }
    }

    override fun save(post: Post) {
        if(post.id == PostRepo.NEWPOSTID) insert(post) else update(post)
    }



    private fun update(post: Post) {
        data.value = posts.map{
            if (it.id == post.id) post else it
        }
    }

    private fun insert(post: Post) {
        data.value = listOf(post.copy(id = ++nextId)) + posts

    }

    private companion object{
        const val GENERATEDAMMOUNT = 10
    }

}