package ru.netology.nmedia

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.databinding.PostBinding
import java.math.RoundingMode

class MainActivity : AppCompatActivity(){
    val viewModel by viewModels<PostViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val adapter = PostsAdapter(viewModel::likePost)
        binding.postRecyclerView.adapter = adapter
        viewModel.data.observe(this){posts ->
            adapter.submitList(posts)
        }

//        binding.share.setOnClickListener{
//            viewModel.sharePost()
//        }


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
