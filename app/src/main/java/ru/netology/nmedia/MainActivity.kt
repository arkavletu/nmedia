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


        val adapter = PostsAdapter(viewModel)
        binding.list.adapter = adapter
        viewModel.data.observe(this){posts ->
            adapter.submitList(posts)
        }

        binding.save.setOnClickListener {
            with(binding.content) {
                val content = text.toString()
                viewModel.onSaveClicked(content)
//                clearFocus()
//                hideKeyboard()

            }
        }
        viewModel.currentPost.observe(this){ currentPost ->
            with(binding.content) {
                val content = currentPost?.content
                setText(content)
                if(content != null) {
                    requestFocus()
                    showKeyboard()
                }
                else {
                    clearFocus()
                    hideKeyboard()
                }
            }
        }
//SingleLifeEvent for scroll
    }
}
