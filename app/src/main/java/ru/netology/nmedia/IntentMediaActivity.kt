package ru.netology.nmedia

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import ru.netology.nmedia.databinding.ActivityIntentMediaBinding
import ru.netology.nmedia.databinding.IntentHandlerActivityBinding

class IntentMediaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)

        val binding = ActivityIntentMediaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val intent = intent ?: return
        if (intent.action != Intent.ACTION_VIEW) return
        if(intent.data == null) return
    }
}