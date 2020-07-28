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
        var pes = pref.getInt("PES", 0)
        textView.setText(pes.toString())

        button.setOnClickListener {
            val pesDesitjat = Integer.parseInt(input.text.toString())
            val tantPerCentMassaMare = 0.2
            val tantPerCentAigua = 0.6
            val tantPerCentSal = 0.02
            val tantPerCentSuma = 1 + tantPerCentAigua + tantPerCentSal

            val farinaNecessariaTotal = Math.round(1 / tantPerCentSuma * pesDesitjat)
            val aiguaNecessariaTotal = Math.round(farinaNecessariaTotal * tantPerCentAigua)
            val salNecessariaTotal = Math.round(farinaNecessariaTotal * tantPerCentSal)
            val massaMareNecessariaTotal = Math.round(farinaNecessariaTotal * tantPerCentMassaMare)
            val farinaDinsMassaMare = Math.round(massaMareNecessariaTotal * 0.5)
            val aiguaDinsMassaMare = Math.round(massaMareNecessariaTotal * 0.5)

            val farinaFinal = farinaNecessariaTotal - farinaDinsMassaMare
            val aiguaFinal = aiguaNecessariaTotal - aiguaDinsMassaMare

            farinaOutput.text = farinaFinal.toString()
            aiguaOutput.text = aiguaFinal.toString()
            massaMareOutput.text = massaMareNecessariaTotal.toString()
            salOutput.text = salNecessariaTotal.toString()

            onSave()
        }

        config.setOnClickListener {
            val intent = Intent(this, ConfigurationActivity::class.java).apply {
            }

            startActivity(intent)
        }
    }

    fun onSave() {
        val pref = getPreferences(Context.MODE_PRIVATE)
        val editor = pref.edit()
        editor.putInt("PES", textView.text.toString().toInt())
        editor.commit()
    }
}
