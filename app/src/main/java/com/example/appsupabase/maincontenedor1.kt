package com.example.appsupabase

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.appsupabase.models.Materia
import com.example.appsupabase.services.SupabaseErrorHandler
import com.example.appsupabase.services.SupabaseManager
import io.github.jan.supabase.exceptions.RestException
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Order
import kotlinx.coroutines.launch

class maincontenedor1 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_maincontenedor1)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val listaCategorias = findViewById<AutoCompleteTextView>(R.id.listaCategorias)
        val listaMaterias = findViewById<AutoCompleteTextView>(R.id.listaMaterias)
        val lvMaterias = findViewById<ListView>(R.id.lvMaterias)

        listaCategorias.setOnItemClickListener { _, _, position, _ ->
            val lstMaterias = ArrayList<String>()
            listaMaterias.setText("")

            lifecycleScope.launch {
                try {
                    val listadoMaterias = ArrayList(
                        SupabaseManager.client
                            .from("materias")
                            .select {
                                filter {
                                    eq("nivel", position + 1)
                                }
                                order("nombre", Order.ASCENDING)
                            }
                            .decodeList<Materia>()
                    )
                    for (materia in listadoMaterias) {
                        lstMaterias.add(materia.nombre ?: "")
                    }
                } catch (e: RestException) {
                    SupabaseErrorHandler.show(this@maincontenedor1, e)
                    lstMaterias.clear()
                } finally {
                    val adapter = ArrayAdapter(
                        this@maincontenedor1,
                        android.R.layout.simple_spinner_dropdown_item,
                        lstMaterias
                    )
                    listaMaterias.setAdapter(adapter)

                    val adapterLv = ArrayAdapter(
                        this@maincontenedor1,
                        android.R.layout.simple_list_item_1,
                        lstMaterias
                    )
                    lvMaterias.adapter = adapterLv
                }
            }
        }
    }
}