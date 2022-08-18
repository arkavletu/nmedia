package ru.netology.nmedia

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.viewModels
import ru.netology.nmedia.databinding.ActivityPostContentBinding

class PostContentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityPostContentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.edit.setText(intent.getStringExtra("postContent"))
        binding.edit.requestFocus()
        binding.edit.showKeyboard()
        binding.ok.setOnClickListener {

            val intent = Intent()
            val text = binding.edit.text
            if(text.isNullOrBlank()){
                setResult(Activity.RESULT_CANCELED,intent)
            } else {
                val content = text.toString()
                intent.putExtra(RESULT_KEY,content)
                setResult(Activity.RESULT_OK,intent)

            }
            binding.edit.text.clear()
            binding.edit.hideKeyboard()
            finish()
        }
    }

    object ResultContract: ActivityResultContract<String?,String?>(){
        override fun createIntent(context: Context, input: String?): Intent {
        val intent = Intent(context,PostContentActivity::class.java)
            intent.putExtra("postContent",input)
            return intent
        }


        override fun parseResult(resultCode: Int, intent: Intent?): String? =
            if(resultCode == Activity.RESULT_OK){
                intent?.getStringExtra(RESULT_KEY)
            } else null

    }

    private companion object {
        private const val RESULT_KEY = "postNewContent"
    }
}