package com.example.broadcastreceiver_service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import java.text.SimpleDateFormat
import java.util.*

const val ACTION_KEY = "ACTION_KEY"
const val DATE_KEY = "DATE_KEY"

class MyBroadcastReceiver : BroadcastReceiver(){

    override fun onReceive(context: Context, intent: Intent) {
        val action = intent.action
        val date = getActionDate(Date())

        when(action){
            Intent.ACTION_TIMEZONE_CHANGED,
            Intent.ACTION_TIME_CHANGED,
            Intent.ACTION_AIRPLANE_MODE_CHANGED -> {
                startMyService(context, date, action)
            }
        }
    }

    private fun getActionDate(date: Date): String{
        val simpleDateFormat = SimpleDateFormat("yyyy/MM/dd_KK:mm")
        return simpleDateFormat.format(date)
    }

    private fun startMyService(context: Context, date: String, action: String){
        val intentService = Intent(context, MyService::class.java)
        intentService.putExtra(ACTION_KEY, action)
        intentService.putExtra(DATE_KEY, date)
        context.startService(intentService)
    }
}