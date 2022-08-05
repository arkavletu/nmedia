package ru.netology.nmedia

import android.content.Intent
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


    }
}
