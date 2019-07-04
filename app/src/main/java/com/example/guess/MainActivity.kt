package com.example.guess

import android.app.Activity
import android.app.ProgressDialog.show
import android.content.Context
import android.content.Intent
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
    private val REQUEST_RECODE: Int = 100
    var secretNumber = SecretNumber()
    var answer =secretNumber.secret

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        Log.d("yuri", "Answer : ${answer}")

        fab.setOnClickListener { view ->
            replay()
        }
       counter.setText(secretNumber.count.toString())
        val count = getSharedPreferences("guess", Context.MODE_PRIVATE)
            .getInt("REC_COUNT", -1)
        val nick = getSharedPreferences("guess", Context.MODE_PRIVATE)
            .getString("REC_NICKNAME", null)
        Log.d("yuri", "data  : " + count + "/" + nick)

    }

    private fun replay() {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.replay))
            .setMessage(getString(R.string.sure))
            .setPositiveButton(getString(R.string.yes), { dialog, which ->
                secretNumber.reset()
                counter.setText("0")
            })
            .show()
    }

    override fun onStart() {
        super.onStart()
        Log.d("yuri","onStart" )
    }

    override fun onStop() {
        super.onStop()
        Log.d("yuri","onStop" )

    }

    override fun onPause() {
        super.onPause()
        Log.d("yuri","onPause" )

    }

    /**
     * Called after [.onStop] when the current activity is being
     * re-displayed to the user (the user has navigated back to it).  It will
     * be followed by [.onStart] and then [.onResume].
     *
     *
     * For activities that are using raw [Cursor] objects (instead of
     * creating them through
     * [.managedQuery],
     * this is usually the place
     * where the cursor should be requeried (because you had deactivated it in
     * [.onStop].
     *
     *
     * *Derived classes must call through to the super class's
     * implementation of this method.  If they do not, an exception will be
     * thrown.*
     *
     * @see .onStop
     *
     * @see .onStart
     *
     * @see .onResume
     */
    override fun onRestart() {
        super.onRestart()
        Log.d("yuri","onRestart" )

    }


    override fun onResume() {
        super.onResume()
        Log.d("yuri","onResume" )

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("yuri","onDestroy" )

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
            .setPositiveButton(getString(R.string.ok), {dialog, which ->
                if (guess == 0){
                    val intent = Intent(this, RecordActivity :: class.java)
                    intent.putExtra("COUNTER", secretNumber.count)
//                    startActivity(intent)
                    startActivityForResult(intent,REQUEST_RECODE)
                }
            })
            .show()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    if (requestCode == REQUEST_RECODE){
        if (resultCode == Activity.RESULT_OK){
           val nickname = data?.getStringExtra("NICK")
            Log.d("yuri", "name : " + nickname)
            replay()

        }    }
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
