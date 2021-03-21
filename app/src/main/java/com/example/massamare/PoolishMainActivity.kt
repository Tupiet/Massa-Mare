package com.example.massamare

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class PoolishMainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_poolish_main)

        val button: Button = this.findViewById(R.id.button)
        val config: Button = findViewById(R.id.button3)

        val input: EditText = findViewById(R.id.textView)
        val farinaOutput: TextView = findViewById(R.id.farinaOutput)
        val aiguaOutput: TextView = findViewById(R.id.aiguaOutput)
        val massaMareOutput: TextView = findViewById(R.id.massaMareOutput)
        val salOutput: TextView = findViewById(R.id.salOutput)
        val prefermentFarinaOutput: TextView = findViewById(R.id.prefermentFarinaOutput)
        val prefermentAiguaOutput: TextView = findViewById(R.id.prefermentAiguaOutput)

        val pref = getPreferences(Context.MODE_PRIVATE)
        val pes = pref.getInt("POOLISH_PES", 500)
        textView.setText(pes.toString())

        fun calculate() {
            var poolPesDesitjat = 0
            if (input.text.toString() == "") {
                poolPesDesitjat = 0
                var toast = Toast.makeText(this, "Has d'introduïr un valor correcte!", Toast.LENGTH_SHORT)
                toast.show()

            } else {
                poolPesDesitjat = Integer.parseInt(input.text.toString())
            }


            // region OBTENIM LES DADES DE LA PÀGINA DE CONFIGURACIÓ
            var poolAiguaRebuda = 80F
            var poolSalRebuda = 2F
            var poolMassaMareRebuda = 100F
            var poolPerdutRebut = 17F
            var poolFarinaMassaMareRebuda = 100F
            var poolAiguaMassaMareRebuda = 100F

            var poolAiguaReduida = poolAiguaRebuda / 100
            var poolSalReduida = poolSalRebuda / 100
            var poolMassaMareReduida = poolMassaMareRebuda
            var poolPerdutReduida = 1 - poolPerdutRebut / 100
            var poolFarinaMassaMareReduida = poolFarinaMassaMareRebuda / 100
            var poolAiguaMassaMareReduida = poolAiguaMassaMareRebuda / 100
            val poolIntent = intent.getBooleanExtra("POOLISH_intent", false)

            val editor = pref.edit()
            editor.putInt("POOLISH_PES", poolPesDesitjat)

            if (poolIntent == true) {
                poolAiguaRebuda = intent.getFloatExtra("POOLISH_percentatgeAigua", poolAiguaRebuda)
                poolSalRebuda = intent.getFloatExtra("POOLISH_percentatgeSal", poolSalRebuda)
                poolMassaMareRebuda = intent.getFloatExtra("POOLISH_quantitatPoolish", poolMassaMareRebuda)
                poolPerdutRebut = intent.getFloatExtra("POOLISH_percentatgePerdua", poolPerdutRebut)
                poolFarinaMassaMareRebuda = intent.getFloatExtra("POOLISH_percentatgeFarinaMassaMare", poolFarinaMassaMareRebuda)
                poolAiguaMassaMareRebuda = intent.getFloatExtra("POOLISH_percentatgeAiguaMassaMare", poolAiguaMassaMareRebuda)

                poolAiguaReduida = poolAiguaRebuda / 100
                poolSalReduida = poolSalRebuda / 100
                poolMassaMareReduida = poolMassaMareRebuda
                poolPerdutReduida = 1 - poolPerdutRebut / 100
                poolFarinaMassaMareReduida = poolFarinaMassaMareRebuda / 100
                poolAiguaMassaMareReduida = poolAiguaMassaMareRebuda / 100

                editor.putFloat("POOLISH_AIGUA_GUARDADA", poolAiguaReduida)
                editor.putFloat("POOLISH_SAL_GUARDADA", poolSalReduida)
                editor.putFloat("POOLISH_POOLISH_GUARDADA", poolMassaMareReduida)
                editor.putFloat("POOLISH_PERDUT_GUARDADA", poolPerdutReduida)
                editor.putFloat("POOLISH_FARINA_MASSA_MARE_GUARDADA", poolFarinaMassaMareReduida)
                editor.putFloat("POOLISH_AIGUA_MASSA_MARE_GUARDADA", poolAiguaMassaMareReduida)
            }
            editor.commit()
            // endregion

            val poolAiguaGuardada = pref.getFloat("POOLISH_AIGUA_GUARDADA", poolAiguaReduida)
            val poolSalGuardada = pref.getFloat("POOLISH_SAL_GUARDADA", poolSalReduida)
            val poolMassaMareGuardada = pref.getFloat("POOLISH_POOLISH_GUARDADA", poolMassaMareReduida)
            val poolPerdutGuardada = pref.getFloat("POOLISH_PERDUT_GUARDADA", poolPerdutReduida)
            val poolFarinaMassaMareGuardada = pref.getFloat("POOLISH_FARINA_MASSA_MARE_GUARDADA", poolFarinaMassaMareReduida)
            val poolAiguaMassaMareGuardada = pref.getFloat("POOLISH_AIGUA_MASSA_MARE_GUARDADA", poolAiguaMassaMareReduida)


            val poolTantPerCentMassaMare = poolMassaMareGuardada
            val poolTantPerCentAigua = poolAiguaGuardada
            val poolTantPerCentSal = poolSalGuardada
            val poolTantPerCentSuma = 1 + poolTantPerCentAigua + poolTantPerCentSal
            val poolTantPerCentFarinaMassaMare = poolFarinaMassaMareGuardada
            val poolTantPerCentAiguaMassaMare = poolAiguaMassaMareGuardada

            val poolPesReal = poolPesDesitjat / poolPerdutGuardada

            val poolFarinaNecessariaTotal = Math.round(1 / poolTantPerCentSuma * poolPesReal)
            val poolAiguaNecessariaTotal = Math.round(poolFarinaNecessariaTotal * poolTantPerCentAigua)
            val poolSalNecessariaTotal = Math.round(poolFarinaNecessariaTotal * poolTantPerCentSal)
            val poolMassaMareNecessariaTotal = poolTantPerCentFarinaMassaMare + poolTantPerCentAiguaMassaMare
            val poolUnitari = poolTantPerCentMassaMare /poolMassaMareNecessariaTotal
            val poolFarinaDinsMassaMare = Math.round(poolUnitari * poolTantPerCentFarinaMassaMare)
            val poolAiguaDinsMassaMare = Math.round(poolUnitari * poolTantPerCentAiguaMassaMare)

            val poolFarinaFinal = poolFarinaNecessariaTotal - poolFarinaDinsMassaMare
            val poolAiguaFinal = poolAiguaNecessariaTotal - poolAiguaDinsMassaMare

            farinaOutput.text = poolFarinaFinal.toString()
            aiguaOutput.text = poolAiguaFinal.toString()
            massaMareOutput.text = poolMassaMareGuardada.toInt().toString()
            salOutput.text = poolSalNecessariaTotal.toString()
            prefermentFarinaOutput.text = poolFarinaDinsMassaMare.toString()
            prefermentAiguaOutput.text = poolAiguaDinsMassaMare.toString()

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
            val intent = Intent(this, ConfigurationPoolish::class.java)
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

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(newBase)
        val newOverride = Configuration(newBase?.resources?.configuration)
        newOverride.fontScale = 1.0f
        applyOverrideConfiguration(newOverride)
    }
}
