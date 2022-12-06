package ru.netology.nmedia

import android.app.Application
import android.content.Context
import androidx.core.content.edit
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

import kotlin.properties.Delegates

class PostRepoImplFiles(
    private val application: Application
) : PostRepo {
    private val gson = Gson()
    private val type = TypeToken.getParameterized(List::class.java, Post::class.java).type

    private val prefs = application.getSharedPreferences("repo", Context.MODE_PRIVATE)
    private var nextId by Delegates.observable(
        prefs.getLong(NEXT_ID_KEY, 0L)
    ) { _, _, newValue ->
        prefs.edit { putLong(NEXT_ID_KEY, newValue) }
    }

    override val data: MutableLiveData<List<Post>> = MutableLiveData(emptyList())

    private var posts
        get() = checkNotNull(data.value) { "no nullable" }
        set(value) {
            application.openFileOutput(FILE_NAME, Context.MODE_PRIVATE).bufferedWriter().use {
                it.write(gson.toJson(value))
            }
            data.value = value

        }

    init {
        val file = application.filesDir.resolve(FILE_NAME)
        val posts: List<Post> = if (file.exists()) {
            val input = application.openFileInput(FILE_NAME)
            val reader = input.bufferedReader()
            reader.use {
                gson.fromJson(it, type)
            }
        } else emptyList()
        data.value = posts


    }

    override fun like(postId: Long) {
        posts = posts.map {
            if (it.id == postId) it.copy(
                liked = !it.liked,
                countLikes = if (!it.liked) 1 else 0
            ) else it
        }
    }

    override fun share(postId: Long) {
        posts =
            posts.map { if (it.id == postId) it.copy(countReposts = it.countReposts + 1) else it }

    }

    override fun delete(postId: Long) {
        posts = posts.filter { it.id != postId }
    }

    override fun save(post: Post) {
        if (post.id == PostRepo.NEWPOSTID) insert(post) else update(post)
    }


    private fun update(post: Post) {
        posts = posts.map {
            if (it.id == post.id) post else it
        }
    }

    private fun insert(post: Post) {
        posts = listOf(post.copy(id = ++nextId)) + posts

    }



    private companion object {
        const val NEXT_ID_KEY = "nextId"
        const val FILE_NAME = "post.json"
    }

}
