package ru.netology.nmedia

import android.os.Bundle
import android.widget.ImageButton
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.databinding.ActivityMainBinding
import java.math.RoundingMode
import kotlin.math.absoluteValue

//import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val post = Post(1, "Vova", "Let it crash", "21.04.2022",count_reposts = 1999.0)

        binding.render(post)

        binding.likes.setOnClickListener {
            post.liked = !post.liked
            binding.likes.setImageResource(getImageRes(post.liked))
            binding.countLikes.text = getSmthCounted(post).toString()
        }
        binding.share.setOnClickListener {
            post.shared = true
            post.count_reposts++
            binding.countReposts.text = spellCounterOfRepo(post)
        }//сумма меняется со второго клика

    }

    private fun ActivityMainBinding.render(post: Post){
        author.text = post.author
        date.text = post.date
        content.text = post.content
        likes.setImageResource(getImageRes(post.liked))
        countLikes.text = getSmthCounted(post).toString()
        countReposts.text = spellCounterOfRepo(post)
    }

    @DrawableRes
    private fun getImageRes(isLiked: Boolean) = if (isLiked) R.drawable.liked_24 else R.drawable.likes_24dp
    private fun getSmthCounted(post: Post): Int = if(post.liked) 1 else  0

    private fun spellCounterOfRepo(post: Post):String{
        return when{
            post.count_reposts >= 1000000.0 -> "${(post.count_reposts/1000000.0)}M"//1.1
            post.count_reposts >= 10000 -> "${(post.count_reposts/1000)}K" //done
            post.count_reposts >= 1000.0 -> "${(post.count_reposts/1000.0).toBigDecimal().setScale(1, RoundingMode.DOWN).toDouble()}K"//1.1
            else -> post.count_reposts.toString()//done
        }
    }
}