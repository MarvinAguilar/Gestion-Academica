package com.example.mobileclient

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.models.Alumno
import com.example.models.Profesor
import com.example.models.Usuario
import com.google.android.material.navigation.NavigationView
import org.json.JSONObject

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView
    private lateinit var toolbar: Toolbar
    private lateinit var toggle: ActionBarDrawerToggle

    private lateinit var bundle: Bundle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        drawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById(R.id.nav_view)
        toolbar = findViewById(R.id.toolbar)

        //Setup Toolbar
        setSupportActionBar(toolbar)

        toggle = setUpDrawerToggle()
        toggle.isDrawerIndicatorEnabled = true
        drawerLayout.addDrawerListener(toggle)

        navView.setNavigationItemSelectedListener(this)

        //User Data
        bundle = intent.extras!!
        val usuario = JSONObject(bundle.getString("usuario").toString())

//        val usuario = castUsuario(JSONObject(bundle.getString("usuario").toString()))

        updateNavContentByProfile(usuario)

        changeFragment(HomeFragment())
        title = "Inicio"
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        toggle.syncState()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        toggle.onConfigurationChanged(newConfig)
    }

    private fun setUpDrawerToggle(): ActionBarDrawerToggle {
        return ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        selectItemNav(item)
        return true
    }

    private fun changeFragment(fragment: Fragment) {
        fragment.arguments = bundle
        supportFragmentManager
            .beginTransaction()
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .replace(R.id.content_fragment, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun selectItemNav(item: MenuItem) {
        when (item.itemId) {
            R.id.nav_home -> {
                changeFragment(HomeFragment())
            }
            R.id.nav_carreras -> {
                changeFragment(CarrerasFragment())
            }
            R.id.nav_cursos -> {
                changeFragment(CursosFragment())
            }
            R.id.nav_profesores -> {
                changeFragment(ProfesoresFragment())
            }
            R.id.nav_alumnos -> {
                changeFragment(AlumnosFragment())
            }
            R.id.nav_ciclos -> {
                changeFragment(CiclosFragment())
            }
            R.id.nav_seguridad -> {
                changeFragment(SeguridadFragment())
            }
            R.id.nav_ofertaAcademica -> {
                changeFragment(OfertaAcademicaFragment())
            }
            R.id.nav_matricula -> {
                changeFragment(MatriculaFragment())
            }
            R.id.nav_registroNotas -> {
                changeFragment(RegistroNotasFragment())
            }
            R.id.nav_historialAcademico -> {
                changeFragment(HistorialAcademicoFragment())
            }
            R.id.nav_logout -> {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
        title = item.title
        drawerLayout.closeDrawers()
    }

//    private fun updateNavContentByProfile(usuario: Usuario) {
//        updateNavHeader(usuario)
//        updateNavMenu(usuario)
//    }
//
//    @SuppressLint("SetTextI18n")
//    private fun updateNavHeader(usuario: Usuario) {
//        val headerView = navView.getHeaderView(0)
//        val tvUsername = headerView.findViewById<TextView>(R.id.tv_nav_header_title)
//        val tvUserProfile = headerView.findViewById<TextView>(R.id.tv_nav_header_subtitle)
//        when (usuario.perfil) {
//            1 -> {
//                tvUsername.text = "Usuario: ${usuario.cedula}"
//                tvUserProfile.text = "Perfil: Administrador"
//            }
//            2 -> {
//                tvUsername.text = "Usuario: ${usuario.cedula}"
//                tvUserProfile.text = "Perfil: Matriculador"
//            }
//            3 -> {
//                val profesor = usuario as Profesor
//                tvUsername.text = "Usuario: ${profesor.nombre}"
//                tvUserProfile.text = "Perfil: Profesor"
//            }
//            4 -> {
//                val alumno = usuario as Alumno
//                tvUsername.text = "Usuario: ${alumno.nombre}"
//                tvUserProfile.text = "Perfil: Alumno"
//            }
//        }
//    }
//
//    private fun updateNavMenu(usuario: Usuario) {
//        val navMenu = navView.menu
//
//        when (usuario.perfil) {
//            1 -> {
//                navMenu.findItem(R.id.nav_historialAcademico).isVisible = false
//                navMenu.findItem(R.id.nav_registroNotas).isVisible = false
//            }
//            2 -> {
//                navMenu.findItem(R.id.menu_mantenimiento).isVisible = false
//                navMenu.findItem(R.id.nav_ofertaAcademica).isVisible = false
//                navMenu.findItem(R.id.nav_registroNotas).isVisible = false
//                navMenu.findItem(R.id.nav_historialAcademico).isVisible = false
//            }
//            3 -> {
//                navMenu.findItem(R.id.menu_mantenimiento).isVisible = false
//                navMenu.findItem(R.id.nav_ofertaAcademica).isVisible = false
//                navMenu.findItem(R.id.nav_matricula).isVisible = false
//                navMenu.findItem(R.id.nav_historialAcademico).isVisible = false
//            }
//            4 -> {
//                navMenu.findItem(R.id.menu_mantenimiento).isVisible = false
//                navMenu.findItem(R.id.nav_ofertaAcademica).isVisible = false
//                navMenu.findItem(R.id.nav_matricula).isVisible = false
//                navMenu.findItem(R.id.nav_registroNotas).isVisible = false
//            }
//        }
//    }
//
//    private fun castUsuario(usuario: JSONObject): Usuario {
//        when (usuario.getInt("perfil")) {
//            3 -> return Profesor(
//                usuario.getString("cedula"),
//                usuario.getString("nombre"),
//                usuario.getString("telefono"),
//                usuario.getString("email"),
//            )
//            4 ->
//                return Alumno(
//                    usuario.getString("cedula"),
//                    usuario.getString("nombre"),
//                    usuario.getString("telefono"),
//                    usuario.getString("email"),
//                    usuario.getString("fechaNacimiento"),
//                    ""
//                )
//            else ->
//                return Usuario(
//                    usuario.getString("cedula"),
//                    "",
//                    usuario.getInt("perfil"),
//                )
//        }
//    }

    private fun updateNavContentByProfile(usuario: JSONObject) {
        updateNavHeader(usuario)
        updateNavMenu(usuario)
    }

    @SuppressLint("SetTextI18n")
    private fun updateNavHeader(usuario: JSONObject) {
        val headerView = navView.getHeaderView(0)
        val tvUsername = headerView.findViewById<TextView>(R.id.tv_nav_header_title)
        val tvUserProfile = headerView.findViewById<TextView>(R.id.tv_nav_header_subtitle)
        when (usuario.getInt("perfil")) {
            1 -> {
                tvUsername.text = "Usuario: ${usuario.getString("cedula")}"
                tvUserProfile.text = "Perfil: Administrador"
            }
            2 -> {
                tvUsername.text = "Usuario: ${usuario.getString("cedula")}"
                tvUserProfile.text = "Perfil: Matriculador"
            }
            3 -> {
                tvUsername.text = "Usuario: ${usuario.getString("nombre")}"
                tvUserProfile.text = "Perfil: Profesor"
            }
            4 -> {
                tvUsername.text = "Usuario: ${usuario.getString("nombre")}"
                tvUserProfile.text = "Perfil: Alumno"
            }
        }
    }

    private fun updateNavMenu(usuario: JSONObject) {
        val navMenu = navView.menu

        when (usuario.getInt("perfil")) {
            1 -> {
                navMenu.findItem(R.id.nav_historialAcademico).isVisible = false
                navMenu.findItem(R.id.nav_registroNotas).isVisible = false
            }
            2 -> {
                navMenu.findItem(R.id.menu_mantenimiento).isVisible = false
                navMenu.findItem(R.id.nav_ofertaAcademica).isVisible = false
                navMenu.findItem(R.id.nav_registroNotas).isVisible = false
                navMenu.findItem(R.id.nav_historialAcademico).isVisible = false
            }
            3 -> {
                navMenu.findItem(R.id.menu_mantenimiento).isVisible = false
                navMenu.findItem(R.id.nav_ofertaAcademica).isVisible = false
                navMenu.findItem(R.id.nav_matricula).isVisible = false
                navMenu.findItem(R.id.nav_historialAcademico).isVisible = false
            }
            4 -> {
                navMenu.findItem(R.id.menu_mantenimiento).isVisible = false
                navMenu.findItem(R.id.nav_ofertaAcademica).isVisible = false
                navMenu.findItem(R.id.nav_matricula).isVisible = false
                navMenu.findItem(R.id.nav_registroNotas).isVisible = false
            }
        }
    }
}