package chellotech.br.recursosnativosdio.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import chellotech.br.recursosnativosdio.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        agenda.setOnClickListener {

            val intent = Intent(this, AgendaMain::class.java)
            startActivity(intent)

        }
        contatos.setOnClickListener {

            val intent = Intent(this, ContatosMain::class.java)
            startActivity(intent)
        }
        fotos.setOnClickListener {

            val intent = Intent(this, FotosMain::class.java)
            startActivity(intent)

        }
        camera.setOnClickListener {

            val intent = Intent(this, CameraMain::class.java)
            startActivity(intent)

        }
        localizacao.setOnClickListener {

            val intent = Intent(this, MapsActivity::class.java)
            startActivity(intent)
        }
    }

}