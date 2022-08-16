package ru.netology.nmedia

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.result.launch
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.databinding.ActivityMainBinding
import androidx.activity.viewModels

class MainActivity : AppCompatActivity(){
    val viewModel by viewModels<PostViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        val adapter = PostsAdapter(viewModel)
        binding.list.adapter = adapter
        viewModel.data.observe(this){posts ->
            adapter.submitList(posts)
        }

        viewModel.playVideoEvent.observe(this){video ->
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(video))
            val playIntent = Intent.createChooser(intent,"Choose app")
            startActivity(playIntent)
        }
        viewModel.sharePost.observe(this){postContent ->
            val intent = Intent().apply{
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT,postContent)
                type = "text/plain"
            }
            val shareIntent =
                Intent.createChooser(intent,getString(R.string.chooser_share_post))
            startActivity(shareIntent)
        }

        binding.fab.setOnClickListener {
            viewModel.onFabClicked()
        }


        val postContentResultLauncher = registerForActivityResult(
            PostContentActivity.ResultContract
        ){ postContent ->
            postContent ?: return@registerForActivityResult
            viewModel.onSaveClicked(postContent)

        }
        viewModel.navigateToEditScreenEvent.observe(this){
            postContentResultLauncher.launch()
        }

//        viewModel.currentPost.observe(this){ currentPost ->
//            val bindingPost = ActivityPostContentBinding()
//            with(bindingPost) {
//                val content = currentPost?.content
//                edit.setText(content)
//                if(content != null) {
//                    edit.requestFocus()
//                    edit.showKeyboard()
//                }
//                else {
//                    return@observe
//                }
//            }
//        }


    }
}
