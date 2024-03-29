package ru.netology.nmedia

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.databinding.FeedFragmentBinding

class FeedFragment : Fragment() {
    val viewModel by viewModels<PostViewModel>(
        ownerProducer = ::requireParentFragment
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.playVideoEvent.observe(this)
        { video ->
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(video))
            val playIntent = Intent.createChooser(intent, "Choose app")
            startActivity(playIntent)
        }
        viewModel.sharePost.observe(this)
        { postContent ->
            val intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, postContent)
                type = "text/plain"
            }
            val shareIntent =
                Intent.createChooser(intent, getString(R.string.chooser_share_post))
            startActivity(shareIntent)
        }

        setFragmentResultListener(PostContentFragment.REQUEST_KEY)
        { requestKey, bundle ->
            if (requestKey != PostContentFragment.REQUEST_KEY) return@setFragmentResultListener// edit here!!!
            val newPostContent = bundle.getString(
                PostContentFragment.RESULT_KEY
            ) ?: return@setFragmentResultListener
            viewModel.onSaveClicked(newPostContent)
        }

        viewModel.navigateToEditScreenEvent.observe(this)
        { initialContent ->
            val direction = FeedFragmentDirections.toPostContentFragment(initialContent)
            findNavController().navigate(direction)

        }

        viewModel.navigateToPostFragment.observe(this)
        { id ->
            val direction = FeedFragmentDirections.toSinglePostFragment(id)
            findNavController().navigate(direction)

        }


    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FeedFragmentBinding.inflate(layoutInflater, container, false).also {
        val adapter = PostsAdapter(viewModel)
        it.includedList.list.adapter = adapter
        viewModel.data.observe(viewLifecycleOwner) { posts ->
            adapter.submitList(posts)
        }
        it.fab.setOnClickListener {
            viewModel.onFabClicked()
        }


    }.root


    companion object {
        const val TAG = "feedFragment"
    }
}





