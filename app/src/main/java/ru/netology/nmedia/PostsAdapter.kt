package ru.netology.nmedia

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.databinding.PostBinding
import kotlin.properties.Delegates

internal class PostsAdapter(
    private val likePost: (Post) -> Unit
) : ListAdapter<Post, PostsAdapter.ViewHolder>(DiffSearcher) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = PostBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    inner class ViewHolder(private val binding: PostBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private lateinit var post: Post

        init {
            binding.likes.setOnClickListener {
                likePost(post)
            }
        }

        fun bind(post: Post) {
            this.post = post
            with(binding)
            {
                author.text = post.author
                date.text = post.date
                likes.setImageResource(if (post.liked) R.drawable.liked_24 else R.drawable.likes_24dp)
                countLikes.text = post.count_likes.toString()
                //countReposts.text = spellCounterOfSmth(post.count_reposts)

            }
        }
    }

    private object DiffSearcher : DiffUtil.ItemCallback<Post>() {
        override fun areItemsTheSame(oldItem: Post, newItem: Post) = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Post, newItem: Post) = oldItem == newItem

    }
}