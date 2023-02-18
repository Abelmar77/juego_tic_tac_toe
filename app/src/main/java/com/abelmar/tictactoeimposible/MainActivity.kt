package com.abelmar.tictactoeimposible


import android.app.AlertDialog
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {


    var imageView1: ImageView? = null
    var imageView2: ImageView? = null
    var imageView3: ImageView? = null
    var imageView4: ImageView? = null
    var imageView5: ImageView? = null
    var imageView6: ImageView? = null
    var imageView7: ImageView? = null
    var imageView8: ImageView? = null
    var imageView9: ImageView? = null
    var textContador: TextView? = null
    var textGanX: TextView? = null
    var textEmpat: TextView? = null
    var textGan0: TextView? = null

    var numeroTurno = 1
    var x: ImageView? = null
    var o: ImageView? = null
    var recibido = arrayListOf<String>("", "", "")
    var recibidoMarcador = intArrayOf(0, 0, 0)
    var arregloJuego = intArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0)
    var matrizJuego = arrayOf(
        intArrayOf(0, 0, 0),
        intArrayOf(0, 0, 0),
        intArrayOf(0, 0, 0)
    )
    var turno = ""
    var nombreClick = ""
    var fichaColocada = false
    var tiempoRespuesta: Long = 0
    var tiempoOut: Long = 3000
    var ganadorFinal = ""
    var cancelar = false
    private lateinit var mediaPlayer: MediaPlayer


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        imageView1 = findViewById(R.id.imageView1)
        imageView2 = findViewById(R.id.imageView2)
        imageView3 = findViewById(R.id.imageView3)
        imageView4 = findViewById(R.id.imageView4)
        imageView5 = findViewById(R.id.imageView5)
        imageView6 = findViewById(R.id.imageView6)
        imageView7 = findViewById(R.id.imageView7)
        imageView8 = findViewById(R.id.imageView8)
        imageView9 = findViewById(R.id.imageView9)

        textEmpat = findViewById(R.id.empate)
        textGan0 = findViewById(R.id.contador0)
        textGanX = findViewById(R.id.contadorx)
        textContador = findViewById(R.id.txtContador)
        x = findViewById(R.id.x)
        o = findViewById(R.id.o)

        recibirDatos()
        turno = recibido[1]

        if (recibido[1] == "x") {
            x?.startAnimation(AnimationUtils.loadAnimation(this, R.anim.scala_juego))
        }
        if (recibido[1] == "o") {
            o?.startAnimation(AnimationUtils.loadAnimation(this, R.anim.scala_juego))
            colocaAlAzar()
        }

        textGan0?.text = recibidoMarcador[0].toString()
        textEmpat?.text = recibidoMarcador[1].toString()
        textGanX?.text = recibidoMarcador[2].toString()


        if (recibido[0] == "intermedio") {
            tiempoRespuesta = 300
            tiempoOut = 7000
        }
        if (recibido[0] == "facil") {
            tiempoRespuesta = 500
            tiempoOut = 11000
        }

        if (recibido[2] == "si") {

            conometro()
        }


    }

    fun clickImagen(view: View) {

        reproducirSonido()
        var numbers = intArrayOf(1, 2).randomOrNull()!!


        if (recibido[0] == "imposible" && recibido[1] == "x") {
            pintaJugadaVs(view)
            guardarJugadaVs(view)
            animacionSalidaX()
            if (!verificarGameover()) {
                turnocpu()
            }
            numeroTurno++

        }
        if (recibido[0] == "imposible" && recibido[1] == "o") {

            pintaJugadaVs(view)
            guardarJugadaVs(view)
            animacionSalidaX()
            if (numeroTurno == 1) {

                juegoInteligente()
            } else {

                if (!verificarGameover()) {
                    turnocpu()
                }
            }
            numeroTurno++

        }
        if (recibido[0] == "intermedio" && recibido[1] == "x") {
            pintaJugadaVs(view)
            guardarJugadaVs(view)
            animacionSalidaX()
            if (numeroTurno == 1) {
                if (numbers == 1) {
                    turnocpu()
                } else {
                    colocaAlAzar()
                }
            } else {
                if (!verificarGameover()) {
                    turnocpu()
                }
            }

            numeroTurno++
        }
        if (recibido[0] == "intermedio" && recibido[1] == "o") {

            pintaJugadaVs(view)
            guardarJugadaVs(view)
            animacionSalidaX()
            if (numeroTurno == 1) {

                if (numbers == 1) {
                    juegoInteligente()
                } else {
                    colocaAlAzar()
                }
            } else {
                if (!verificarGameover()) {
                    turnocpu()
                }
            }
            numeroTurno++
        }
        if (recibido[0] == "facil" && recibido[1] == "x") {
            pintaJugadaVs(view)
            guardarJugadaVs(view)
            animacionSalidaX()
            if (numeroTurno == 1) {
                if (numbers == 1) {
                    turnocpu()
                } else {
                    colocaAlAzar()
                }
            }
            if (numeroTurno > 1 && !verificarGameover()) {
                if (numbers == 1) {
                    colocaAlAzar()
                } else {
                    turnocpu()
                }
            }
            numeroTurno++

        }
        if (recibido[0] == "facil" && recibido[1] == "o") {

            pintaJugadaVs(view)
            guardarJugadaVs(view)
            animacionSalidaX()
            if (numeroTurno == 1) {
                colocaAlAzar()

            }
            if (numeroTurno > 1 && !verificarGameover()) {
                if (numbers == 1) {
                    colocaAlAzar()
                } else {
                    turnocpu()
                }
            }

            numeroTurno++
        }


        if (recibido[0] == "libre") {
            pintaJugada(view) //dibuja la x ó 0
            guardarJugada(view)
        }

        if (verificarGameover()) {
            Handler(Looper.getMainLooper()).postDelayed({
                gameOver()
            }, 500)


        }

    }

    fun pintaJugada(view: View) {

        if (turno == "x") {

            view.setBackgroundResource(R.drawable.x)
            o?.startAnimation(AnimationUtils.loadAnimation(this, R.anim.scala_juego))
            x?.startAnimation(AnimationUtils.loadAnimation(this, R.anim.scale_finish))


        } else {

            view.setBackgroundResource(R.drawable.o)
            x?.startAnimation(AnimationUtils.loadAnimation(this, R.anim.scala_juego))
            o?.startAnimation(AnimationUtils.loadAnimation(this, R.anim.scale_finish))

        }
        view.isEnabled = false


    }

    fun juegoInteligente() {

        var numbers = mutableListOf(1, 3, 5, 7).randomOrNull()!!
        //caso 1 centro

        if (arregloJuego[4] == 2 && (arregloJuego[1] == 1 || arregloJuego[3] == 1 || arregloJuego[5] == 1 || arregloJuego[7] == 1)) {

            ponenEsquinas()
            fichaColocada = true
        }

        numbers = mutableListOf(1, 2).randomOrNull()!!

        //caso 2 esquinas y caso 3

        if (!fichaColocada) {
            if (numbers == 1) {/// jugada al azar

                numbers = mutableListOf(1, 2).randomOrNull()!!

                if ((arregloJuego[0] == 2 && arregloJuego[2] == 1) || (arregloJuego[0] == 1 && arregloJuego[2] == 2)) {
                    when (numbers) {
                        1 -> {
                            posicionSiete()
                        }
                        2 -> {
                            posicionNueve()
                        }
                        else -> {}
                    }
                    fichaColocada = true
                }
                if ((arregloJuego[0] == 2 && arregloJuego[6] == 1) || (arregloJuego[0] == 1 && arregloJuego[6] == 2)) {
                    when (numbers) {
                        1 -> {
                            posicionTres()
                        }
                        2 -> {
                            posicionNueve()
                        }
                        else -> {}
                    }
                    fichaColocada = true
                }
                if ((arregloJuego[0] == 2 && arregloJuego[8] == 1) || (arregloJuego[0] == 1 && arregloJuego[8] == 2)) {
                    when (numbers) {
                        1 -> {
                            posicionTres()
                        }
                        2 -> {
                            posicionSiete()
                        }
                        else -> {}
                    }
                    fichaColocada = true
                }
                if ((arregloJuego[2] == 2 && arregloJuego[8] == 1) || (arregloJuego[2] == 1 && arregloJuego[8] == 2)) {
                    when (numbers) {
                        1 -> {
                            posicionUno()
                        }
                        2 -> {
                            posicionSiete()
                        }
                        else -> {}
                    }
                    fichaColocada = true
                }
                if ((arregloJuego[6] == 2 && arregloJuego[8] == 1) || (arregloJuego[6] == 1 && arregloJuego[8] == 2)) {
                    when (numbers) {
                        1 -> {
                            posicionUno()
                        }
                        2 -> {
                            posicionTres()
                        }
                        else -> {}
                    }
                    fichaColocada = true
                }
                if ((arregloJuego[6] == 2 && arregloJuego[2] == 1) || (arregloJuego[6] == 1 && arregloJuego[2] == 2)) {
                    when (numbers) {
                        1 -> {
                            posicionUno()
                        }
                        2 -> {
                            posicionNueve()
                        }
                        else -> {}
                    }
                    fichaColocada = true
                }
            } else {
                if (arregloJuego[0] == 2 && arregloJuego[6] == 1) {
                    posicionDos()
                    fichaColocada = true
                }
                if (arregloJuego[2] == 2 && arregloJuego[0] == 1) {
                    posicionSeis()
                    fichaColocada = true
                }
                if (arregloJuego[8] == 2 && arregloJuego[2] == 1) {
                    posicionOcho()
                    fichaColocada = true
                }
                if (arregloJuego[6] == 2 && arregloJuego[8] == 1) {
                    posicionCuatro()
                    fichaColocada = true
                }
            }
        }

        if (!fichaColocada) {

            //caso cuatro

            if ((arregloJuego[0] == 2 && arregloJuego[1] == 1) || (arregloJuego[8] == 2 && arregloJuego[5] == 1)) {
                posicionSiete()
                fichaColocada = true
            }
            if ((arregloJuego[2] == 2 && arregloJuego[1] == 1) || (arregloJuego[6] == 2 && arregloJuego[3] == 1)) {
                posicionNueve()
                fichaColocada = true
            }
            if ((arregloJuego[6] == 2 && arregloJuego[7] == 1) || (arregloJuego[2] == 2 && arregloJuego[5] == 1)) {
                posicionUno()
                fichaColocada = true
            }
            if ((arregloJuego[3] == 2 && arregloJuego[6] == 1) || (arregloJuego[8] == 2 && arregloJuego[7] == 1)) {
                posicionTres()
                fichaColocada = true
            }
        }
        //caso cinco

        if (!fichaColocada) {
            if (arregloJuego[0] == 2 && arregloJuego[7] == 1) {
                posicionTres()
                fichaColocada = true
            }
            if (arregloJuego[2] == 2 && arregloJuego[3] == 1) {
                posicionNueve()
                fichaColocada = true
            }
            if (arregloJuego[8] == 2 && arregloJuego[1] == 1) {
                posicionSiete()
                fichaColocada = true
            }
            if (arregloJuego[6] == 2 && arregloJuego[5] == 1) {
                posicionUno()
                fichaColocada = true
            }

        }
        if (!fichaColocada) {
            ponenEsquinas()
        }

        fichaColocada = false


    }

    fun recibirDatos() {

        val bundle = intent.extras
        recibido = bundle!!.getStringArrayList("datos_juego")!!
        recibidoMarcador = bundle!!.getIntArray("resultados_juego")!!

    }

    fun pintaJugadaVs(view: View) {

        view.setBackgroundResource(R.drawable.x)
        view.isEnabled = false

    }

    fun guardarJugadaVs(view: View) {

        val rutaNombre = resources.getResourceName(view.id)
        nombreClick =
            rutaNombre.substring(rutaNombre.lastIndexOf("/") + 1)// devuelve GUARDA  el lugar de la imagen tocada

        for (i in 1..9) {  //buca el nombre de la imagen del 1 al 9

            var nombreConcatenado = "imageView$i"

            if (nombreClick == nombreConcatenado) {
                arregloJuego[i - 1] = 1 //guarda el turno en posicion
                break
            }

        }
    }

    fun guardarJugada(view: View) {

        val rutaNombre = resources.getResourceName(view.id)
        nombreClick =
            rutaNombre.substring(rutaNombre.lastIndexOf("/") + 1)// devuelve GUARDA  el lugar de la imagen tocada

        for (i in 1..9) {  //buca el nombre de la imagen del 1 al 9


            var nombreConcatenado = "imageView$i"

            if (nombreClick == nombreConcatenado) { // si coincide la imagen tocada con la imagen en turno

                if (turno == "x") {
                    arregloJuego[i - 1] = 1 //guarda el turno en posicion
                    turno = "o"
                    break

                } else {
                    arregloJuego[i - 1] = 2//guarda el turno en posicion
                    turno = "x"
                    break
                }

            }

        }

    }

    fun posicionUno() {
        arregloJuego[0] = 2
        imageView1?.isEnabled = false
        Handler(Looper.getMainLooper()).postDelayed({
            reproducirSonido()
            animacionCambioSalidaO()
            imageView1?.setBackgroundResource(R.drawable.o)
        }, tiempoRespuesta)
    }

    fun posicionDos() {
        arregloJuego[1] = 2
        imageView2?.isEnabled = false
        Handler(Looper.getMainLooper()).postDelayed({
            reproducirSonido()
            animacionCambioSalidaO()
            imageView2?.setBackgroundResource(R.drawable.o)
        }, tiempoRespuesta)
    }

    fun posicionTres() {
        arregloJuego[2] = 2
        imageView3?.isEnabled = false
        Handler(Looper.getMainLooper()).postDelayed({
            reproducirSonido()
            animacionCambioSalidaO()
            imageView3?.setBackgroundResource(R.drawable.o)
        }, tiempoRespuesta)
    }

    fun posicionCuatro() {
        arregloJuego[3] = 2
        imageView4?.isEnabled = false
        Handler(Looper.getMainLooper()).postDelayed({
            reproducirSonido()
            animacionCambioSalidaO()
            imageView4?.setBackgroundResource(R.drawable.o)
        }, tiempoRespuesta)
    }

    fun posicionCinco() {
        arregloJuego[4] = 2
        imageView5?.isEnabled = false
        Handler(Looper.getMainLooper()).postDelayed({
            reproducirSonido()
            animacionCambioSalidaO()
            imageView5?.setBackgroundResource(R.drawable.o)
        }, tiempoRespuesta)
    }

    fun posicionSeis() {
        arregloJuego[5] = 2
        imageView6?.isEnabled = false
        Handler(Looper.getMainLooper()).postDelayed({
            reproducirSonido()
            animacionCambioSalidaO()
            imageView6?.setBackgroundResource(R.drawable.o)
        }, tiempoRespuesta)
    }

    fun posicionSiete() {
        arregloJuego[6] = 2
        imageView7?.isEnabled = false
        Handler(Looper.getMainLooper()).postDelayed({
            reproducirSonido()
            animacionCambioSalidaO()
            imageView7?.setBackgroundResource(R.drawable.o)
        }, tiempoRespuesta)
    }

    fun posicionOcho() {
        arregloJuego[7] = 2
        imageView8?.isEnabled = false
        Handler(Looper.getMainLooper()).postDelayed({
            reproducirSonido()
            animacionCambioSalidaO()
            imageView8?.setBackgroundResource(R.drawable.o)
        }, tiempoRespuesta)
    }

    fun posicionNueve() {
        arregloJuego[8] = 2
        imageView9?.isEnabled = false
        Handler(Looper.getMainLooper()).postDelayed({
            reproducirSonido()
            animacionCambioSalidaO()
            imageView9?.setBackgroundResource(R.drawable.o)
        }, tiempoRespuesta)
    }

    fun turnocpu() {

        if (arregloJuego[4] == 1 && numeroTurno == 1) { //turno 1 con x en medio

            ponenEsquinas()
        }

        if (numeroTurno == 1 && (arregloJuego[0] == 1 || arregloJuego[2] == 1 || arregloJuego[6] == 1 || arregloJuego[8] == 1)) {  //turno 1 con x en esquinas
            posicionCinco()

        }
        if (numeroTurno == 1 && (arregloJuego[1] == 1 || arregloJuego[3] == 1 || arregloJuego[5] == 1 || arregloJuego[7] == 1)) //turno 1 con x en cruz
        {
            var numbers = mutableListOf(1, 3, 5)
            if (arregloJuego[1] == 1) {
                numbers = mutableListOf(1, 3, 5)
            }
            if (arregloJuego[3] == 1) {
                numbers = mutableListOf(1, 7, 5)
            }
            if (arregloJuego[5] == 1) {
                numbers = mutableListOf(3, 9, 5)
            }
            if (arregloJuego[7] == 1) {
                numbers = mutableListOf(7, 9, 5)
            }

            when (numbers.randomOrNull()) {
                1 -> {
                    posicionUno()
                }
                3 -> {
                    posicionTres()
                }
                5 -> {
                    posicionCinco()
                }
                7 -> {
                    posicionSiete()
                }
                9 -> {
                    posicionNueve()
                }
                else -> {}
            }

        }
        if (numeroTurno == 2) {

            if (recibido[1] == "o") {
                buscaGane()
            }
            impideGane()


            if (!fichaColocada) {


                if (numeroTurno == 2 && arregloJuego[4] == 0) {

                    posicionCinco()
                }//coloca lo antes posible o en centro sino

                else {

                    if (numeroTurno == 2 && (arregloJuego[4] == 1))  //turno dos con x  en medio
                    {
                        if ((arregloJuego[0] != arregloJuego[8]) || (arregloJuego[2] != arregloJuego[6]))//
                        {
                            ponenEsquinas()

                        } else {
                            if ((arregloJuego[0] == 0 && arregloJuego[2] == 0 && arregloJuego[6] == 0 && arregloJuego[8] == 0)) {
                                val numbers = mutableListOf(1, 3, 7, 9)

                                when (numbers.randomOrNull()) {
                                    1 -> {
                                        posicionUno()
                                    }
                                    3 -> {
                                        posicionTres()
                                    }
                                    7 -> {
                                        posicionSiete()
                                    }
                                    9 -> {
                                        posicionNueve()
                                    }
                                    else -> {}

                                }
                            }
                        }

                    }
                    if (numeroTurno == 2 && arregloJuego[4] == 2) {//turno dos con x  en medio


                        if ((arregloJuego[0] == 1 && arregloJuego[8] == 1) || (arregloJuego[2] == 1 && arregloJuego[6] == 1)) {

                            ponenCruz()

                        } else {

                            ponenEsquinas()
                        }
                    }
                }
            }
        }

        if (numeroTurno == 3) {
            buscaGane()
            if (!fichaColocada) {
                impideGane()
                if (!fichaColocada) {
                    colocaAlAzar()
                }

            }
        }

        if (numeroTurno == 4) {
            buscaGane()
            if (!fichaColocada) {
                impideGane()
                if (!fichaColocada) {
                    colocaAlAzar()
                }

            }

        }

        if (numeroTurno == 5) {
            buscaGane()

            if (!fichaColocada) {
                impideGane()


            }

        }

        fichaColocada = false


    }

    fun ponenCruz() {

        val numbers = mutableListOf(2, 4, 6, 8)

        when (numbers.randomOrNull()) {
            2 -> {
                posicionDos()
            }
            4 -> {
                posicionCuatro()
            }
            6 -> {
                posicionSeis()
            }
            8 -> {
                posicionOcho()
            }
            else -> {}

        }
    }

    fun ponenEsquinas() {
        val numbers = mutableListOf(1, 2)
        val numbers2 = mutableListOf(1, 3, 7, 9)
        if (arregloJuego[0] == 0 && arregloJuego[8] == 0 && arregloJuego[2] == 0 && arregloJuego[6] == 0) {
            when (numbers2.randomOrNull()) {
                1 -> {
                    posicionUno()
                }
                3 -> {
                    posicionTres()
                }
                7 -> {
                    posicionSiete()
                }
                9 -> {
                    posicionNueve()
                }
                else -> {}
            }
        } else {
            if (arregloJuego[0] == 0 && arregloJuego[8] == 0) {
                when (numbers.randomOrNull()) {
                    1 -> {
                        posicionUno()
                    }
                    2 -> {
                        posicionNueve()
                    }
                    else -> {}
                }
            } else {
                if (arregloJuego[2] == 0 && arregloJuego[6] == 0) {

                    when (numbers.randomOrNull()) {
                        1 -> {
                            posicionTres()
                        }
                        2 -> {
                            posicionSiete()
                        }
                        else -> {}

                    }
                }
            }
        }

    }

    fun colocaAlAzar() {

        var pasa = false
        var numbers: Int
        while (!pasa) {
            if (numeroTurno == 3) {

                numbers = intArrayOf(1, 3, 5, 7).randomOrNull()!!

            } else {
                if (numeroTurno == 1 && recibido[0] == "imposible") {

                    numbers = intArrayOf(0, 2, 4, 6, 8).randomOrNull()!!
                } else {
                    numbers = intArrayOf(0, 1, 2, 3, 4, 5, 6, 7, 8).randomOrNull()!!
                }
            }

            if (arregloJuego[numbers] == 0) {
                pasa = true
                when (numbers) {
                    0 -> {
                        posicionUno()
                    }
                    1 -> {
                        posicionDos()
                    }
                    2 -> {
                        posicionTres()
                    }
                    3 -> {
                        posicionCuatro()
                    }
                    4 -> {
                        posicionCinco()
                    }
                    5 -> {
                        posicionSeis()
                    }
                    6 -> {
                        posicionSiete()
                    }
                    7 -> {
                        posicionOcho()
                    }
                    8 -> {
                        posicionNueve()
                    }
                    else -> {}
                }
            }

        }

    }

    fun animacionCambioSalidaO() {
        x?.startAnimation(AnimationUtils.loadAnimation(this, R.anim.scala_juego))
        o?.startAnimation(AnimationUtils.loadAnimation(this, R.anim.scale_finish))

    }

    fun animacionSalidaX() {
        o?.startAnimation(AnimationUtils.loadAnimation(this, R.anim.scala_juego))
        x?.startAnimation(AnimationUtils.loadAnimation(this, R.anim.scale_finish))
    }

    fun buscaGane() {

        if (((arregloJuego[0] == 2 && arregloJuego[2] == 2) && arregloJuego[1] == 0) && (!fichaColocada)) {/// gane con  lineas si falta en medio en medio
            posicionDos()
            fichaColocada = true

        }
        if (((arregloJuego[0] == 2 && arregloJuego[6] == 2) && arregloJuego[3] == 0) && (!fichaColocada)) {

            posicionCuatro()
            fichaColocada = true

        }
        if (((arregloJuego[2] == 2 && arregloJuego[8] == 2) && arregloJuego[5] == 0) && (!fichaColocada)) {

            posicionSeis()
            fichaColocada = true

        }
        if (((arregloJuego[6] == 2 && arregloJuego[8] == 2) && arregloJuego[7] == 0) && (!fichaColocada)) {

            posicionOcho()
            fichaColocada = true

        }
        if (!fichaColocada) {

            if (((arregloJuego[8] == 2 && arregloJuego[5] == 2) || (arregloJuego[0] == 2 && arregloJuego[1] == 2)) && arregloJuego[2] == 0) {/// gane con lineas si faltaesquinas

                posicionTres()
                fichaColocada = true

            }

            if (((arregloJuego[2] == 2 && arregloJuego[5] == 2) || (arregloJuego[6] == 2 && arregloJuego[7] == 2)) && arregloJuego[8] == 0) {

                posicionNueve()
                fichaColocada = true

            }

            if (((arregloJuego[0] == 2 && arregloJuego[3] == 2) || (arregloJuego[7] == 2 && arregloJuego[8] == 2)) && arregloJuego[6] == 0) {

                posicionSiete()
                fichaColocada = true

            }
            if (((arregloJuego[3] == 2 && arregloJuego[6] == 2) || (arregloJuego[1] == 2 && arregloJuego[2] == 2)) && arregloJuego[0] == 0) {

                posicionUno()
                fichaColocada = true

            }
        }
        if (!fichaColocada) {
            ///gane con equis
            if ((arregloJuego[0] == 2 && arregloJuego[4] == 2) && arregloJuego[8] == 0) {
                posicionNueve()
                fichaColocada = true
            } else {
                if ((arregloJuego[2] == 2 && arregloJuego[4] == 2) && arregloJuego[6] == 0) {
                    posicionSiete()
                    fichaColocada = true
                } else {
                    if ((arregloJuego[6] == 2 && arregloJuego[4] == 2) && arregloJuego[2] == 0) {
                        posicionTres()
                        fichaColocada = true
                    } else {
                        if ((arregloJuego[8] == 2 && arregloJuego[4] == 2) && arregloJuego[0] == 0) {
                            posicionUno()
                            fichaColocada = true
                        }
                    }
                }
            }

            //gane con cuz

            if (arregloJuego[1] == 2 && arregloJuego[4] == 2 && arregloJuego[7] == 0) {
                posicionOcho()
                fichaColocada = true

            }
            if (arregloJuego[3] == 2 && arregloJuego[4] == 2 && arregloJuego[5] == 0) {
                posicionSeis()
                fichaColocada = true

            }
            if (arregloJuego[5] == 2 && arregloJuego[4] == 2 && arregloJuego[3] == 0) {
                posicionCuatro()
                fichaColocada = true


            }
            if (arregloJuego[7] == 2 && arregloJuego[4] == 2 && arregloJuego[1] == 0) {
                posicionDos()
                fichaColocada = true

            }

        }
    }

    fun impideGane() {
        /////impidegane equis cruz
        if (arregloJuego[1] == 1 && arregloJuego[4] == 1 && arregloJuego[7] == 0) {
            posicionOcho()
            fichaColocada = true

        }
        if ((arregloJuego[3] == 1 && arregloJuego[4] == 1 && arregloJuego[5] == 0) && (!fichaColocada)) {
            posicionSeis()
            fichaColocada = true


        }
        if ((arregloJuego[5] == 1 && arregloJuego[4] == 1 && arregloJuego[3] == 0) && (!fichaColocada)) {
            posicionCuatro()
            fichaColocada = true


        }
        if ((arregloJuego[7] == 1 && arregloJuego[4] == 1 && arregloJuego[1] == 0) && (!fichaColocada)) {
            posicionDos()
            fichaColocada = true

        }
        ////// evita en medio

        if ((arregloJuego[0] == 1 && arregloJuego[2] == 1 && arregloJuego[1] == 0) && (!fichaColocada)) {
            posicionDos()
            fichaColocada = true

        }
        if ((arregloJuego[0] == 1 && arregloJuego[6] == 1 && arregloJuego[3] == 0) && (!fichaColocada)) {

            posicionCuatro()
            fichaColocada = true

        }
        if ((arregloJuego[2] == 1 && arregloJuego[8] == 1 && arregloJuego[5] == 0) && (!fichaColocada)) {
            posicionSeis()
            fichaColocada = true

        }
        if ((arregloJuego[6] == 1 && arregloJuego[8] == 1 && arregloJuego[7] == 0) && (!fichaColocada)) {
            posicionOcho()
            fichaColocada = true

        }

//impide gane en esquinas

        if ((((arregloJuego[0] == 1 && arregloJuego[1] == 1) || (arregloJuego[8] == 1 && arregloJuego[5] == 1)) && arregloJuego[2] == 0) && (!fichaColocada)) {

            posicionTres()
            fichaColocada = true

        }
        if ((((arregloJuego[1] == 1 && arregloJuego[2] == 1) || (arregloJuego[3] == 1 && arregloJuego[6] == 1)) && arregloJuego[0] == 0) && (!fichaColocada)) {
            posicionUno()
            fichaColocada = true

        }
        if ((((arregloJuego[0] == 1 && arregloJuego[3] == 1) || (arregloJuego[7] == 1 && arregloJuego[8] == 1)) && arregloJuego[6] == 0) && (!fichaColocada)) {
            posicionSiete()
            fichaColocada = true

        }
        if ((((arregloJuego[6] == 1 && arregloJuego[7] == 1) || (arregloJuego[2] == 1 && arregloJuego[5] == 1)) && arregloJuego[8] == 0) && (!fichaColocada)) {
            posicionNueve()
            fichaColocada = true

        }


///

        /////impidegane equis
        if ((arregloJuego[0] == 1 && arregloJuego[4] == 1 && arregloJuego[8] == 0) && (!fichaColocada)) {

            posicionNueve()
            fichaColocada = true

        }
        if ((arregloJuego[2] == 1 && arregloJuego[4] == 1 && arregloJuego[6] == 0) && (!fichaColocada)) {

            posicionSiete()
            fichaColocada = true

        }
        if ((arregloJuego[6] == 1 && arregloJuego[4] == 1 && arregloJuego[2] == 0) && (!fichaColocada)) {

            posicionTres()
            fichaColocada = true

        }
        if ((arregloJuego[8] == 1 && arregloJuego[4] == 1 && arregloJuego[0] == 0) && (!fichaColocada)) {

            posicionUno()
            fichaColocada = true

        }


    }

    fun verificarGameover(): Boolean {

        var ganador = false
        var contador = 0
        var jugadas = 0
        var puntosGanadorX = 0
        var puntosGanadorO = 0


        for (i in 0..2) {
            for (j in 0..2) {
                matrizJuego[i][j] = arregloJuego[contador]
                contador++
                if (matrizJuego[i][j] != 0) {
                    jugadas++
                }
                if (contador > 8 && jugadas > 8) {
                    cancelar = true//cancela cronometro
                    ganador = true//empate
                }

            }
        }

//verificar ganador en filas

        for (i in 0..2) {
            for (j in 0..2) {
                if (matrizJuego[i][j] == 1) {
                    puntosGanadorX++
                    if (puntosGanadorX == 3) {
                        ganador = true
                        ganadorFinal = "x"
                        break
                    }
                }
                if (matrizJuego[i][j] == 2) {
                    puntosGanadorO++
                    if (puntosGanadorO == 3) {
                        ganador = true;
                        ganadorFinal = "o"
                        break
                    }
                }
            }

            puntosGanadorX = 0
            puntosGanadorO = 0

        }
//verificar ganador en columnas
        for (i in 0..2) {
            for (j in 0..2) {
                if (matrizJuego[j][i] == 1) {
                    puntosGanadorX++
                    if (puntosGanadorX == 3) {
                        ganador = true
                        ganadorFinal = "x"
                        break
                    }
                }
                if (matrizJuego[j][i] == 2) {
                    puntosGanadorO++
                    if (puntosGanadorO == 3) {
                        ganador = true;
                        ganadorFinal = "o"
                        break
                    }
                }
            }
            puntosGanadorX = 0
            puntosGanadorO = 0

        }
        //verificar ganador en cruz

        if (matrizJuego[0][0] == 1 && matrizJuego[1][1] == 1 && matrizJuego[2][2] == 1) {
            ganador = true;
            ganadorFinal = "x"
        }
        if (matrizJuego[0][2] == 1 && matrizJuego[1][1] == 1 && matrizJuego[2][0] == 1) {
            ganador = true;
            ganadorFinal = "x"
        }
        if (matrizJuego[0][0] == 2 && matrizJuego[1][1] == 2 && matrizJuego[2][2] == 2) {
            ganador = true;
            ganadorFinal = "o"
        }
        if (matrizJuego[0][2] == 2 && matrizJuego[1][1] == 2 && matrizJuego[2][0] == 2) {
            ganador = true;
            ganadorFinal = "o"
        }

        if (ganador) {
            cancelar = true//cancela cronometro
        }

        return ganador

    }

    fun gameOver() {

        val builder = AlertDialog.Builder(this)
        builder.setTitle("GAME OVER")

        if (recibido[0] == "libre") {
            when (ganadorFinal) {
                "x" -> {
                    builder.setMessage("Ganador X")
                    recibidoMarcador[2]++
                }
                "o" -> {
                    builder.setMessage("Ganador O")
                    recibidoMarcador[0]++
                }
                else -> {
                    builder.setMessage("empate")
                    recibidoMarcador[1]++
                }
            }

        } else {
            if (cancelar) {
                if (ganadorFinal == "x") {
                    builder.setMessage("Tú ganas")
                    recibidoMarcador[2]++
                }
                if (ganadorFinal == "") {
                    builder.setMessage("Empate")
                    recibidoMarcador[1]++
                }
                if (ganadorFinal == "o") {
                    builder.setMessage("Tú pierdes")
                    recibidoMarcador[0]++
                }

            } else {

                builder.setMessage("Se acabó el tiempo")
            }

        }


        builder.setNeutralButton("Reiniciar juego") { dialog, which ->
            val intento = intent
            intento.putExtra("resultados_juego", recibidoMarcador)
            finish()
            startActivity(intento)
        }
        builder.setNegativeButton("Menu principal") { dialog, which ->

            val intent = Intent(this, MenuJuego::class.java)
            finishAffinity()
            startActivity(intent)
        }
        builder.setCancelable(false)
        builder.show()

        imageView1?.isEnabled = false
        imageView2?.isEnabled = false
        imageView3?.isEnabled = false
        imageView4?.isEnabled = false
        imageView5?.isEnabled = false
        imageView6?.isEnabled = false
        imageView7?.isEnabled = false
        imageView8?.isEnabled = false
        imageView9?.isEnabled = false


    }

    fun reproducirSonido() {
        mediaPlayer = MediaPlayer.create(applicationContext,R.raw.fichas)
        mediaPlayer.start()
        Handler(Looper.getMainLooper()).postDelayed({
            mediaPlayer.release()
        }, 150)

    }



    fun conometro() {

        textContador!!.visibility = View.VISIBLE
        object : CountDownTimer(tiempoOut, 1000) {

            override fun onTick(p0: Long) {
                val tiempoSegundos = (p0 / 1000).toInt()
                textContador?.text = tiempoSegundos.toString()
                if (cancelar) {
                    cancel()
                }
            }

            override fun onFinish() {
                gameOver()

            }
        }.start()
    }


    override fun onBackPressed() {
        val intent = Intent(this, MenuJuego::class.java)
        cancelar=true
        finishAffinity()
        onDestroy()
        startActivity(intent)
    }

}




