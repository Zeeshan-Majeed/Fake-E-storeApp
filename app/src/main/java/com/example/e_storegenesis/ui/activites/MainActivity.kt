package com.example.e_storegenesis.ui.activites

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.e_storegenesis.databinding.ActivityMainBinding
import com.example.e_storegenesis.db.DataBaseClass
import com.example.e_storegenesis.db.models.Products
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.io.InputStream
import java.nio.charset.Charset
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    @Inject
    lateinit var database: DataBaseClass

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

         /*CoroutineScope(Dispatchers.IO).launch {
             readJsonFromLocal().forEach { item ->
                 database.getDao().insertProducts(item)
             }
         }*/
    }


    private fun loadJSONFromAsset(): String? {
        var json: String? = try {
            val inputStream: InputStream = assets.open("fake_products.json")
            val size: Int = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            run {
                val charset: Charset = Charsets.UTF_8
                String(buffer, charset)
            }
        } catch (ex: IOException) {
            ex.printStackTrace()
            return null
        }
        return json
    }

    fun readJsonFromLocal(): MutableList<Products> {
        Log.i("read_json_object", "readJson:")
        val formList = ArrayList<Products>()
        try {
            val obj = loadJSONFromAsset()?.let { JSONObject(it) }
            obj?.let {
                val mJarry = it.getJSONArray("data")
                for (i in 0 until mJarry.length()) {
                    val jsonObject = mJarry.getJSONObject(i)
                    val category = jsonObject.getString("category")
                    val description = jsonObject.getString("description")
                    val id = jsonObject.getInt("id")
                    val image = jsonObject.getString("image")
                    val price = jsonObject.getDouble("price")
                    val rating = jsonObject.getDouble("rate")
                    val sold = jsonObject.getInt("count")
                    val title = jsonObject.getString("title")
                    val product = Products(
                        id,
                        category,
                        description,
                        image/*.replace("\\", "")*/,
                        price,
                        rating,
                        title,
                        sold
                    )
                    formList.add(product)

                }
            }
            Log.i("read_json_object", "readJson: $formList")
        } catch (e: JSONException) {
            Log.i("read_json_object", "readJson EXCP: ${e.message}")
        }
        return formList
    }
}