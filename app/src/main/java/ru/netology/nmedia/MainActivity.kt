package ru.netology.nmedia

import android.os.Bundle
import android.widget.ImageButton
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.databinding.ActivityMainBinding
import kotlin.math.absoluteValue

//import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val post = Post(1, "Vova", "Let it crash", "21.04.2022",count_reposts = 999)

        binding.render(post)

        binding.likes.setOnClickListener {
            post.liked = !post.liked
            binding.likes.setImageResource(getImageRes(post.liked))
            binding.countLikes.text = getSmthCounted(post, post.liked).toString()
        }
        binding.share.setOnClickListener {
            post.shared = true
            binding.countReposts.text = (post.count_reposts++).toString()
        }//по первому клику сумма остается 0

    }

    private fun ActivityMainBinding.render(post: Post){
        author.text = post.author
        date.text = post.date
        content.text = post.content
        likes.setImageResource(getImageRes(post.liked))
        countLikes.text = getSmthCounted(post, post.liked).digitToChar().toString()
        countReposts.text = spellCounterOfRepo(post)
    }

    @DrawableRes
    private fun getImageRes(isLiked: Boolean) = if (isLiked) R.drawable.liked_24 else R.drawable.likes_24dp
    private fun getSmthCounted(post: Post,likedIs: Boolean): Int = if(likedIs) 1 else  0

    private fun spellCounterOfRepo(post: Post):String{
        return when{
            post.count_reposts >= 1000000 -> "1M"
            post.count_reposts >= 10000 -> "${(post.count_reposts/10000).absoluteValue}K"
            post.count_reposts >= 1000 ->"${post.count_reposts/1000}K"
            else -> post.count_reposts.toString()
        }
    }
}