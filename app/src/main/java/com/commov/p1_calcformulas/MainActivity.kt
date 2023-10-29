package com.commov.p1_calcformulas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import com.commov.p1_calcformulas.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    var pi = 3.1415926535

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Se conecta el bindingView
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Declaración de spinner con opciones
        val spinner: Spinner = findViewById(R.id.spinner)
        val options = resources.getStringArray(R.array.spinner_options)
        val adapter = ArrayAdapter (this, R.layout.text_spinner_layout, options)
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_layout)
        spinner.adapter = adapter

        //Declaración de elementos que cambian conforme al spinner
        val formula: ImageView = findViewById(R.id.formula)

        //Función para determinar el item seleccionado en el spinner
        spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, index: Int, id: Long) {
                //Switch para determinar el funcionamiento conforme a la selección del usuario
                if(index == 0){ //Volúmen de un cilindro (radio y altura)
                    formula.setImageResource(R.drawable.formula1)
                }else if(index == 1){ //Volúmen de un cono (radio y altura)
                    formula.setImageResource(R.drawable.formula2)
                }else{ //Volúmen de una pirámide (lado, lado y altura)
                    formula.setImageResource(R.drawable.formula3)
                }
                val selectedItem = options[index]
                Toast.makeText(this@MainActivity, "$selectedItem", Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }
    }
}