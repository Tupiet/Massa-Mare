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

        fun calculate() {
            val pesDesitjat = Integer.parseInt(input.text.toString())

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



        button.setOnClickListener {
            calculate()
        }

        config.setOnClickListener {
            val intent = Intent(this, ConfigurationActivity::class.java).apply {
            }

            startActivity(intent)
            finish()
        }
    }


}
