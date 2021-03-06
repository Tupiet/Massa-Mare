package com.example.massamare

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.aleix.tupi_library.TupiLibrary
import kotlinx.android.synthetic.main.activity_configuration.*

class ConfigurationActivity : AppCompatActivity() {

    val tupiLibrary = TupiLibrary()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_configuration)

        val pref = getPreferences(Context.MODE_PRIVATE)

        val percentatgeAigua = pref.getFloat("PERCENTATGE_AIGUA", 60F)
        val percentatgeSal = pref.getFloat("PERCENTATGE_SAL", 2F)
        val percentatgeMassaMare = pref.getFloat("PERCENTATGE_MASSA_MARE", 20F)
        val percentatgePerdua = pref.getFloat("PERCENTATGE_PERDUA", 17F)
        val percentatgeFarinaMassaMare = pref.getFloat("PERCENTATGE_FARINA_MASSA_MARE", 50F)
        val percentatgeAiguaMassaMare = pref.getFloat("PERCENTATGE_AIGUA_MASSA_MARE", 50F)

        aiguaConfig.setText(percentatgeAigua.toInt().toString())
        salConfig.setText(percentatgeSal.toInt().toString())
        massaMareConfig.setText(percentatgeMassaMare.toInt().toString())
        perduaConfig.setText(percentatgePerdua.toInt().toString())
        farinaMassaMareConfig.setText(percentatgeFarinaMassaMare.toInt().toString())
        aiguaMassaMareConfig.setText(percentatgeAiguaMassaMare.toInt().toString())
    }

    fun onSave(view: View) {
        val pref = getPreferences(Context.MODE_PRIVATE)
        val editor = pref.edit()
        // Verifica si realment té algun número escrit o no.
        if (aiguaConfig.text.toString() == "" || salConfig.text.toString() == "" ||
            massaMareConfig.text.toString() == "" || farinaMassaMareConfig.text.toString() == "" ||
            aiguaMassaMareConfig.text.toString() == "" || perduaConfig.text.toString() == "") {
            tupiLibrary.toast(this, "Has d'introduïr un valor correcte!")
        } else {
            val percentatgeAigua = aiguaConfig.text.toString().toFloat()
            val percentatgeSal = salConfig.text.toString().toFloat()
            val percentatgeMassaMare = massaMareConfig.text.toString().toFloat()
            val percentatgePerdua = perduaConfig.text.toString().toFloat()
            val percentatgeFarinaMassaMare = farinaMassaMareConfig.text.toString().toFloat()
            val percentatgeAiguaMassaMare = aiguaMassaMareConfig.text.toString().toFloat()

            editor.putFloat("PERCENTATGE_AIGUA", percentatgeAigua)
            editor.putFloat("PERCENTATGE_SAL", percentatgeSal)
            editor.putFloat("PERCENTATGE_MASSA_MARE", percentatgeMassaMare)
            editor.putFloat("PERCENTATGE_PERDUA", percentatgePerdua)
            editor.putFloat("PERCENTATGE_FARINA_MASSA_MARE", percentatgeFarinaMassaMare)
            editor.putFloat("PERCENTATGE_AIGUA_MASSA_MARE", percentatgeAiguaMassaMare)
            editor.commit()

            var intent = Intent(this,MainActivity::class.java)
            intent.putExtra("percentatgeAigua", percentatgeAigua)
            intent.putExtra("percentatgeSal", percentatgeSal)
            intent.putExtra("percentatgeMassaMare", percentatgeMassaMare)
            intent.putExtra("percentatgePerdua", percentatgePerdua)
            intent.putExtra("percentatgeFarinaMassaMare", percentatgeFarinaMassaMare)
            intent.putExtra("percentatgeAiguaMassaMare", percentatgeAiguaMassaMare)
            intent.putExtra("intent", true)
            tupiLibrary.toast(this, "Guardat!")
            startActivity(intent)
            finish()
        }

    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(newBase)
        val newOverride = Configuration(newBase?.resources?.configuration)
        newOverride.fontScale = 1.0f
        applyOverrideConfiguration(newOverride)
    }


}
