package com.example.guess

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_record.*
import kotlinx.android.synthetic.main.content_main.counter

class RecordActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_record)

        val count = intent.getIntExtra("COUNTER",-1)

        counter.setText(count.toString())

        save.setOnClickListener { view ->
            val nick = nickname.text.toString()
            getSharedPreferences("guess", Context.MODE_PRIVATE)
                .edit()
                .putInt("REC_COUNT",count)
                .putString("REC_NICKNAME", nick)
                .apply()

            val intent = Intent()
            intent.putExtra("NICK",nick)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
        }
}
