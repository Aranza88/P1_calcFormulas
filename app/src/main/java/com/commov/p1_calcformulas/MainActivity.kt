package com.commov.p1_calcformulas

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.commov.p1_calcformulas.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val pi = 3.1415926535

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Se conecta el bindingView
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var result: Double
        var text: Array<String>

        //Spinner con opciones
        val options = resources.getStringArray(R.array.spinner_options)
        val adapter = ArrayAdapter (this, R.layout.text_spinner_layout, options)
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_layout)
        binding.spinner.adapter = adapter

        //Función para determinar el item seleccionado en el spinner
        binding.spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            @SuppressLint("ResourceAsColor")
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, index: Int, id: Long) {
                //Reset de datos
                binding.data1.text = null
                binding.data2.text = null
                binding.data3.text = null
                binding.result.text = null
                binding.btnVerify.isEnabled = false

                //Switch para determinar el funcionamiento conforme a la selección del usuario
                when (index) {
                    0 -> { //Volúmen de un cilindro (radio y altura)
                        //Se definen los datos que cambian conforme al spinner
                        binding.formula.setImageResource(R.drawable.formula1)
                        text = resources.getStringArray(R.array.cylinder_text)
                        binding.data1.hint = text[0]
                        binding.data2.hint = text[1]
                        binding.data3.visibility = View.GONE

                        //Se valida si el botón debe de estar activado o no
                        enableButton(2)

                        //Se establece el funcionamiento del botón
                        binding.btnVerify.setOnClickListener {
                            if(verifyText(2)){
                                val radius = binding.data1.text.toString().toDouble()
                                val height = binding.data2.text.toString().toDouble()
                                if((radius <= 0) or (height <= 0)){ //Se verifica que los valores no sean menores o iguales a cero
                                    Toast.makeText(this@MainActivity, text[2], Toast.LENGTH_LONG).show()
                                }else{
                                    result = cylinderVolume(radius, height)
                                    binding.result.text = resources.getString(R.string.result, result)
                                }
                            }
                        }
                    }
                    1 -> { //Volúmen de un cono (radio y altura)
                        //Se definen los datos que cambian conforme al spinner
                        binding.formula.setImageResource(R.drawable.formula2)
                        text = resources.getStringArray(R.array.cone_text)
                        binding.data1.hint = text[0]
                        binding.data2.hint = text[1]
                        binding.data3.visibility = View.GONE

                        //Se valida si el botón debe de estar activado o no
                        enableButton(2)

                        //Se establece el funcionamiento del botón
                        binding.btnVerify.setOnClickListener {
                            if(verifyText(2)){
                                val radius = binding.data1.text.toString().toDouble()
                                val height = binding.data2.text.toString().toDouble()
                                if((radius <= 0) or (height <= 0)){ //Se verifica que los valores no sean menores o iguales a cero
                                    Toast.makeText(this@MainActivity, text[2], Toast.LENGTH_LONG).show()
                                }else{
                                    result = coneVolume(radius, height)
                                    binding.result.text = resources.getString(R.string.result, result)
                                }
                            }
                        }
                    }
                    else -> { //Volúmen de una pirámide (lado, lado y altura)
                        //Se definen los datos que cambian conforme al spinner
                        binding.formula.setImageResource(R.drawable.formula3)
                        text = resources.getStringArray(R.array.pyramid_text)
                        binding.data1.hint = text[0]
                        binding.data2.hint = text[1]
                        binding.data3.visibility = View.VISIBLE
                        binding.data3.hint = text[2]

                        //Se valida si el botón debe de estar activado o no
                        enableButton(3)

                        //Se establece el funcionamiento del botón
                        binding.btnVerify.setOnClickListener {
                            if(verifyText(3)){
                                val a = binding.data1.text.toString().toDouble()
                                val b = binding.data2.text.toString().toDouble()
                                val height = binding.data3.text.toString().toDouble()
                                if((a <= 0) or (b <= 0) or (height <= 0)){ //Se verifica que los valores no sean menores o iguales a cero
                                    Toast.makeText(this@MainActivity, text[3], Toast.LENGTH_LONG).show()
                                }else{
                                    result = pyramidVolume(a, b, height)
                                    binding.result.text = resources.getString(R.string.result, result)
                                }
                            }
                        }
                    }
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }
    }

    //Verificación de cajas de texto
    fun verifyText(numText: Int): Boolean {
        if(numText == 2)
            return (binding.data1.text.isNotEmpty() && binding.data2.text.isNotEmpty())
        if(numText == 3)
            return (binding.data1.text.isNotEmpty() && binding.data2.text.isNotEmpty() && binding.data3.text.isNotEmpty())
        return false
    }

    //Funciones de fórmulas
    fun cylinderVolume(radius: Double, height: Double): Double = pi * radius * radius * height
    fun coneVolume(radius: Double, height: Double): Double = (pi * radius * radius * height) / 3
    fun pyramidVolume(a: Double, b: Double, height: Double): Double = (a * b * height) / 3

    //Función para habilitar el botón
    fun enableButton(numText: Int){
        binding.data1.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {
                binding.btnVerify.isEnabled = verifyText(numText)
            }
        })
        if(numText >= 2){
            binding.data2.addTextChangedListener(object: TextWatcher{
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun afterTextChanged(p0: Editable?) {
                    binding.btnVerify.isEnabled = verifyText(numText)

                }
            })
        }
        if(numText >= 3){
            binding.data3.addTextChangedListener(object: TextWatcher{
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun afterTextChanged(p0: Editable?) {
                    binding.btnVerify.isEnabled = verifyText(numText)

                }
            })
        }
    }
}