package com.example.massamare

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.aleix.tupi_library.TupiLibrary
import kotlinx.android.synthetic.main.activity_configuration.aiguaConfig
import kotlinx.android.synthetic.main.activity_configuration.aiguaMassaMareConfig
import kotlinx.android.synthetic.main.activity_configuration.farinaMassaMareConfig
import kotlinx.android.synthetic.main.activity_configuration.perduaConfig
import kotlinx.android.synthetic.main.activity_configuration.salConfig
import kotlinx.android.synthetic.main.activity_configuration_poolish.*

class ConfigurationPoolish : AppCompatActivity() {

    val tupiLibrary = TupiLibrary()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_configuration_poolish)

        val pref = getPreferences(Context.MODE_PRIVATE)

        val poolPercentatgeAigua = pref.getFloat("POOLISH_PERCENTATGE_AIGUA", 80F)
        val poolPercentatgeSal = pref.getFloat("POOLISH_PERCENTATGE_SAL", 2F)
        val poolQuantitatPoolish = pref.getFloat("POOLISH_QUANTITAT_POOLISH", 100F)
        val poolPercentatgePerdua = pref.getFloat("POOLISH_PERCENTATGE_PERDUA", 17F)

        aiguaConfig.setText(poolPercentatgeAigua.toInt().toString())
        salConfig.setText(poolPercentatgeSal.toInt().toString())
        prefermentConfig.setText(poolQuantitatPoolish.toInt().toString())
        perduaConfig.setText(poolPercentatgePerdua.toInt().toString())
    }

    fun onSave(view: View) {
        val pref = getPreferences(Context.MODE_PRIVATE)
        val editor = pref.edit()
        // Verifica si realment té algun número escrit o no.
        if (aiguaConfig.text.toString() == "" || salConfig.text.toString() == "" ||
            prefermentConfig.text.toString() == "" || perduaConfig.text.toString() == "") {
            tupiLibrary.toast(this, "Has d'introduïr un valor correcte!")
        } else {
            val poolPercentatgeAigua = aiguaConfig.text.toString().toFloat()
            val poolPercentatgeSal = salConfig.text.toString().toFloat()
            val poolQuantitatPoolish = prefermentConfig.text.toString().toFloat()
            val poolPercentatgePerdua = perduaConfig.text.toString().toFloat()

            editor.putFloat("POOLISH_PERCENTATGE_AIGUA", poolPercentatgeAigua)
            editor.putFloat("POOLISH_PERCENTATGE_SAL", poolPercentatgeSal)
            editor.putFloat("POOLISH_QUANTITAT_POOLISH", poolQuantitatPoolish)
            editor.putFloat("POOLISH_PERCENTATGE_PERDUA", poolPercentatgePerdua)
            editor.commit()

            var intent = Intent(this,PoolishMainActivity::class.java)
            intent.putExtra("POOLISH_percentatgeAigua", poolPercentatgeAigua)
            intent.putExtra("POOLISH_percentatgeSal", poolPercentatgeSal)
            intent.putExtra("POOLISH_quantitatPoolish", poolQuantitatPoolish)
            intent.putExtra("POOLISH_percentatgePerdua", poolPercentatgePerdua)
            intent.putExtra("POOLISH_intent", true)
            tupiLibrary.toast(this, "Guardat!")
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

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(newBase)
        val newOverride = Configuration(newBase?.resources?.configuration)
        newOverride.fontScale = 1.0f
        applyOverrideConfiguration(newOverride)
    }
}
