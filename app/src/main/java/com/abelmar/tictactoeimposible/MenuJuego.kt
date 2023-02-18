package com.abelmar.tictactoeimposible

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MenuJuego : AppCompatActivity() {

    var imageView1: ImageView?=null
    var imageView2: ImageView?=null
    var imageView3: ImageView?=null
    var imageView4: ImageView?=null
    var imageView5: ImageView?=null
    var imageView6: ImageView?=null
    var imageView7: ImageView?=null
    var imageView8: ImageView?=null
    var imageView9: ImageView?=null
    var x:ImageView?=null
    var o:ImageView?=null
    var tiempo:Long=1100
    private lateinit var mediaPlayer: MediaPlayer
    var ciclo=true


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.menu_juego)

        imageView1=findViewById(R.id.imageView11)
        imageView2=findViewById(R.id.imageView22)
        imageView3=findViewById(R.id.imageView33)
        imageView4=findViewById(R.id.imageView44)
        imageView5=findViewById(R.id.imageView55)
        imageView6=findViewById(R.id.imageView66)
        imageView7=findViewById(R.id.imageView77)
        imageView8=findViewById(R.id.imageView88)
        imageView9=findViewById(R.id.imageView99)
        x=findViewById(R.id.x2)
        o=findViewById(R.id.o2)


            animacion()


    }

    fun animacion(){

        imageView1?.setBackgroundResource(R.drawable.o)
        sonido()
        Handler(Looper.getMainLooper()).postDelayed({
            imageView5?.setBackgroundResource(R.drawable.x)
            sonido()
            Handler(Looper.getMainLooper()).postDelayed({
                imageView3?.setBackgroundResource(R.drawable.o)
                sonido()
                Handler(Looper.getMainLooper()).postDelayed({
                    imageView2?.setBackgroundResource(R.drawable.x)
                    sonido()
                    Handler(Looper.getMainLooper()).postDelayed({
                        imageView8?.setBackgroundResource(R.drawable.o)
                        sonido()
                        Handler(Looper.getMainLooper()).postDelayed({
                            imageView4?.setBackgroundResource(R.drawable.x)
                            sonido()
                            Handler(Looper.getMainLooper()).postDelayed({
                                imageView6?.setBackgroundResource(R.drawable.o)
                                sonido()
                                Handler(Looper.getMainLooper()).postDelayed({
                                    imageView9?.setBackgroundResource(R.drawable.x)
                                    sonido()
                                    Handler(Looper.getMainLooper()).postDelayed({
                                        imageView7?.setBackgroundResource(R.drawable.o)
                                        sonido()
                                        Handler(Looper.getMainLooper()).postDelayed({
                                            limpiar()
                                            Handler(Looper.getMainLooper()).postDelayed({
                                                if(ciclo){animacion()}
                                                   }, 500)
                                               }, tiempo)
                                             }, tiempo)
                                        }, tiempo)
                                    }, tiempo)
                                }, tiempo)
                            }, tiempo)
                        }, tiempo)
                    }, tiempo)
                }, tiempo)

    }

    fun limpiar(){
        imageView1?.setBackgroundResource(0);
        imageView2?.setBackgroundResource(0);
        imageView3?.setBackgroundResource(0);
        imageView4?.setBackgroundResource(0);
        imageView5?.setBackgroundResource(0);
        imageView6?.setBackgroundResource(0);
        imageView7?.setBackgroundResource(0);
        imageView8?.setBackgroundResource(0);
        imageView9?.setBackgroundResource(0);



    }

    fun sonido(){
        if(ciclo){
            mediaPlayer = MediaPlayer.create(applicationContext,R.raw.fichas)
            mediaPlayer.start()
            Handler(Looper.getMainLooper()).postDelayed({
                mediaPlayer.release()
            }, 150)
        }

    }


    fun clickMultiplayer(view: View){

        val intent = Intent(this, MainActivity::class.java)
        var enviarDato= arrayListOf<String>("libre","x","")
        var enviarDatosPartido: IntArray = intArrayOf(0, 0, 0)
        intent.putExtra("resultados_juego",enviarDatosPartido)
        intent.putExtra("datos_juego", enviarDato)
        ciclo=false
        tiempo=0
        startActivity(intent)
    }

    override fun onBackPressed() {
        finishAffinity()
        onDestroy()
    }



    fun clickJugador(view: View){

        val intent = Intent(this, MenuUnJugador::class.java)
        ciclo=false
        tiempo=0
        startActivity(intent)
        finish()



    }
}