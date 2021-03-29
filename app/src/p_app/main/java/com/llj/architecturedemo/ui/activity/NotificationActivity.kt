package com.llj.architecturedemo.ui.activity

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.StrictMode
import android.support.v4.media.session.MediaSessionCompat
import android.view.View
import android.widget.RemoteViews
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.Person
import com.alibaba.android.arouter.facade.annotation.Route
import com.llj.application.router.CRouter
import com.llj.architecturedemo.MainMvcBaseActivity
import com.llj.architecturedemo.R
import com.llj.architecturedemo.databinding.ActivityNotificationBinding
import com.llj.lib.utils.ANotificationUtils
import java.net.URL
import java.util.*


@Route(path = CRouter.APP_NOTIFICATION_ACTIVITY)
class NotificationActivity : MainMvcBaseActivity<ActivityNotificationBinding>() {

  private val mMap = hashMapOf(
      "活动通知1" to "test_channel_id1",
      "活动通知2" to "test_channel_id2",
      "活动通知3" to "test_channel_id3",
      "活动通知4" to "test_channel_id4",
      "活动通知5" to "test_channel_id5",
      "活动通知8" to "test_channel_id8",
  )

  override fun initViews(savedInstanceState: Bundle?) {

    val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

    val value = View.OnClickListener { v ->
      val title = "标题标题标题标题标题标题标题标题标题标题标题标题标题标题标题标题标题标题"
      val content = "内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容"
      val summaryText = "setSummaryText大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大"
      val bigContentTitle = "setBigContentTitle大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大"
      val bigText = "bigText这里是显示一个大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大大文本"

      val smallIcon = R.drawable.def_user_header
      val largeIconUrl = "https://images.pexels.com/photos/139829/pexels-photo-139829.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=150&w=440"
      val bigPictureUrl = "https://images.pexels.com/photos/1058683/pexels-photo-1058683.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940"

      val policy: StrictMode.ThreadPolicy = StrictMode.ThreadPolicy.Builder().permitNetwork().build()
      StrictMode.setThreadPolicy(policy)
      val largeIcon = BitmapFactory.decodeStream(URL(largeIconUrl).openConnection().getInputStream())
      val bigPicture = BitmapFactory.decodeStream(URL(bigPictureUrl).openConnection().getInputStream())

      val intent = PendingIntent.getActivity(this, 0, Intent(this, MvvmRequestActivity::class.java), PendingIntent.FLAG_UPDATE_CURRENT)

      val customContentView = RemoteViews(packageName, R.layout.item_notification_custom)
      customContentView.setTextViewText(R.id.tv_title, "customContentView" + title)

      val customBigContentView = RemoteViews(packageName, R.layout.item_notification_custom_big)
      customBigContentView.setTextViewText(R.id.tv_title, "customBigContentView" + title)
      customBigContentView.setTextViewText(R.id.tv_content, "customBigContentView" + content)
      customBigContentView.setImageViewBitmap(R.id.iv_large_icon, bigPicture)
      customBigContentView.setOnClickPendingIntent(R.id.tv_confirm, intent)
      customBigContentView.setOnClickPendingIntent(R.id.tv_cancel, intent)

      when (v.id) {
        R.id.tv_check_notification -> {
          //检测通知总开关
          if (ANotificationUtils.isNotificationEnabled(this)) {
            showToast("通知已经打开")
          } else {
            ANotificationUtils.jumpSetting(this, null)
          }
        }
        R.id.tv_check_notification_channel -> {
          //检测通知渠道开关
          if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (ANotificationUtils.isNotificationsChannelEnabled(this, "test_channel_id1")) {
              showToast("通知渠道已经打开")
            } else {
              ANotificationUtils.jumpSetting(this, "test_channel_id1")
            }
          }
        }

        R.id.tv_small_icon -> {
          //普通文本小icon
          val channelId = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            channelId(notificationManager, "活动通知1")
          } else {
            null
          }
          val notification = getNotification(smallIcon, title, content, channelId, intent).build()
          notificationManager.notify(generateNotificationId(), notification)
        }
        R.id.tv_large_icon -> {
          //普通文本大icon
          val channelId = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            channelId(notificationManager, "活动通知2")
          } else {
            null
          }

          val notification = getNotification(smallIcon, title, content, channelId, intent, largeIcon).build()
          notificationManager.notify(generateNotificationId(), notification)
        }
        R.id.tv_custom_content_view -> {
          //setCustomContentView
          val channelId = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            channelId(notificationManager, "活动通知3")
          } else {
            null
          }

