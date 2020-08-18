package com.example.massamare

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import kotlinx.android.synthetic.main.activity_main.*



class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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

        val button: Button = this.findViewById(R.id.button)
        val config: Button = findViewById(R.id.button3)

        val input: EditText = findViewById(R.id.textView)
        val farinaOutput: TextView = findViewById(R.id.farinaOutput)
        val aiguaOutput: TextView = findViewById(R.id.aiguaOutput)
        val massaMareOutput: TextView = findViewById(R.id.massaMareOutput)
        val salOutput: TextView = findViewById(R.id.salOutput)

        val pref = getPreferences(Context.MODE_PRIVATE)
        val pes = pref.getInt("PES", 0)
        textView.setText(pes.toString())

        fun calculate() {
            var pesDesitjat = 0
            if (input.text.toString() == "") {
                pesDesitjat = 0
                var toast = Toast.makeText(this, "Has d'introduïr un valor correcte!", Toast.LENGTH_SHORT)
                toast.setGravity(Gravity.TOP, 0, 10)
                toast.show()

            } else {
                pesDesitjat = Integer.parseInt(input.text.toString())
            }


            // region OBTENIM LES DADES DE LA PÀGINA DE CONFIGURACIÓ
            val aiguaRebuda = intent.getFloatExtra("percentatgeAigua", 60F)
            val salRebuda = intent.getFloatExtra("percentatgeSal", 2F)
            val massaMareRebuda = intent.getFloatExtra("percentatgeMassaMare", 20F)
            val farinaMassaMareRebuda = intent.getFloatExtra("percentatgeFarinaMassaMare", 50F)
            val aiguaMassaMareRebuda = intent.getFloatExtra("percentatgeAiguaMassaMare", 50F)
            // endregion
            val editor = pref.edit()

            val aiguaReduida = aiguaRebuda / 100
            val salReduida = salRebuda / 100
            val massaMareReduida = massaMareRebuda / 100
            val farinaMassaMareReduida = farinaMassaMareRebuda / 100
            val aiguaMassaMareReduida = aiguaMassaMareRebuda / 100


            editor.putInt("PES", pesDesitjat)
            editor.putFloat("AIGUA_GUARDADA", aiguaReduida)
            editor.putFloat("SAL_GUARDADA", salReduida)
            editor.putFloat("MASSA_MARE_GUARDADA", massaMareReduida)
            editor.putFloat("FARINA_MASSA_MARE_GUARDADA", farinaMassaMareReduida)
            editor.putFloat("AIGUA_MASSA_MARE_GUARDADA", aiguaMassaMareReduida)

            editor.commit()
            val aiguaGuardada = pref.getFloat("AIGUA_GUARDADA", aiguaReduida)
            val salGuardada = pref.getFloat("SAL_GUARDADA", aiguaReduida)
            val massaMareGuardada = pref.getFloat("MASSA_MARE_GUARDADA", aiguaReduida)
            val farinaMassaMareGuardada = pref.getFloat("FARINA_MASSA_MARE_GUARDADA", aiguaReduida)
            val aiguaMassaMareGuardada = pref.getFloat("AIGUA_MASSA_MARE_GUARDADA", aiguaReduida)


            val tantPerCentMassaMare = massaMareGuardada
            val tantPerCentAigua = aiguaGuardada
            val tantPerCentSal = salGuardada
            val tantPerCentSuma = 1 + tantPerCentAigua + tantPerCentSal
            val tantPerCentFarinaMassaMare = farinaMassaMareGuardada
            val tantPerCentAiguaMassaMare = aiguaMassaMareGuardada

            val farinaNecessariaTotal = Math.round(1 / tantPerCentSuma * pesDesitjat)
            val aiguaNecessariaTotal = Math.round(farinaNecessariaTotal * tantPerCentAigua)
            val salNecessariaTotal = Math.round(farinaNecessariaTotal * tantPerCentSal)
            val massaMareNecessariaTotal = Math.round(farinaNecessariaTotal * tantPerCentMassaMare)
            val farinaDinsMassaMare = Math.round(massaMareNecessariaTotal * tantPerCentFarinaMassaMare)
            val aiguaDinsMassaMare = Math.round(massaMareNecessariaTotal * tantPerCentAiguaMassaMare)

            val farinaFinal = farinaNecessariaTotal - farinaDinsMassaMare
            val aiguaFinal = aiguaNecessariaTotal - aiguaDinsMassaMare

            farinaOutput.text = farinaFinal.toString()
            aiguaOutput.text = aiguaFinal.toString()
            massaMareOutput.text = massaMareNecessariaTotal.toString()
            salOutput.text = salNecessariaTotal.toString()

            editor.commit()
        }

        calculate()


        fun View.hideKeyboard() {
            val inputManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(windowToken, 0)
        }

        button.setOnClickListener {
            it.hideKeyboard()
            calculate()
        }

        config.setOnClickListener {
            val intent = Intent(this, ConfigurationActivity::class.java)
            startActivity(intent)
            finish()
        }

        suma.setOnClickListener{
            val original: EditText = findViewById(R.id.textView)
            var nouPes = Integer.parseInt(original.text.toString())
            nouPes += 25
            original.setText(nouPes.toString())
            calculate()
        }

        resta.setOnClickListener{
            val original: EditText = findViewById(R.id.textView)
            var nouPes = Integer.parseInt(original.text.toString())
            nouPes -= 25
            original.setText(nouPes.toString())
            calculate()
        }
    }
}
