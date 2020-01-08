package com.example.list_06

import android.content.Intent
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

var username_value = "ioana"
var password_value = "parola"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }


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

    fun doLogin(view: View) {

        if (username.text.toString().isNotEmpty() && password.text.toString().isNotEmpty()){
            if(username.text.toString().equals(username_value))
                if(password.text.toString().equals(password_value)) {
                    val intent = Intent(this, SecondActivity::class.java)
                    startActivity(intent)
                }
                else{
                    val dlgAlert = AlertDialog.Builder(this)
                    dlgAlert.setTitle("Wrong password")
                    dlgAlert.setMessage("Maybe you misspelled your password. You should try again.")
                    dlgAlert.setPositiveButton("Ok", null)
                    dlgAlert.create().show()
                }
            else{
                val dlgAlert = AlertDialog.Builder(this)
                dlgAlert.setTitle("Inexistent account")
                dlgAlert.setMessage("It seems like you misspelled something. You should try again.")
                dlgAlert.setPositiveButton("Ok", null)
                dlgAlert.create().show()
            }
        }
        else{
            val dlgAlert = AlertDialog.Builder(this)
            dlgAlert.setTitle("Warning")
            dlgAlert.setMessage("You need to fill the blanks in order to log in.")
            dlgAlert.setPositiveButton("Ok", null)
            dlgAlert.create().show()
        }
    }
}
