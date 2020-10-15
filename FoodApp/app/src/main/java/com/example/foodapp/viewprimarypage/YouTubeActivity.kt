package com.example.foodapp.viewprimarypage

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.example.foodapp.R
import kotlinx.android.synthetic.main.activity_area_list.*
import kotlinx.android.synthetic.main.activity_you_tube.*

const val KEY_EXTRA_URL_TO_VIDEO = "KEY_EXTRA_URL_TO_VIDEO"

class YouTubeActivity : AppCompatActivity(){

    private lateinit var urlToVideo: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_you_tube)

        setSupportActionBar(toolbarYouTubeActivity)
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        urlToVideo = getURLtoVideo()
        loadYouTubeFragment(urlToVideo)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.navigateToFavoriteDishesActivity -> startFavoriteDishesActivity()
            android.R.id.home ->  finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun startFavoriteDishesActivity(){
        val instance = FavoriteDishesActivity.newInstance()
        val intent = instance.newIntent(this)
        startActivity(intent)
    }

    private fun loadYouTubeFragment(urlToVideo: String){
        supportFragmentManager.beginTransaction()
            .add(R.id.fragmentContainerYouTube, YouTubeFragment.newInstance(urlToVideo), YouTubeFragment.TAG)
            .commit()
    }

    private fun getURLtoVideo(): String{
        val intent = intent
        return intent.getStringExtra(KEY_EXTRA_URL_TO_VIDEO) as String
    }

    companion object {
        fun newInstance() = YouTubeActivity()
    }

    fun newIntent(context: Context): Intent {
        return Intent(context, YouTubeActivity::class.java)
    }
}