package ru.netology.nmedia

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.databinding.ActivityMainBinding
import androidx.activity.viewModels

class MainActivity : AppCompatActivity() {
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
        viewModel.data.observe(this) { posts ->
            adapter.submitList(posts)
        }

        viewModel.playVideoEvent.observe(this) { video ->
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(video))
            val playIntent = Intent.createChooser(intent, "Choose app")
            startActivity(playIntent)
        }
        viewModel.sharePost.observe(this) { postContent ->
            val intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, postContent)
                type = "text/plain"
            }
            val shareIntent =
                Intent.createChooser(intent, getString(R.string.chooser_share_post))
            startActivity(shareIntent)
        }

        binding.fab.setOnClickListener {
            viewModel.onFabClicked()
        }


        val postContentResultLauncher = registerForActivityResult(
            PostContentActivity.ResultContract
        ) { postContent ->
            postContent ?: return@registerForActivityResult
            viewModel.onSaveClicked(postContent)

        }
        viewModel.navigateToEditScreenEvent.observe(this) {
            val content = viewModel.currentPost.value?.content
            postContentResultLauncher.launch(content)
        }


    }


}

