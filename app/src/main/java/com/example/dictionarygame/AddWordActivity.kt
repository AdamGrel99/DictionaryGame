package com.example.dictionarygame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ListView
import java.io.PrintStream

class AddWordActivity : AppCompatActivity() {
    private val WORDS_FILE_NAME = "extrawords.txt"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_word)
    }

    fun letsAddTheWord(view: View){
        val wordToAdd = findViewById<EditText>(R.id.word_to_add)
        val definitionToAdd = findViewById<EditText>(R.id.definition_to_add)

        val word = wordToAdd.text.toString()
        val defn = definitionToAdd.text.toString()

        val line = "$word - $defn"
        val outStream = PrintStream(openFileOutput(WORDS_FILE_NAME, MODE_PRIVATE))
        outStream.println(line)
        outStream.close()
        /* Zamienik tych dwóch wyżej linijek
        outStream.use {
            outStream.println(line)
        }
         */
        // go back to the main activity (and return word+defn)
        val myIntent = Intent()
        myIntent.putExtra("word", word)
        myIntent.putExtra("defn", defn)
        setResult(RESULT_OK, myIntent)
        finish()
    }
}