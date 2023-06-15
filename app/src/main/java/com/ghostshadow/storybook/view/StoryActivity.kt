package com.ghostshadow.storybook.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.widget.Toast
import com.ghostshadow.storybook.R
import com.ghostshadow.storybook.databinding.ActivityStoryBinding
import com.ghostshadow.storybook.service.constants.Constants
import com.ghostshadow.storybook.service.model.StoryModel
import java.util.Locale

class StoryActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private var binding: ActivityStoryBinding? = null
    private var position = 0
    private lateinit var storyList: ArrayList<StoryModel>
    private var tts: TextToSpeech? = null
    private lateinit var speakableText: String
    private var isPlaying = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStoryBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        position = intent.getIntExtra("position", 0)
        storyList = Constants.getStoryList()
        tts = TextToSpeech(this, this)
        setStoryView()

        setSpeakableText()

        binding?.btnNext?.setOnClickListener {
            if (position < storyList.size - 1) {
                onChangeStory(1)
            } else {
                Toast.makeText(
                    this,
                    "No more sotries available",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        binding?.btnPrevious?.setOnClickListener {
            if (position > 0) {
                onChangeStory(-1)
            } else {
                Toast.makeText(
                    this,
                    "No more sotries available",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        binding?.btnPlay?.setOnClickListener { playStory() }
    }

    override fun onBackPressed() {
        finish()
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts!!.setLanguage(Locale.ENGLISH)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "The Specific lang is not supported!")
            }
        } else {
            Log.e("TTS", "Initialisation failed")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (tts != null) {
            tts?.stop()
            tts?.shutdown()
        }
        binding = null
    }

    private fun setStoryView() {
        val story = storyList[position]
        binding?.storyImage?.setImageResource(story.image2)
        binding?.tvStoryTitle?.setText(story.title)
        binding?.tvStory?.setText(story.story)
        binding?.tvMoral?.setText(story.moral)
    }

    private fun speakOut(text: String) {
        tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }

    private fun setSpeakableText() {
        speakableText = getString(storyList[position].story) + "Moral of the story." +
                getString(storyList[position].moral)
    }

    private fun playStory() {
        if (!isPlaying) {
            isPlaying = true
            speakOut(speakableText)
            binding?.btnPlay?.setImageResource(R.drawable.pause)
        } else {
            tts?.stop()
            isPlaying = false
            binding?.btnPlay?.setImageResource(R.drawable.play)
        }
    }

    private fun onChangeStory(offSet: Int) {
        tts?.stop()
        position += offSet
        isPlaying = false
        setStoryView()
        binding?.btnPlay?.setImageResource(R.drawable.play)
        setSpeakableText()
    }

}