          val notification = getNotification(smallIcon, title, content, channelId, intent, largeIcon, null, customContentView).build()
          notificationManager.notify(generateNotificationId(), notification)
        }

        R.id.tv_custom_content_view2 -> {
          //setCustomContentView
          val channelId = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            channelId(notificationManager, "活动通知3")
          } else {
            null
          }

          val notification = getNotification(smallIcon, title, content, channelId, intent, largeIcon, null, customBigContentView).build()
          notificationManager.notify(generateNotificationId(), notification)
        }
        R.id.tv_custom_big_content_view -> {
          //setCustomBigContentView
          val channelId = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            channelId(notificationManager, "活动通知3")
          } else {
            null
          }
          val notification = getNotification(smallIcon, title, content, channelId, intent, largeIcon, null, null, customContentView)
              .build()
          notificationManager.notify(generateNotificationId(), notification)
        }

        R.id.tv_custom_big_content_view2 -> {
          //setCustomBigContentView
          val channelId = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            channelId(notificationManager, "活动通知3")
          } else {
            null
          }
          val notification = getNotification(smallIcon, title, content, channelId, intent, largeIcon, null, null, customBigContentView)
              .build()
          notificationManager.notify(generateNotificationId(), notification)
        }

        R.id.tv_big_text_style -> {
          //BigTextStyle
          val channelId = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            channelId(notificationManager, "活动通知3")
          } else {
            null
          }
          val style = NotificationCompat.BigTextStyle()
              .setSummaryText(summaryText)
              .setBigContentTitle(bigContentTitle)
              .bigText(bigText)

          val notification = getNotification(smallIcon, title, content, channelId, intent, largeIcon, style).build()
          notificationManager.notify(generateNotificationId(), notification)
        }
        R.id.tv_big_picture_style -> {
          //BigPictureStyle
          val channelId = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            channelId(notificationManager, "活动通知4")
          } else {
            null
          }
          val style = NotificationCompat.BigPictureStyle()
              .bigPicture(bigPicture)
              .setSummaryText(summaryText)
              .setBigContentTitle(bigContentTitle)
              .bigLargeIcon(largeIcon)

          val notification = getNotification(smallIcon, title, content, channelId, intent, largeIcon, style).build()
          notificationManager.notify(generateNotificationId(), notification)
        }
        R.id.tv_inbox_style -> {
          //InboxStyle
          val channelId = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            channelId(notificationManager, "活动通知4")
          } else {
            null
          }
          val style = NotificationCompat.InboxStyle()
              .setSummaryText("setSummaryText")
              .setBigContentTitle("setBigContentTitle")
              .addLine("line1")
              .addLine("line2")
              .addLine("line3")
              .addLine("line4")

          val notification = getNotification(smallIcon, title, content, channelId, intent, largeIcon, style).build()
          notificationManager.notify(generateNotificationId(), notification)
        }

        R.id.tv_media_style -> {
          val channelId = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            channelId(notificationManager, "活动通知4")
          } else {
            null
          }
          val style = androidx.media.app.NotificationCompat.MediaStyle()
              .setMediaSession(MediaSessionCompat(this, "MediaSession").sessionToken)

          val notification = getNotification(smallIcon, title, content, channelId, null, largeIcon, style)
              .addAction(android.R.drawable.ic_media_previous, "Previous", intent) // #0
              .addAction(android.R.drawable.ic_media_play, "Pause", intent)  // #1
              .addAction(android.R.drawable.ic_media_next, "Next", intent)     // #2
              .build()
          notificationManager.notify(generateNotificationId(), notification)
        }
        R.id.tv_head_up -> {
        }
        R.id.tv_message_style -> {
          //MessagingStyle
          val channelId = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            channelId(notificationManager, "活动通知4")
          } else {
            null
          }

          val person1 = Person.Builder().setName("赵某某").build()
          val person2 = Person.Builder().setName("钱某某").build()
          val person3 = Person.Builder().setName("孙某某").build()
          val style = NotificationCompat.MessagingStyle(person1)
              .addMessage("你在家吗？", System.currentTimeMillis(), person2)
              .addMessage("刚Zhang san是不是找你了？", System.currentTimeMillis(), person3)
              .addMessage("晚饭吃什么？", System.currentTimeMillis(), person3)
              .addMessage("一天都没有回我信息了，你在忙什么哪？", System.currentTimeMillis(), person3)

          val notification = getNotification(smallIcon, title, content, channelId, intent, largeIcon, style).build()
          notificationManager.notify(generateNotificationId(), notification)
        }
        R.id.tv_add_remote_input -> {
          //MessagingStyle
          val channelId = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            channelId(notificationManager, "活动通知4")
          } else {
            null
          }

          val person1 = Person.Builder().setName("赵某某").build()
          val person2 = Person.Builder().setName("钱某某").build()
          val person3 = Person.Builder().setName("孙某某").build()
          val bigPictureStyle = NotificationCompat.MessagingStyle(person1)
              .addMessage("Message2", System.currentTimeMillis(), person2)
              .addMessage("Message3", System.currentTimeMillis(), person3)

          val notification = getNotification(smallIcon, title, content, channelId, intent, largeIcon, bigPictureStyle)
              .addAction(NotificationCompat.Action.Builder(android.R.drawable.ic_media_play, "回复消息", intent)
                  .addRemoteInput(androidx.core.app.RemoteInput.Builder("key_text_reply")
                      .setChoices(arrayOf("吃饭呢", "睡觉呢"))
                      .build())
                  .build())
              .build()
          notificationManager.notify(generateNotificationId(), notification)
        }

        R.id.tv_decorated_custom_view_style -> {
          //DecoratedCustomViewStyle
          val channelId = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            channelId(notificationManager, "活动通知8")
          } else {
            null
          }

          val style = NotificationCompat.DecoratedCustomViewStyle()

          val notification = getNotification(smallIcon, null, null, channelId, null, largeIcon, style, customBigContentView).build()
          notificationManager.notify(generateNotificationId(), notification)

        }
        R.id.tv_decorated_media_custom_view_style -> {
          //DecoratedMediaCustomViewStyle
          val channelId = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            channelId(notificationManager, "活动通知8")
          } else {
            null
          }

          val style = androidx.media.app.NotificationCompat.DecoratedMediaCustomViewStyle()


          val notification = getNotification(smallIcon, null, null, channelId, null, largeIcon, style, customBigContentView).build()
          notificationManager.notify(8, notification)

        }

        R.id.textView100 -> {
          //DecoratedCustomViewStyle
          val channelId = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            channelId(notificationManager, "活动通知4")
          } else {
            null
          }


          val customNotification = NotificationCompat.Builder(this, channelId!!)
              .setSmallIcon(smallIcon)
              .setStyle(NotificationCompat.DecoratedCustomViewStyle())
              .setCustomBigContentView(customBigContentView)
              .build()
          notificationManager.notify(generateNotificationId(), customNotification)
        }
      }
    }
    mViewBinder.tvCheckNotification.setOnClickListener(value)
    mViewBinder.tvCheckNotificationChannel.setOnClickListener(value)
    mViewBinder.tvSmallIcon.setOnClickListener(value)
    mViewBinder.tvLargeIcon.setOnClickListener(value)
    mViewBinder.tvCustomContentView.setOnClickListener(value)
    mViewBinder.tvCustomContentView2.setOnClickListener(value)
    mViewBinder.tvCustomBigContentView.setOnClickListener(value)
    mViewBinder.tvCustomBigContentView2.setOnClickListener(value)
    mViewBinder.tvBigTextStyle.setOnClickListener(value)
    mViewBinder.tvBigPictureStyle.setOnClickListener(value)
    mViewBinder.tvInboxStyle.setOnClickListener(value)
    mViewBinder.tvMediaStyle.setOnClickListener(value)
    mViewBinder.tvHeadUp.setOnClickListener(value)
    mViewBinder.tvMessageStyle.setOnClickListener(value)
    mViewBinder.tvAddRemoteInput.setOnClickListener(value)
    mViewBinder.tvDecoratedCustomViewStyle.setOnClickListener(value)
    mViewBinder.tvDecoratedMediaCustomViewStyle.setOnClickListener(value)
    mViewBinder.textView100.setOnClickListener(value)


  }

  override fun initData() {
  }

  @RequiresApi(api = Build.VERSION_CODES.O)
  private fun channelId(notificationManager: NotificationManager, name: String): String? {
    val channelId = mMap[name]
    val notificationChannel = NotificationChannel(channelId, name,
        NotificationManager.IMPORTANCE_HIGH)
    notificationChannel.enableLights(true) //闪光
    notificationChannel.setLockscreenVisibility(NotificationCompat.VISIBILITY_SECRET) //锁屏显示通知
    notificationChannel.enableVibration(true) //是否允许震动
    //这里并非多此一举，channel设置了振动只是为了8.0以上的手机，低版本的振动只能在这里设置
    notificationChannel.setVibrationPattern(longArrayOf(100, 100, 200)) //设置震动方式（事件长短）
    notificationChannel.setBypassDnd(true)//设置绕过勿扰模式
    notificationChannel.setDescription("重要活动通知：千万福利,助力终端")//设置渠道通知类型
    notificationChannel.setLightColor(Color.GREEN) //制定闪灯是灯光颜色
    notificationChannel.setShowBadge(true)//设置启动图标右上角显示标记
    notificationManager.createNotificationChannel(notificationChannel)
    return channelId
  }

  private fun getNotification(
      smallIcon: Int, title: String?, content: String?,
      channelId: String?, pendingIntent: PendingIntent?,
      largeIcon: Bitmap? = null,
      style: NotificationCompat.Style? = null,
      customContentView: RemoteViews? = null,
      customBigContentView: RemoteViews? = null,
  ): NotificationCompat.Builder {
    val builder: NotificationCompat.Builder = if (channelId == null) NotificationCompat.Builder(applicationContext)
    else NotificationCompat.Builder(applicationContext, channelId)

    builder.setSmallIcon(smallIcon)
    builder.setLargeIcon(largeIcon)
    builder.setContentTitle(title)
    builder.setContentText(content)
    builder.setStyle(style)
    builder.setWhen(System.currentTimeMillis())
    builder.setShowWhen(true)
    builder.setTicker("New Message")
    builder.setNumber(5)

    builder.setOngoing(false)
    builder.setAutoCancel(true)
    builder.setPriority(NotificationCompat.PRIORITY_MAX)
    builder.setVisibility(NotificationCompat.VISIBILITY_SECRET)
    builder.setDefaults(NotificationCompat.DEFAULT_ALL)

    builder.setContentIntent(pendingIntent)
    builder.setFullScreenIntent(pendingIntent, true)
    builder.setCustomContentView(customContentView)
    builder.setCustomBigContentView(customBigContentView)
    return builder
  }

  var canCreateDuplicateNotification = true

  private fun generateNotificationId(): Int {
    return if (canCreateDuplicateNotification) {
      Random().nextInt()
    } else {
      1
    }
  }


}