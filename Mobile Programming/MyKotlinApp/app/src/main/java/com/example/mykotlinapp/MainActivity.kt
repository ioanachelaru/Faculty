package com.example.mykotlinapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import androidx.appcompat.app.AlertDialog

var jsonResult = ""
var postResult = ""

var username_value = "ioana"
var password_value = "parola"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        login.setOnClickListener {
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


//    class makePostRequst(var activity: MainActivity, var username: String, var password: String) : AsyncTask<Void, Void, String>() {
//
//        override fun doInBackground(vararg params: Void?): String {
//            val client = OkHttpClient()
//            val requestBody = MultipartBody.Builder()
//                .setType(MultipartBody.FORM)
//                .addFormDataPart("username", username)
//                .addFormDataPart("password", password)
//                .build()
//            val request = Request.Builder()
//                .url("http://192.168.43.212/postlogin.php")
//                .post(requestBody)
//                .build()
//            val response = client.newCall(request).execute()
//            return response.body()!!.string()
//        }
//
//        override fun onPostExecute(result: String?) {
//            if (result != null) {
//                val obj = JSONObject(result)
//                postResult = obj.getString("message")
//                makeJSONRequst(activity, username, password).execute()
//            }
//            super.onPostExecute(result)
//        }
//
//    }

//    class makeJSONRequst(var activity: MainActivity, var username: String, var password: String) : AsyncTask<Void, Void, String>() {
//
//        override fun doInBackground(vararg params: Void?): String {
//            val JSON = MediaType.parse("application/json; charset=utf-8")
//            val client = OkHttpClient()
//            val requestObject = com.example.vicky.androidui.Model.Request()
//            requestObject.username = username
//            requestObject.password = password
//            val body = RequestBody.create(JSON, Gson().toJson(requestObject))
//            val request = Request.Builder()
//                .url("http://192.168.43.212/login.php")
//                .post(body)
//                .build()
//            val response = client.newCall(request).execute()
//            return response.body()!!.string()
//        }
//
//        override fun onPostExecute(result: String?) {
//            if (result != null) {
//                val obj = JSONObject(result)
//                jsonResult = obj.getString("message")
//            }
//            val dialog = AlertDialog.Builder(activity)
//            val view = activity.layoutInflater.inflate(R.layout.dialog_result, null)
//            dialog.setView(view)
//            view.findViewById<TextView>(R.id.json_result).text = jsonResult
//            view.findViewById<TextView>(R.id.post_result).text = postResult
//            dialog.show()
//            super.onPostExecute(result)
//        }
//    }

}
