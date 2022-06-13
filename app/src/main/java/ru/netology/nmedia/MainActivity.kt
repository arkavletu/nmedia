package ru.netology.nmedia

import android.os.Bundle
import android.widget.ImageButton
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.databinding.ActivityMainBinding
import java.math.RoundingMode
import kotlin.math.absoluteValue
import kotlin.math.pow

//import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val post = Post(1, "Vova", "Let it crash", "21.04.2022",count_reposts = 1999)

        binding.render(post)

        binding.likes.setOnClickListener {
            post.liked = !post.liked
            binding.likes.setImageResource(getImageRes(post.liked))
            binding.countLikes.text = getLikesCounted(post.liked).toString()
        }
        binding.share.setOnClickListener {
            post.shared = true
            post.count_reposts++
            binding.countReposts.text = spellCounterOfSmth(post.count_reposts)
        }

    }

    private fun ActivityMainBinding.render(post: Post){
        author.text = post.author
        date.text = post.date
        content.text = post.content
        likes.setImageResource(getImageRes(post.liked))
        countLikes.text = getLikesCounted(post.liked).toString()
        countReposts.text = spellCounterOfSmth(post.count_reposts)
    }

    @DrawableRes
    private fun getImageRes(isLiked: Boolean) = if (isLiked) R.drawable.liked_24 else R.drawable.likes_24dp
    private fun getLikesCounted(isLiked: Boolean): Int = if(isLiked) 1 else  0

    private fun spellCounterOfSmth(ammount: Int):String{//упростить, вынести в константы или ресурсы
        val thousand = 1000
        val tenThousand = 10000
        val million = 1000000
        val multiplier = 0.001
        return when{
            ammount >= million -> "${if(ammount % million >= tenThousand*10) 
                    (ammount * multiplier.pow(2.0)).
                    toBigDecimal().setScale(1, RoundingMode.DOWN).toDouble() else 
                    (ammount/million)}M"

            ammount >= tenThousand -> "${(ammount/thousand)}K"

            ammount >= thousand -> "${if(ammount % thousand >= 100) 
                    (ammount * multiplier).
                    toBigDecimal().setScale(1, RoundingMode.DOWN).toDouble()
            else (ammount / thousand)}K"

            else -> ammount.toString()
        }
    }
}