package com.example.myfirstfirebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.firebase.database.*
import java.lang.StringBuilder

class MainActivity : AppCompatActivity() {

    val database = FirebaseDatabase.getInstance();
    //table name below
    val myRef = database.getReference("Student");

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnAdd: Button = findViewById(R.id.btnAdd)
        btnAdd.setOnClickListener(){


            val inputId:String = findViewById<TextView>(R.id.inputId).text.toString()
            val inputName:String = findViewById<TextView>(R.id.inputName).text.toString()
            val inputProgramme:String = findViewById<TextView>(R.id.inputProgramme).text.toString()

            //first child para = primary key
            myRef.child(inputId).child("Name").setValue(inputName)
            myRef.child(inputId).child("Programme").setValue(inputProgramme)
        }

        //ValueEventListener is abstract class
        val getData= object:ValueEventListener{
            override fun onDataChange(p0: DataSnapshot) {
                //can use recycler view to display
                var sb = StringBuilder()
                var i=1
                sb.append("List of students in the firebase database \n")
                for(student in p0.children){
                    var name = student.child("Name").getValue()
                    sb.append("${i}. ${name} \n")
                    i++
                }
                val tvResult = findViewById<TextView>(R.id.tvResult)
                tvResult.setText(sb)
            }

            override fun onCancelled(p0: DatabaseError) {
            }

        }
        val btnGet: Button = findViewById(R.id.btnGet)
        btnGet.setOnClickListener() {
            myRef.addValueEventListener(getData)
            myRef.addListenerForSingleValueEvent(getData)

//            val qry: Query = myRef.orderByChild("Programme").equalTo("rsf")
//            qry.addValueEventListener(getData)
//            qry.addListenerForSingleValueEvent(getData)
        }


    }
}