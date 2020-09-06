package com.example.massamare

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*



class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
                toast.show()

            } else {
                pesDesitjat = Integer.parseInt(input.text.toString())
            }


            // region OBTENIM LES DADES DE LA PÀGINA DE CONFIGURACIÓ
            var aiguaRebuda = 60F
            var salRebuda = 2F
            var massaMareRebuda = 20F
            var perdutRebut = 17F
            var farinaMassaMareRebuda = 50F
            var aiguaMassaMareRebuda = 50F
            val intentValue = intent.getBooleanExtra("intent", false)

            var aiguaReduida = aiguaRebuda / 100
            var salReduida = salRebuda / 100
            var massaMareReduida = massaMareRebuda / 100
            var perdutReduida = 1 - perdutRebut / 100
            var farinaMassaMareReduida = farinaMassaMareRebuda / 100
            var aiguaMassaMareReduida = aiguaMassaMareRebuda / 100

            val editor = pref.edit()

            editor.putInt("PES", pesDesitjat)

            if (intentValue == true) {
                aiguaRebuda = intent.getFloatExtra("percentatgeAigua", aiguaRebuda)
                salRebuda = intent.getFloatExtra("percentatgeSal", salRebuda)
                massaMareRebuda = intent.getFloatExtra("percentatgeMassaMare", massaMareRebuda)
                perdutRebut = intent.getFloatExtra("percentatgePerdua", perdutRebut)
                farinaMassaMareRebuda = intent.getFloatExtra("percentatgeFarinaMassaMare", farinaMassaMareRebuda)
                aiguaMassaMareRebuda = intent.getFloatExtra("percentatgeAiguaMassaMare", aiguaMassaMareRebuda)

                aiguaReduida = aiguaRebuda / 100
                salReduida = salRebuda / 100
                massaMareReduida = massaMareRebuda / 100
                perdutReduida = 1 - perdutRebut / 100
                farinaMassaMareReduida = farinaMassaMareRebuda / 100
                aiguaMassaMareReduida = aiguaMassaMareRebuda / 100


                editor.putFloat("AIGUA_GUARDADA", aiguaReduida)
                editor.putFloat("SAL_GUARDADA", salReduida)
                editor.putFloat("MASSA_MARE_GUARDADA", massaMareReduida)
                editor.putFloat("PERDUT_GUARDADA", perdutReduida)
                editor.putFloat("FARINA_MASSA_MARE_GUARDADA", farinaMassaMareReduida)
                editor.putFloat("AIGUA_MASSA_MARE_GUARDADA", aiguaMassaMareReduida)
            }
            editor.commit()



            // endregion


            val aiguaGuardada = pref.getFloat("AIGUA_GUARDADA", aiguaReduida)
            val salGuardada = pref.getFloat("SAL_GUARDADA", salReduida)
            val massaMareGuardada = pref.getFloat("MASSA_MARE_GUARDADA", massaMareReduida)
            val perdutGuardada = pref.getFloat("PERDUT_GUARDADA", perdutReduida)
            val farinaMassaMareGuardada = pref.getFloat("FARINA_MASSA_MARE_GUARDADA", farinaMassaMareReduida)
            val aiguaMassaMareGuardada = pref.getFloat("AIGUA_MASSA_MARE_GUARDADA", aiguaMassaMareReduida)


            val tantPerCentMassaMare = massaMareGuardada
            val tantPerCentAigua = aiguaGuardada
            val tantPerCentSal = salGuardada
            val tantPerCentSuma = 1 + tantPerCentAigua + tantPerCentSal
            val tantPerCentFarinaMassaMare = farinaMassaMareGuardada
            val tantPerCentAiguaMassaMare = aiguaMassaMareGuardada

            val pesReal = pesDesitjat / perdutGuardada

            val farinaNecessariaTotal = Math.round(1 / tantPerCentSuma * pesReal)
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
