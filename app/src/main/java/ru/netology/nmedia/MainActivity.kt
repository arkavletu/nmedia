package ru.netology.nmedia

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.databinding.ActivityMainBinding

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



        val adapter = PostsAdapter(viewModel)
        binding.list.adapter = adapter
        viewModel.data.observe(this){posts ->
            adapter.submitList(posts)
        }

        binding.save.setOnClickListener {
            with(binding.content) {
                val content = text.toString()
                viewModel.onSaveClicked(content)
                binding.canselText.text = ""
            }
        }

        binding.canselButton.setOnClickListener {
            with(binding.content){
                clearFocus()
                hideKeyboard()
                binding.group.visibility = View.GONE
                viewModel.onCanselClicked()
                binding.canselText.text = ""
            }
        }
        viewModel.currentPost.observe(this){ currentPost ->
            with(binding.content) {
                val content = currentPost?.content
                setText(content)
                if(content != null) {
                    binding.group.visibility = View.VISIBLE
                    binding.canselText.text = "${currentPost.id}"
                    requestFocus()
                    showKeyboard()
                }
                else {
                    clearFocus()
                    hideKeyboard()
                    binding.group.visibility = View.GONE
                }
            }
        }


    }
}
