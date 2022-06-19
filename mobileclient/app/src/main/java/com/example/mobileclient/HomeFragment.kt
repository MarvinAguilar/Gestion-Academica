package com.example.mobileclient

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.models.Alumno
import com.example.models.Profesor
import com.example.models.Usuario
import kotlinx.android.synthetic.main.fragment_home.view.*
import org.json.JSONObject

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val usuario = JSONObject(arguments?.getSerializable("usuario").toString())

        changeWelcome(view, usuario)

        return view
    }

    @SuppressLint("SetTextI18n")
    private fun changeWelcome(view: View, usuario: JSONObject) {
        when (usuario.getInt("perfil")) {
            1 -> view.tv_welcome.text = "Bievenido/a Administrador"
            2 -> view.tv_welcome.text = "Bievenido/a Matriculador"
            3, 4 -> {
                view.tv_welcome.text = "Bievenido/a ${usuario.getString("nombre")}"
            }
        }
    }
}