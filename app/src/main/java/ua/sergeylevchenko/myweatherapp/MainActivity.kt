package ua.sergeylevchenko.myweatherapp

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import org.jetbrains.anko.doAsync
import org.json.JSONObject
import java.net.URL


class MainActivity : AppCompatActivity() {

    private var user_field: EditText? = null
    private var main_btn: Button? = null
    private var result_info: TextView? = null


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        user_field = findViewById(R.id.user_field)
        main_btn = findViewById(R.id.main_btn)
        result_info = findViewById(R.id.result_info)

        main_btn?.setOnClickListener {
            if (user_field?.text?.toString()?.trim()?.equals("")!!)
                Toast.makeText(this, "Введите город", Toast.LENGTH_LONG).show()
            else {
                var city: String = user_field!!.text.toString()
                var key: String = "12bc6a502828a8b1dfce046b0b18e644"
                var url: String =
                    "https://api.openweathermap.org/data/2.5/weather?q=$city&appid=$key&units=metric&mod=json&lang=ru"

                doAsync {
                    val apiResponse = URL(url).readText()
                    Log.d("INFO", apiResponse)

                    val weather = JSONObject(apiResponse).getJSONArray("weather")
                    val desc = weather.getJSONObject(0).getString("description")


                    val main = JSONObject(apiResponse).getJSONObject("main")
                    val temp = main.getString("temp")
                    val tempmin = main.getString("temp_min")
                    val tempmax = main.getString("temp_max")


                    result_info?.text =
                        "Температура: $temp\n$desc, Мин.температура: $tempmin, Макс.температура: $tempmax"
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.info -> {
                Toast.makeText(this, "Это учебная версия", Toast.LENGTH_LONG).show()

            }
        }
        return super.onOptionsItemSelected(item)
    }
}


