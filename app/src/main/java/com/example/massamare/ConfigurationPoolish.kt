package com.example.massamare

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_configuration.aiguaConfig
import kotlinx.android.synthetic.main.activity_configuration.aiguaMassaMareConfig
import kotlinx.android.synthetic.main.activity_configuration.farinaMassaMareConfig
import kotlinx.android.synthetic.main.activity_configuration.perduaConfig
import kotlinx.android.synthetic.main.activity_configuration.salConfig
import kotlinx.android.synthetic.main.activity_configuration_poolish.*

class ConfigurationPoolish : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_configuration_poolish)

        val pref = getPreferences(Context.MODE_PRIVATE)

        val poolPercentatgeAigua = pref.getFloat("POOLISH_PERCENTATGE_AIGUA", 80F)
        val poolPercentatgeSal = pref.getFloat("POOLISH_PERCENTATGE_SAL", 2F)
        val poolQuantitatPoolish = pref.getFloat("POOLISH_QUANTITAT_POOLISH", 100F)
        val poolPercentatgePerdua = pref.getFloat("POOLISH_PERCENTATGE_PERDUA", 17F)
        val poolPercentatgeFarinaMassaMare = pref.getFloat("POOLISH_PERCENTATGE_FARINA_MASSA_MARE", 50F)
        val poolPercentatgeAiguaMassaMare = pref.getFloat("POOLISH_PERCENTATGE_AIGUA_MASSA_MARE", 50F)

        aiguaConfig.setText(poolPercentatgeAigua.toInt().toString())
        salConfig.setText(poolPercentatgeSal.toInt().toString())
        prefermentConfig.setText(poolQuantitatPoolish.toInt().toString())
        perduaConfig.setText(poolPercentatgePerdua.toInt().toString())
        farinaMassaMareConfig.setText(poolPercentatgeFarinaMassaMare.toInt().toString())
        aiguaMassaMareConfig.setText(poolPercentatgeAiguaMassaMare.toInt().toString())
    }

    fun onSave(view: View) {
        val pref = getPreferences(Context.MODE_PRIVATE)
        val editor = pref.edit()
        // Verifica si realment té algun número escrit o no.
        if (aiguaConfig.text.toString() == "" || salConfig.text.toString() == "" ||
            prefermentConfig.text.toString() == "" || farinaMassaMareConfig.text.toString() == "" ||
            aiguaMassaMareConfig.text.toString() == "" || perduaConfig.text.toString() == "") {
            val toast = Toast.makeText(this, "Has d'introduïr un valor correcte!", Toast.LENGTH_SHORT)
            toast.show()
        } else {
            val poolPercentatgeAigua = aiguaConfig.text.toString().toFloat()
            val poolPercentatgeSal = salConfig.text.toString().toFloat()
            val poolQuantitatPoolish = prefermentConfig.text.toString().toFloat()
            val poolPercentatgePerdua = perduaConfig.text.toString().toFloat()
            val poolPercentatgeFarinaMassaMare = farinaMassaMareConfig.text.toString().toFloat()
            val poolPercentatgeAiguaMassaMare = aiguaMassaMareConfig.text.toString().toFloat()

            editor.putFloat("POOLISH_PERCENTATGE_AIGUA", poolPercentatgeAigua)
            editor.putFloat("POOLISH_PERCENTATGE_SAL", poolPercentatgeSal)
            editor.putFloat("POOLISH_QUANTITAT_POOLISH", poolQuantitatPoolish)
            editor.putFloat("POOLISH_PERCENTATGE_PERDUA", poolPercentatgePerdua)
            editor.putFloat("POOLISH_PERCENTATGE_FARINA_MASSA_MARE", poolPercentatgeFarinaMassaMare)
            editor.putFloat("POOLISH_PERCENTATGE_AIGUA_MASSA_MARE", poolPercentatgeAiguaMassaMare)
            editor.commit()

            var intent = Intent(this,PoolishMainActivity::class.java)
            intent.putExtra("POOLISH_percentatgeAigua", poolPercentatgeAigua)
            intent.putExtra("POOLISH_percentatgeSal", poolPercentatgeSal)
            intent.putExtra("POOLISH_quantitatPoolish", poolQuantitatPoolish)
            intent.putExtra("POOLISH_percentatgePerdua", poolPercentatgePerdua)
            intent.putExtra("POOLISH_percentatgeFarinaMassaMare", poolPercentatgeFarinaMassaMare)
            intent.putExtra("POOLISH_percentatgeAiguaMassaMare", poolPercentatgeAiguaMassaMare)
            intent.putExtra("POOLISH_intent", true)
            var toast = Toast.makeText(this, "Guardat!", Toast.LENGTH_SHORT)
            toast.show()
            startActivity(intent)
            finish()
        }

    }

    fun setPoolishValue(view: View) {
        farinaMassaMareConfig.setText("50")
        aiguaMassaMareConfig.setText("50")
        onSave(button2)

    }
    fun setBaseValue(view: View) {
        farinaMassaMareConfig.setText("66")
        aiguaMassaMareConfig.setText("33")
        onSave(button2)
    }
}
