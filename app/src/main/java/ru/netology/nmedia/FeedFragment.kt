package ru.netology.nmedia

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import ru.netology.nmedia.databinding.ActivityMainBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels

class FeedFragment : Fragment() {
    val viewModel by viewModels<PostViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)





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

        setFragmentResultListener(PostContentFragment.REQUEST_KEY){requestKey, bundle ->
            if (requestKey != PostContentFragment.REQUEST_KEY) return@setFragmentResultListener// edit here!!!
            val newPostContent = bundle.getString(
                PostContentFragment.RESULT_KEY
            ) ?: return@setFragmentResultListener
            viewModel.onSaveClicked(newPostContent) // arguments вместо extras
        }

        viewModel.navigateToEditScreenEvent.observe(this) {initialContent ->
            //val content = viewModel.currentPost.value?.content
            parentFragmentManager.commit {
                val fragment = PostContentFragment(initialContent)
                replace(R.id.fragment_container, fragment)
                addToBackStack(null)
            }
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = ActivityMainBinding.inflate(layoutInflater,container,false).also{
        val adapter = PostsAdapter(viewModel)
        it.list.adapter = adapter
        viewModel.data.observe(viewLifecycleOwner) { posts ->
            adapter.submitList(posts)
        }
        it.fab.setOnClickListener {
            viewModel.onFabClicked()
        }
    }.root


}

