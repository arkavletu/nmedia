package ru.netology.nmedia

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ru.netology.nmedia.databinding.FragmentSinglePostBinding

class SinglePostFragment : Fragment() {
    private val args by navArgs<SinglePostFragmentArgs>()

    val viewModel by viewModels<PostViewModel>(
        ownerProducer = ::requireParentFragment
    )
    //val singlePostVM by viewModels<SinglePostViewModel>()


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
            if (requestKey != PostContentFragment.REQUEST_KEY) return@setFragmentResultListener
            val newPostContent = bundle.getString(
                PostContentFragment.RESULT_KEY
            ) ?: return@setFragmentResultListener
            viewModel.onSaveClicked(newPostContent)
        }

        viewModel.navigateToEditScreenEvent.observe(this)
        { initialContent ->
            val direction = SinglePostFragmentDirections.fromSinglePostToPostContentFragment(initialContent)
            findNavController().navigate(direction)
        }

        viewModel.navigateToFirstFragment.observe(this){
            findNavController().popBackStack()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentSinglePostBinding.inflate(layoutInflater, container, false).also{
        val id = args.post
        val adapter = PostsAdapter(viewModel)
        it.included.list.adapter = adapter
        viewModel.data.observe(viewLifecycleOwner) { posts ->
            adapter.submitList(posts.filter { it.id == id })
        }
    }.root

}