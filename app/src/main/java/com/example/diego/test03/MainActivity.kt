package com.example.diego.test03

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.TextView
import android.widget.BaseAdapter
import com.example.diego.test03.DataModel.Dispositivo
import com.google.firebase.database.ChildEventListener
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.row_main.view.*
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DataSnapshot
import android.widget.ArrayAdapter



class MainActivity : AppCompatActivity() {


    lateinit var notificationManager: NotificationManager
    lateinit var notificationChannel: NotificationChannel
    lateinit var builder: Notification.Builder
    private val channelId = "com.example.diego.test03"
    private val description = "Test Notification"


//    lateinit var listViewDispositivos: ListView

    //metodo que se ejecuta al iniciar la aplicacion
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val listViewDispositivos = findViewById<ListView>(R.id.listaDispositivos)
        val pinkLightColor = Color.parseColor("#FCE4EC")
        listViewDispositivos.setBackgroundColor(pinkLightColor)
        listViewDispositivos.adapter = MyCustomAdapter(this) // this needs to be my custom adapter telling my list what to render


        //Para abrir AgregarDispositivoActivity
        btnAD.setOnClickListener {
            val intent = Intent(this, AgregarDispositivoActivity::class.java)
            startActivity(intent)
        }

        //val ref = FirebaseDatabase.getInstance().getReference("Dispositivos")
//        var myArrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, nombresList)


        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        btn_notify.setOnClickListener {
            //on click of the notification, we need to launch the application
            val intent = Intent(this, LauncherActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                notificationChannel = NotificationChannel(channelId, description, NotificationManager.IMPORTANCE_HIGH)
                notificationChannel.enableLights(true)
                notificationChannel.lightColor = Color.GREEN
                notificationChannel.enableVibration(false)
                notificationManager.createNotificationChannel(notificationChannel)

                //we shouldn't pass mipmap as icons, so im copying the icons to drawable

                builder = Notification.Builder(this, channelId)
                        .setContentTitle("Casa Segura")
                        .setContentText("Ha ocurrido un corte de energia")
                        .setLargeIcon(BitmapFactory.decodeResource(this.resources, R.drawable.ic_launcher))
                        .setSmallIcon(R.drawable.ic_launcher_round)
                        //.setSmallIcon(R.drawable.notification_template_icon_bg)
                        .setContentIntent(pendingIntent)


            } else {

                builder = Notification.Builder(this)
                        .setContentTitle("Casa Segura")
                        .setContentText("Ha ocurrido un corte de energia")
                        .setLargeIcon(BitmapFactory.decodeResource(this.resources, R.drawable.ic_launcher))
                        .setSmallIcon(R.drawable.ic_launcher_round)
                        //.setSmallIcon(R.drawable.notification_template_icon_bg)
                        .setContentIntent(pendingIntent)
            }


            //use unique id for each notification
            notificationManager.notify(1234, builder.build())

        }


    }


    private class MyCustomAdapter(context: Context) : BaseAdapter() {


        private val mContext: Context

        private val nombresDispositivos = arrayListOf<String>(

                "Cocina", "Pieza 1", "Bodega"
        )

        private val estadoDispositivos = arrayListOf<String>(
                "ON", "OFF", "ON"
        )

        init {
            mContext = context
        }

        // responsible for how many rows in my list
        override fun getCount(): Int {
            return nombresDispositivos.size
        }

        // you can also ignore this
        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        // you can ignore this for now
        override fun getItem(position: Int): Any {
            return "TEST STRING"
        }

        // responsible for rendering out each row
        override fun getView(position: Int, convertView: View?, viewGroup: ViewGroup?): View {

            val rowMain: View

            // checking if convertView is null, meaning we have to inflate a new row
            if (convertView == null) {
                val layoutInflater = LayoutInflater.from(viewGroup!!.context)
                rowMain = layoutInflater.inflate(R.layout.row_main, viewGroup, false)

                //Log.v("getView", "calling findViewById which is expensive")
//                val nTextView = rowMain.name_textView
//                val pTextView = rowMain.position_textview
//                val nameTextView = rowMain.findViewById<TextView>(R.id.name_textView)
//                val positionTextView = rowMain.findViewById<TextView>(R.id.position_textview)
                val viewHolder = ViewHolder(rowMain.NombreDisptextView, rowMain.EstadoDispTextView)
                rowMain.tag = viewHolder

            } else {
                // well, we have our row as convertView, so just set rowMain as that view
                rowMain = convertView
            }

            val viewHolder = rowMain.tag as ViewHolder

            viewHolder.nombreDispositivoTextView.text = nombresDispositivos.get(position)
            viewHolder.estadoDispositivoTextView.text = estadoDispositivos.get(position)

            return rowMain

        }

        private class ViewHolder(val nombreDispositivoTextView: TextView, val estadoDispositivoTextView: TextView)


    }


}




