package com.example.broadcastreceiver_service

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import com.example.broadcastreceiver_service.STORAGE_TYPE.INTERNAL
import com.example.broadcastreceiver_service.STORAGE_TYPE.EXTERNAL
import com.example.broadcastreceiver_service.StorageManager.getStorageManager

class MainActivity : AppCompatActivity() {

    private val broadcastReceiver = MyBroadcastReceiver()
    private lateinit var storageManager: StorageManager
    private lateinit var storageType: STORAGE_TYPE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        storageManager = getStorageManager(this@MainActivity)
        storageType = STORAGE_TYPE.valueOf(storageManager.loadStorageType())

        registerReceiver()
        rememberStorageType()
    }

    private fun registerReceiver(){
        val intentFilter = IntentFilter().apply {
            addAction(Intent.ACTION_TIMEZONE_CHANGED)
            addAction(Intent.ACTION_TIME_CHANGED)
            addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED)
        }
        registerReceiver(broadcastReceiver, intentFilter)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(broadcastReceiver)
    }

    private fun rememberStorageType(){
        when{
            radioInternalStorage.isChecked -> storageManager.saveStorageType(INTERNAL)
            radioExternalStorage.isChecked -> storageManager.saveStorageType(EXTERNAL)
        }
    }
}
