package ru.netology.nmedia

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import ru.netology.nmedia.databinding.ActivityPostContentBinding

class PostContentFragment(
    private val initialContent: String?
) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = ActivityPostContentBinding.inflate(layoutInflater, container, false).also { binding ->
        binding.edit.setText(initialContent)//arguments
        binding.edit.requestFocus()
        binding.edit.showKeyboard()
        binding.ok.setOnClickListener {
            val text = binding.edit.text
            if (!text.isNullOrBlank()) {
                val resultBundle = Bundle(1)
                resultBundle.putString(RESULT_KEY, text.toString())
                setFragmentResult(REQUEST_KEY, resultBundle)
            }
            binding.edit.text.clear()
            binding.edit.hideKeyboard()
            parentFragmentManager.popBackStack()
        }
    }.root



    companion object {
        const val REQUEST_KEY = "postContentRequestKey"
        const val RESULT_KEY = "postNewContent"
    }
}