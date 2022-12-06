package ru.netology.nmedia

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ru.netology.nmedia.databinding.PostContentFragmentBinding

class PostContentFragment : Fragment() {

    private val args by navArgs<PostContentFragmentArgs>()
    val viewModel by viewModels<PostViewModel>(
        ownerProducer = ::requireParentFragment
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = PostContentFragmentBinding.inflate(layoutInflater, container, false).also { binding ->
        binding.edit.setText(args.initialContent)//arguments
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
            findNavController().popBackStack()
        }
    }.root



    companion object {
        const val REQUEST_KEY = "postContentRequestKey"
        const val RESULT_KEY = "postNewContent"




    }
}