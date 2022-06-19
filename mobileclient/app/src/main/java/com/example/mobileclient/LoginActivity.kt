package com.example.mobileclient

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.services.Usuarios
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class LoginActivity : AppCompatActivity() {

//    private val usuarios: Usuarios = Usuarios.instance

//    private val client = OkHttpClient.Builder().build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)



        handleLogin()

//        val txtUsername = findViewById<TextInputLayout>(R.id.txt_username)
//        val txtPassword = findViewById<TextInputLayout>(R.id.txt_password)
//        val btnLogin = findViewById<Button>(R.id.btn_login)
//
//        btnLogin.setOnClickListener {
//            val username = txtUsername.editText?.text.toString()
//            val password = txtPassword.editText?.text.toString()
//
//            val usuario: Usuario? = usuarios.login(username, password)
//
//            if (usuario != null) {
//                val i = Intent(this, MainActivity::class.java)
//                i.putExtra("usuario", usuario)
//                startActivity(i)
//                finish()
//            } else {
//                Toast.makeText(this, "Usuario o Contraseña Incorrectos!", Toast.LENGTH_SHORT).show()
//            }
//        }
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8081/gestion-academica/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun handleLogin() {
        btn_login.setOnClickListener {

            if (txt_username.editText?.text.toString() == "" || txt_password.editText?.text.toString() == "") {
                showToast("Por favor complete los campos!")
                return@setOnClickListener
            }

            val requestBody = JSONObject()
            requestBody.put("cedula", txt_username.editText?.text.toString())
            requestBody.put("clave", txt_password.editText?.text.toString())

            login(requestBody.toString())
        }
    }

    private fun login(requestBody: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(Usuarios::class.java).login(requestBody).execute()
            val usuario = if (call.isSuccessful) JSONObject(call.body().toString()) else JSONObject()

            runOnUiThread {
                if (usuario.length() == 0) {
                    showToast("Error")
                    return@runOnUiThread
                }

                if (usuario.getString("cedula") == "None") {
                    showToast("Usuario o Contraseña Incorrectos!")
                    return@runOnUiThread
                }

                val i = Intent(this@LoginActivity, MainActivity::class.java)
                i.putExtra("usuario", usuario.toString())
                startActivity(i)
                finish()
            }
        }
    }

    private fun showToast(text: String) {
        Toast.makeText(this@LoginActivity, text, Toast.LENGTH_LONG).show()
    }
}