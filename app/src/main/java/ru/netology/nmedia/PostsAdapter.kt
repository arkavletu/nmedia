package ru.netology.nmedia

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.databinding.PostBinding
import java.math.RoundingMode
import kotlin.properties.Delegates

internal class PostsAdapter(
//    private val likePost: onLikeOrShareClick,
//    private val share: onLikeOrShareClick
    private val actionListener: PostActionListener
) : ListAdapter<Post, PostsAdapter.ViewHolder>(DiffSearcher) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = PostBinding.inflate(inflater, parent, false)
        return ViewHolder(binding, actionListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    inner class ViewHolder(
        private val binding: PostBinding,
        listener: PostActionListener
    ) :
        RecyclerView.ViewHolder(binding.root) {
        private lateinit var post: Post
        private val popupMenu by lazy {
            PopupMenu(itemView.context, binding.options).apply {
                inflate(R.menu.menu)
                setOnMenuItemClickListener {
                    when (it.itemId) {
                        R.id.remove -> {
                            listener.onDeleteClicked(post)
                            true
                        }
                        R.id.edit -> {
                            listener.onEditClicked(post)
                            true
                        }
//
                        else -> false
                    }
                    //else -> false
                }
            }
        }

        init {
            binding.likes.setOnClickListener {
                listener.onLikeClicked(post)
            }
            binding.share.setOnClickListener {
                listener.onShareClicked(post)
            }
        }

        fun bind(post: Post) {
            this.post = post
            with(binding)
            {
                author.text = post.author
                content.text = post.content
                date.text = post.date
                likes.setImageResource(if (post.liked) R.drawable.liked_24 else R.drawable.likes_24dp)
                countLikes.text = post.count_likes.toString()// spellCounterOfSmth(post.count_likes)
                countReposts.text = spellCounterOfSmth(post.count_reposts)
                options.setOnClickListener { popupMenu.show() }
            }
        }
    }

    private object DiffSearcher : DiffUtil.ItemCallback<Post>() {
        override fun areItemsTheSame(oldItem: Post, newItem: Post) = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Post, newItem: Post) = oldItem == newItem

    }

    private fun spellCounterOfSmth(sum: Int): String {
        val thousand = 1000.0
        val tenThousand = 10000.0
        val million = 1000000.0
        return when {
            sum >= million -> "${
                if (sum % million > tenThousand * 10)
                    (sum / million).toBigDecimal().setScale(1, RoundingMode.DOWN)
                else (sum / million).toInt()
            }M"
            sum.toDouble() in thousand..million -> "${
                if (sum < tenThousand && sum % thousand >= 100)
                    (sum / thousand).toBigDecimal().setScale(1, RoundingMode.DOWN)
                else (sum / thousand).toInt()
            }K"
            else -> sum.toString()
        }
    }
}