package com.nafisa008.nafisa008recipe.worker   // ✅ FIXED PACKAGE NAME

import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.nafisa008.nafisa008recipe.R

class ReminderWorker(
    context: Context,
    workerParams: WorkerParameters
) : Worker(context, workerParams) {

    override fun doWork(): Result {

        val notification = NotificationCompat.Builder(applicationContext, "recipe_channel")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Recipe Reminder")
            .setContentText("Don’t forget to add a new recipe today!")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        val manager = NotificationManagerCompat.from(applicationContext)

        if (manager.areNotificationsEnabled()) {
            manager.notify(1001, notification)
        }

        return Result.success()
    }
}
