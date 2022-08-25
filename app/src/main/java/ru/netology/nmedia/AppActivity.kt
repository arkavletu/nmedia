package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.commit
import ru.netology.nmedia.databinding.ActivityAppBinding

class AppActivity : AppCompatActivity(R.layout.activity_app)
//    {override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        val binding = ActivityAppBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//        if (supportFragmentManager.findFragmentByTag(FeedFragment.TAG) == null){
//            supportFragmentManager.commit{
//                add(R.id.fragment_container,FeedFragment(),FeedFragment.TAG)
//            }
//        }
//
//
//
//
//
//
//
//
//
//
//
//    }
