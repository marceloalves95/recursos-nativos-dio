package chellotech.br.recursosnativosdio.views

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import chellotech.br.recursosnativosdio.R
import kotlinx.android.synthetic.main.activity_fotos_main.*

class FotosMain : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fotos_main)

        title = "Biblioteca de Fotos"

        pick_button.setOnClickListener {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){

                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {

                    val permission = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                    requestPermissions(permission, PERMISSION_CODE)


                }else{

                    pickImageFromGallery()
                }

            }else{

                pickImageFromGallery()

            }

        }

    }

    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray) {

        when(requestCode){

            PERMISSION_CODE -> {
                if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                    pickImageFromGallery()

                }else{

                    Toast.makeText(this,"Permissão Negada", Toast.LENGTH_SHORT).show()

                }
            }

        }


    }

    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE){

            image_view.setImageURI(data?.data)

        }
    }

    companion object{

        private val PERMISSION_CODE = 1000
        private val IMAGE_PICK_CODE = 1000

    }
}
