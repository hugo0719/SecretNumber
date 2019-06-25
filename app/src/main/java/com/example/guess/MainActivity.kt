package com.example.guess

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity;
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {
    var secretNumber = SecretNumber()
    var answer =secretNumber.secret

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        Log.d("yuri", "Answer : ${answer}")

        fab.setOnClickListener { view ->
            AlertDialog.Builder(this)
                .setTitle(getString(R.string.replay))
                .setMessage(getString(R.string.sure))
                .setPositiveButton(getString(R.string.yes), { dialog, which ->
                    secretNumber.reset()
                    counter.setText("0")
                })
                .show()
        }
       counter.setText(secretNumber.count.toString())
    }


    fun check(view: View){
        var number = ed_number.text.toString().toInt()
        var guess = secretNumber.validate(number)
        counter.setText(secretNumber.count.toString())
        ed_number.setText("")

      val message= when{
          guess < 0 -> getString(R.string.bigger)
          guess > 0 -> getString(R.string.smaller)
          guess == 0 && secretNumber.count <= 2 -> getString(R.string.excellent)+ answer
          else -> getString(R.string.you_got_it) + answer

      }

//        Toast.makeText(this,message , Toast.LENGTH_LONG).show()
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.result))
            .setMessage(message)
            .setPositiveButton(getString(R.string.ok), null)
            .show()

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
