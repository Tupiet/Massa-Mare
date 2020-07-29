package com.example.massamare

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_configuration.*

class ConfigurationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_configuration)

        val pref = getPreferences(Context.MODE_PRIVATE)

        val percentatgeAigua = pref.getFloat("PERCENTATGE_AIGUA", 0.6F)
        val percentatgeSal = pref.getFloat("PERCENTATGE_SAL", 0.02F)
        val percentatgeMassaMare = pref.getFloat("PERCENTATGE_MASSA_MARE", 0.2F)
        val percentatgeFarinaMassaMare = pref.getFloat("PERCENTATGE_FARINA_MASSA_MARE", 0.5F)
        val percentatgeAiguaMassaMare = pref.getFloat("PERCENTATGE_AIGUA_MASSA_MARE", 0.5F)

        aiguaConfig.setText(percentatgeAigua.toString())
        salConfig.setText(percentatgeSal.toString())
        massaMareConfig.setText(percentatgeMassaMare.toString())
        farinaMassaMareConfig.setText(percentatgeFarinaMassaMare.toString())
        aiguaMassaMareConfig.setText(percentatgeAiguaMassaMare.toString())
    }

    fun onSave(view: View) {
        val pref = getPreferences(Context.MODE_PRIVATE)
        val editor = pref.edit()

        val percentatgeAigua = aiguaConfig.text.toString().toFloat()
        val percentatgeSal = salConfig.text.toString().toFloat()
        val percentatgeMassaMare = massaMareConfig.text.toString().toFloat()
        val percentatgeFarinaMassaMare = farinaMassaMareConfig.text.toString().toFloat()
        val percentatgeAiguaMassaMare = aiguaMassaMareConfig.text.toString().toFloat()

        editor.putFloat("PERCENTATGE_AIGUA", percentatgeAigua)
        editor.putFloat("PERCENTATGE_SAL", percentatgeSal)
        editor.putFloat("PERCENTATGE_MASSA_MARE", percentatgeMassaMare)
        editor.putFloat("PERCENTATGE_FARINA_MASSA_MARE", percentatgeFarinaMassaMare)
        editor.putFloat("PERCENTATGE_AIGUA_MASSA_MARE", percentatgeAiguaMassaMare)
        editor.commit()

        var intent = Intent(this@ConfigurationActivity,MainActivity::class.java)
        intent.putExtra("percentatgeAigua", percentatgeAigua)
        intent.putExtra("percentatgeSal", percentatgeSal)
        intent.putExtra("percentatgeMassaMare", percentatgeMassaMare)
        intent.putExtra("percentatgeFarinaMassaMare", percentatgeFarinaMassaMare)
        intent.putExtra("percentatgeAiguaMassaMare", percentatgeAiguaMassaMare)
        startActivity(intent)

    }


}
