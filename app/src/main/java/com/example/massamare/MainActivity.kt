package com.example.massamare

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button: Button = findViewById(R.id.button)
        val config: Button = findViewById(R.id.button3)

        val input: EditText = findViewById(R.id.textView)
        val farinaOutput: TextView = findViewById(R.id.farinaOutput)
        val aiguaOutput: TextView = findViewById(R.id.aiguaOutput)
        val massaMareOutput: TextView = findViewById(R.id.massaMareOutput)
        val salOutput: TextView = findViewById(R.id.salOutput)

        val pref = getPreferences(Context.MODE_PRIVATE)
        val pes = pref.getInt("PES", 0)
        textView.setText(pes.toString())






        button.setOnClickListener {
            val pesDesitjat = Integer.parseInt(input.text.toString())

            // region OBTENIM LES DADES DE LA PÀGINA DE CONFIGURACIÓ
            val aiguaRebuda = intent.getFloatExtra("percentatgeAigua", 0.6F)
            val salRebuda = intent.getFloatExtra("percentatgeSal", 0.02F)
            val massaMareRebuda = intent.getFloatExtra("percentatgeMassaMare", 0.2F)
            val farinaMassaMareRebuda = intent.getFloatExtra("percentatgeFarinaMassaMare", 0.5F)
            val aiguaMassaMareRebuda = intent.getFloatExtra("percentatgeAiguaMassaMare", 0.5F)
            // endregion

            var aiguaGuardada = pref.getFloat("AIGUA_GUARDADA", aiguaRebuda)
            var salGuardada = pref.getFloat("SAL_GUARDADA", aiguaRebuda)
            var massaMareGuardada = pref.getFloat("MASSA_MARE_GUARDADA", aiguaRebuda)
            var farinaMassaMareGuardada = pref.getFloat("FARINA_MASSA_MARE_GUARDADA", aiguaRebuda)
            var aiguaMassaMareGuardada = pref.getFloat("AIGUA_MASSA_MARE_GUARDADA", aiguaRebuda)

            val editor = pref.edit()
            editor.putInt("PES", pesDesitjat)
            editor.putFloat("AIGUA_GUARDADA", aiguaRebuda)
            editor.putFloat("SAL_GUARDADA", salRebuda)
            editor.putFloat("MASSA_MARE_GUARDADA", massaMareRebuda)
            editor.putFloat("FARINA_MASSA_MARE_GUARDADA", farinaMassaMareRebuda)
            editor.putFloat("AIGUA_MASSA_MARE_GUARDADA", aiguaMassaMareRebuda)
            editor.commit()

            aiguaGuardada = pref.getFloat("AIGUA_GUARDADA", aiguaRebuda)
            salGuardada = pref.getFloat("SAL_GUARDADA", aiguaRebuda)
            massaMareGuardada = pref.getFloat("MASSA_MARE_GUARDADA", aiguaRebuda)
            farinaMassaMareGuardada = pref.getFloat("FARINA_MASSA_MARE_GUARDADA", aiguaRebuda)
            aiguaMassaMareGuardada = pref.getFloat("AIGUA_MASSA_MARE_GUARDADA", aiguaRebuda)

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


        }

        config.setOnClickListener {
            val intent = Intent(this, ConfigurationActivity::class.java).apply {
            }

            startActivity(intent)
        }
    }


}
