package com.example.diego.test03

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    var database = FirebaseDatabase.getInstance()
    var myRef = database.reference


    lateinit var notificationManager : NotificationManager
    lateinit var notificationChannel : NotificationChannel
    lateinit var builder : Notification.Builder
    private val channelId = "com.example.diego.test03"
    private val description =  "Test Notification"

    //metodo que se ejecuta al iniciar la aplicacion
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Para abrir AgregarDispositivoActivity
        btnAD.setOnClickListener {
            val intent = Intent(this, AgregarDispositivoActivity::class.java)
            startActivity(intent)
        }

        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        btn_notify.setOnClickListener {
            //on click of the notification, we need to launch the application
            val intent = Intent(this, LauncherActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                notificationChannel = NotificationChannel(channelId,description,NotificationManager.IMPORTANCE_HIGH)
                notificationChannel.enableLights(true)
                notificationChannel.lightColor = Color.GREEN
                notificationChannel.enableVibration(false)
                notificationManager.createNotificationChannel(notificationChannel)

                //we shouldn't pass mipmap as icons, so im copying the icons to drawable

                builder = Notification.Builder(this, channelId)
                        .setContentTitle("Casa Segura")
                        .setContentText("Ha ocurrido un corte de energia")
                        .setLargeIcon(BitmapFactory.decodeResource(this.resources,R.drawable.ic_launcher))
                        .setSmallIcon(R.drawable.ic_launcher_round)
                        //.setSmallIcon(R.drawable.notification_template_icon_bg)
                        .setContentIntent(pendingIntent)


            }else{

                builder = Notification.Builder(this)
                        .setContentTitle("Casa Segura")
                        .setContentText("Ha ocurrido un corte de energia")
                        .setLargeIcon(BitmapFactory.decodeResource(this.resources,R.drawable.ic_launcher))
                        .setSmallIcon(R.drawable.ic_launcher_round)
                        //.setSmallIcon(R.drawable.notification_template_icon_bg)
                        .setContentIntent(pendingIntent)
            }



            //use unique id for each notification
            notificationManager.notify(1234,builder.build())

        }



    }

}
