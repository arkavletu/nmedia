package ru.netology.nmedia

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.databinding.ActivityMainBinding
import java.math.RoundingMode

class MainActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel by viewModels<PostViewModel>()

        viewModel.data.observe(this){post ->
          binding.render(post)
        }
        binding.likes.setOnClickListener {
            viewModel.likePost()
        }
        binding.share.setOnClickListener{
            viewModel.sharePost()// не работает
        }


    }

    private fun ActivityMainBinding.render(post: Post){
        author.text = post.author
        date.text = post.date
        likes.setImageResource(if(post.liked)R.drawable.liked_24 else R.drawable.likes_24dp)
        countLikes.text = (if(post.liked) 1 else  0).toString()
        countReposts.text = spellCounterOfSmth(post.count_reposts)
    }


    private fun spellCounterOfSmth(sum: Int):String{
        val thousand = 1000.0
        val tenThousand = 10000.0
        val million = 1000000.0
        return when{
            sum >= million -> "${if(sum % million > tenThousand*10) 
                    (sum / million).toBigDecimal().setScale(1, RoundingMode.DOWN)
                    else (sum / million).toInt()}M"
            sum.toDouble() in thousand..million -> "${if(sum < tenThousand && sum % thousand >= 100) 
                    (sum / thousand).toBigDecimal().setScale(1, RoundingMode.DOWN)
                    else (sum / thousand).toInt()}K"
            else -> sum.toString()
        }
    }
}
