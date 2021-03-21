package com.example.massamare

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import com.aleix.tupi_library.TupiLibrary
import kotlinx.android.synthetic.main.activity_main_preferment.*

class MainPreferment : AppCompatActivity() {

    val tupiLibrary = TupiLibrary()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_preferment)

        val pref = getPreferences(Context.MODE_PRIVATE)
        val editor = pref.edit()

        poolishButton.setOnClickListener {
            val poolIntent = pref.getBoolean("POOLISH_INTENT", false)
            val poolWater = pref.getFloat("POOLISH_WATER", 100F)
            var intent = Intent(this, PoolishMainActivity::class.java)

            if (poolIntent == true) {
                intent.putExtra("POOLISH_UPDATED", true)
                intent.putExtra("POOLISH_WATER", poolWater)
                editor.putBoolean("POOLISH_INTENT", false)
            }

            startActivity(intent)
            finish()
        }

        poolishBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                tupiLibrary.toast(this@MainPreferment, "Percentatge aigua al Poolish: " + poolishBar.progress.toString())


                editor.putFloat("POOLISH_WATER", poolishBar.progress.toFloat())
                editor.putBoolean("POOLISH_INTENT", true)
                editor.commit()
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) { }
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) { }
        })

        editButton.setOnClickListener {
            showHide(poolishBar)
        }


    }

    // Permet posar en invisible o visible el view que vulguem
    fun showHide(view: View) {
        if (view.visibility == View.VISIBLE) {
            view.visibility = View.INVISIBLE
        } else {
            view.visibility = View.VISIBLE
        }
    }


}
