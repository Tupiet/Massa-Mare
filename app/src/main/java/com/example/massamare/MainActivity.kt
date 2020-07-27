package com.example.massamare

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button: Button = findViewById(R.id.button)
        val input: EditText = findViewById(R.id.textView)
        val output: TextView = findViewById(R.id.textView2)

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

            output.text = "${farinaFinal} grams de farina, ${aiguaFinal} grams d'aigua, ${salNecessariaTotal} grams de sal, ${massaMareNecessariaTotal} grams de massa mare."
        }
    }
}
