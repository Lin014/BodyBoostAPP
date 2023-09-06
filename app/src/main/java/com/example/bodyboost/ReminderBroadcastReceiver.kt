package com.example.bodyboost

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

object ReminderScheduler {

    const val REMINDER_ACTION = "com.example.bodyboost.REMINDER_ACTION"

    fun scheduleReminder(context: Context, requestCode: Int, triggerTimeMillis: Long) {
        val intent = Intent(REMINDER_ACTION)
        // 可以添加額外的資料到 intent，以便在 BroadcastReceiver 中處理
        // intent.putExtra("key", "value")

        val pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        // 設置提醒，這個示例中使用的是重複提醒
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            triggerTimeMillis,
            AlarmManager.INTERVAL_DAY * 7, // 每週觸發一次
            pendingIntent
        )
    }

    fun cancelReminder(context: Context, requestCode: Int) {
        val intent = Intent(REMINDER_ACTION)
        val pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent,
            PendingIntent.FLAG_CANCEL_CURRENT or PendingIntent.FLAG_IMMUTABLE)
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        // 取消提醒
        alarmManager.cancel(pendingIntent)
    }
}

class ReminderBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        // 在此處處理提醒的操作
        if (intent?.action == ReminderScheduler.REMINDER_ACTION) {
            // 在此處顯示提醒通知或執行其他操作
        }
    }
}