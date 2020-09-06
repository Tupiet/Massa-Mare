package com.example.massamare

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import kotlinx.android.synthetic.main.activity_start.*

class StartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        // This region is what makes possible the Remote Config
        // Remote Config (Firebase) is used to detect updates
        // and also to get variables from the cloud.
        // region REMOTE CONFIG
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 5
        }

        val firebaseConfig = Firebase.remoteConfig
        firebaseConfig.setConfigSettingsAsync((configSettings))
        firebaseConfig.setDefaultsAsync(mapOf("min_version" to BuildConfig.VERSION_NAME))
        firebaseConfig.setDefaultsAsync(mapOf("update_title" to "¡Nueva actualización!"))
        firebaseConfig.setDefaultsAsync(mapOf("update_description" to "Deberías actualizar ya, ¡tiene nuevas opciones!"))

        Firebase.remoteConfig.fetchAndActivate().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val minVersion = Firebase.remoteConfig.getString("min_version")
                val updateTitle = Firebase.remoteConfig.getString("update_title")
                val updateDescription = Firebase.remoteConfig.getString("update_description")

                val versionFinal = BuildConfig.VERSION_NAME

                if (versionFinal != minVersion){
                    val builder = AlertDialog.Builder(this)
                    builder.setTitle(updateTitle)
                    builder.setMessage(updateDescription)

                    builder.setPositiveButton(android.R.string.yes) { dialog, which ->
                        val url = "https://massamare.tk"
                        val intent = Intent(Intent.ACTION_VIEW)
                        intent.data = Uri.parse(url)
                        startActivity(intent)
                    }

                    builder.setNeutralButton("Cerrar") { dialog, which ->

                    }
                    builder.show()
                }

            }

        }

        // endregion

        startMassaMare.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        startPoolish.setOnClickListener {
            val intent = Intent(this, PoolishMainActivity::class.java)
            startActivity(intent)
        }


    }
}
