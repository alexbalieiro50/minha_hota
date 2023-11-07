package com.francisco.minhahorta

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.francisco.minhahorta.databinding.ActivityMainBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import java.util.*

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private lateinit var database: FirebaseDatabase
    private lateinit var myReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar!!.hide()

        window.statusBarColor = Color.parseColor("#279EFF")

        binding.btnlerTemperatura.setOnClickListener{readData()}
        
        binding.btnBomba.setOnCheckedChangeListener { compoundButton, onSwitch ->
            if (onSwitch)
                //binding.btnBomba.setBackgroundColor(Color.BLUE)
                ligar()
            else
                deligar()
                //binding.btnBomba.setBackgroundColor(Color.RED)

        }

    }

    private fun readData(){
        myReference = FirebaseDatabase.getInstance().getReference("Temperatura")
        myReference.child("graus").get().addOnSuccessListener {
            if(it.exists()){
                val graus: Float = it.value.toString().toFloat()
                Toast.makeText(this,"Temperatura lida com Sucesso",Toast.LENGTH_SHORT).show()
                binding.txtTemperatura.setText(graus.toString())
            }else{
                Toast.makeText(this,"Sensor/tempeartura não existe",Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener{
            Toast.makeText(this,"FALHA",Toast.LENGTH_SHORT).show()
        }
    }

    private fun ligar(){
        var ligar: Int = 1

        myReference = FirebaseDatabase.getInstance().getReference("Temperatura")
        myReference.child("bomba").setValue(ligar).addOnSuccessListener {
            Toast.makeText(this,"Bomba Ligada!",Toast.LENGTH_SHORT).show()
        }.addOnFailureListener{
            Toast.makeText(this,"Falha ao inserir dados!",Toast.LENGTH_SHORT).show()
        }
        myReference.child("insert").get().addOnSuccessListener {
            if (it.exists()) {
                val resultado: Int = it.value.toString().toInt()
                binding.btnBomba.setText(resultado.toString())
            }
        }
    }

    private fun deligar(){
        var desligar: Int = 0

        myReference = FirebaseDatabase.getInstance().getReference("Temperatura")
        myReference.child("bomba").setValue(desligar).addOnSuccessListener {
            Toast.makeText(this,"Bomba Dsligada!",Toast.LENGTH_SHORT).show()
        }.addOnFailureListener{
            Toast.makeText(this,"Falha ao inserir dados!",Toast.LENGTH_SHORT).show()
        }
        myReference.child("insert").get().addOnSuccessListener {
            if (it.exists()) {
                val resultado: Int = it.value.toString().toInt()
                binding.btnBomba.setText(resultado.toString())
            }
        }
    }

}

