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

       // val viewModel by viewModels<PostViewModel>()

        viewModel.data.observe(this){post ->
          binding.render(post)
        }
        binding.likes.setOnClickListener {
            viewModel.likePost()
        }
        binding.share.setOnClickListener{
            viewModel.sharePost()
        }


    }

    private fun ActivityMainBinding.render(post: Post){
        author.text = post.author
        date.text = post.date
        likes.setImageResource(if(post.liked)R.drawable.liked_24 else R.drawable.likes_24dp)
        countLikes.text = spellCounterOfSmth(post.countLikes)
        countReposts.text = spellCounterOfSmth(post.countReposts)
    }


        val adapter = PostsAdapter(viewModel::likePost, viewModel::sharePost)
        binding.postRecyclerView.adapter = adapter
        viewModel.data.observe(this){posts ->
            adapter.submitList(posts)
        }
    }
}
