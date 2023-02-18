package com.abelmar.tictactoeimposible

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Menu
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MenuUnJugador : AppCompatActivity(){

    var equis: ImageView?=null
    var btnjuga: Button?=null
    var btnFac: Button?=null
    var btnInt: Button?=null
    var btnReloj: Button?=null
     var texto: TextView?=null
    var yo: TextView?=null
    var cpu: TextView?=null
    var btnImp: Button?=null
    var circulo: ImageView?=null
    var nivelJuego: String?=""
    var enviarDatos= arrayListOf<String>("","","no")
    var enviarDatosPartido: IntArray = intArrayOf(0, 0, 0)

    private lateinit var mediaPlayer: MediaPlayer



    var iniciaJugador:String?=""


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.menu_jugador)
        btnjuga=findViewById(R.id.iniciar)
        btnFac=findViewById(R.id.btnFacil)
        btnInt=findViewById(R.id.btnIntermedio)
        btnReloj=findViewById(R.id.btnReloj)
        btnImp=findViewById(R.id.btnImposible)
        equis=findViewById(R.id.x)
        circulo=findViewById(R.id.o)
        texto=findViewById(R.id.textView2)
        yo=findViewById(R.id.yo)
        cpu=findViewById(R.id.cpu)

    }
//U399045

    fun clickFacil(view: View) {
        nivelJuego = "facil"
        verImagenes()
        btnFac?.startAnimation(AnimationUtils.loadAnimation(this, R.anim.scale))
        btnInt?.startAnimation(AnimationUtils.loadAnimation(this, R.anim.scale_finish))
        btnImp?.startAnimation(AnimationUtils.loadAnimation(this, R.anim.scale_finish))

    }

    fun clickIntermedio(view: View){
        nivelJuego="intermedio"
       verImagenes()
        btnInt?.startAnimation(AnimationUtils.loadAnimation(this, R.anim.scale))
        btnFac?.startAnimation(AnimationUtils.loadAnimation(this, R.anim.scale_finish))
        btnImp?.startAnimation(AnimationUtils.loadAnimation(this, R.anim.scale_finish))

    }

    fun clickImposible(view: View){

        nivelJuego="imposible"
        verImagenes()
        btnImp?.startAnimation(AnimationUtils.loadAnimation(this, R.anim.scale))
        btnInt?.startAnimation(AnimationUtils.loadAnimation(this, R.anim.scale_finish))
        btnFac?.startAnimation(AnimationUtils.loadAnimation(this, R.anim.scale_finish))

    }
    fun clickReloj(view: View){

        if(enviarDatos[2]=="no") {
            enviarDatos[2] = "si"
            btnReloj!!.setText("SÍ")
            btnReloj?.startAnimation(AnimationUtils.loadAnimation(this, R.anim.scale))

        }else {
            btnReloj!!.setText("NO")
            btnReloj?.startAnimation(AnimationUtils.loadAnimation(this, R.anim.scale_finish))
            enviarDatos[2] = "no"

        }

    }

    fun verImagenes(){

        btnReloj!!.visibility = View.VISIBLE
        equis!!.visibility = View.VISIBLE
        circulo!!.visibility =View.VISIBLE
        texto!!.visibility =View.VISIBLE
        yo!!.visibility =View.VISIBLE
        cpu!!.visibility =View.VISIBLE


    }

    fun iniciaJugadorX(view: View){
        reproducirSonido()
       iniciaJugador="x"
        btnjuga!!.visibility = View.VISIBLE
        equis?.startAnimation(AnimationUtils.loadAnimation(this, R.anim.scala_juego))
        circulo?.startAnimation(AnimationUtils.loadAnimation(this, R.anim.scale_finish))

    }

    fun iniciaJugadorO(view: View){
        reproducirSonido()
        iniciaJugador="o"
        btnjuga!!.visibility =View.VISIBLE
        circulo?.startAnimation(AnimationUtils.loadAnimation(this, R.anim.scala_juego))
        equis?.startAnimation(AnimationUtils.loadAnimation(this, R.anim.scale_finish))


    }


    fun clickJugar(view: View){

        enviarDatos[0]= nivelJuego.toString()
        enviarDatos[1]= iniciaJugador.toString()


        val intent = Intent(this, MainActivity::class.java)
         intent.putExtra("resultados_juego",enviarDatosPartido)
         intent.putExtra("datos_juego", enviarDatos)
        startActivity(intent)


    }

    fun reproducirSonido(){
        mediaPlayer = MediaPlayer.create(applicationContext,R.raw.fichas)
        mediaPlayer.start()
        Handler(Looper.getMainLooper()).postDelayed({
            mediaPlayer.release()
        }, 150)

    }

    override fun onBackPressed() {
        val intent = Intent(this, MenuJuego::class.java)
        startActivity(intent)
        finishAffinity()
    }


}