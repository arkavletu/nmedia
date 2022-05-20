package ru.netology.nmedia

import android.os.Bundle
import android.widget.ImageButton
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.databinding.ActivityMainBinding

//import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val post = Post(1, "Vova", "Let it crash", "21.04.2022")

        binding.render(post)

        binding.likes.setOnClickListener {
            post.liked = !post.liked
            binding.likes.setImageResource(getImageRes(post.liked))
            binding.countLikes.text = getSmthCounted(post, post.liked).toString()
        }
        binding.share.setOnClickListener {
            post.shared = !post.shared
            binding.countReposts.text = (post.count_reposts++).toString()
        }// CHECK THE 1St DIGIT IS 0

    }

    private fun ActivityMainBinding.render(post: Post){
        author.text = post.author
        date.text = post.date
        content.text = post.content
        likes.setImageResource(getImageRes(post.liked))
        countLikes.text = getSmthCounted(post, post.liked).digitToChar().toString()
        countReposts.text = post.count_reposts.toString()
    }

    @DrawableRes
    private fun getImageRes(isLiked: Boolean) = if (isLiked) R.drawable.liked_24 else R.drawable.likes_24dp
    private fun getSmthCounted(post: Post,likedIs: Boolean): Int = if(likedIs) 1 else  0
    //private fun getCountRepo(post: Post, isShared: Boolean) = if (isShared) +1 else post.count_reposts
}