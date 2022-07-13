package ru.netology.nmedia

import android.os.Bundle
import android.view.View
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

        binding.canselButton.setOnClickListener {
            with(binding.content){
                clearFocus()
                hideKeyboard()
                binding.group.visibility = View.GONE
                viewModel.onCanselClicked()
                binding.canselText.text.clear()//check стереть все
            }
        }
        viewModel.currentPost.observe(this){ currentPost ->
            with(binding.content) {
                val content = currentPost?.content
                setText(content)
                if(content != null) {
                    binding.group.visibility = View.VISIBLE
                    binding.canselText.text.append("Change post ${currentPost.id}")//верхнее поле должно быть непрозрачным
                    requestFocus()
                    showKeyboard()
                }
                else {
                    clearFocus()
                    hideKeyboard()
                    binding.group.visibility = View.GONE//но 1 элемент мне нужен
                }
            }
        }
//SingleLifeEvent for scroll
    }
}
