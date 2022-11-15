package com.example.dictionarygame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class MainActivity : AppCompatActivity() {
    private val SOME_CODE = 200

    private val the_word by lazy { findViewById<TextView>(R.id.the_word) }
    private val definition_list by lazy { findViewById<ListView>(R.id
        .definitions_words) }
    private val words = ArrayList<String>()
    private val defns = ArrayList<String>()
    private lateinit var myAdapter : ArrayAdapter<String>
    private val wordToDefn = HashMap<String, String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
       //
        readDictionaryFile()
        setupList()
    }

    private fun readDictionaryFile(){
        val reader = Scanner(resources.openRawResource(R.raw.words))
        while (reader.hasNextLine()){
            val line = reader.nextLine()
            val pieces = line.split(" - ")
            words.add(pieces[0])
            wordToDefn.put(pieces[0], pieces[1])
        }
    }
    private fun setupList(){
        val rand = Random()
        val index = rand.nextInt(words.size)
        val word = words[index]
        the_word.text = word

        defns.clear()
        defns.add(wordToDefn[word]!!)
        words.shuffle()
        for (otherWord in words.subList(0, 5)){
            if (otherWord == word || defns.size == 5){
                continue
            }
            defns.add(wordToDefn[otherWord]!!)
        }
        defns.shuffle()

        val correctIndex = defns.indexOf(wordToDefn[word])

        myAdapter = ArrayAdapter<String>(this, android.R
            .layout.simple_list_item_1, defns)
        definition_list.adapter = myAdapter

        definition_list.setOnItemClickListener { _, _, indx, _ ->
            if (indx == correctIndex) {
                Toast.makeText(this, "You have right!!",
                    Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "It is Wrong!!",
                    Toast.LENGTH_SHORT).show()
            }
            setupList()
        }
    }

    fun newWordButtonClick(view: View){
        val myIntent = Intent(this, AddWordActivity::class.java)
        startActivityForResult(myIntent, SOME_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SOME_CODE){
            val word = data?.getStringExtra("word") ?: ""
            val defn = data?.getStringExtra("defn") ?: ""
            wordToDefn[word] = defn
            words.add(word)
        }
    }
}
