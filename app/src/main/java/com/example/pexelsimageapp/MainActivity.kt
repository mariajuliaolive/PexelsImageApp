package com.example.pexelsimageapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.lifecycle.lifecycleScope
import com.example.pexelsimageapp.adapters.ImagesAdapter
import com.example.pexelsimageapp.database.ImageDatabase
import com.example.pexelsimageapp.models.ImageEntity
import com.example.pexelsimageapp.network.RetrofitClient
import com.example.pexelsimageapp.network.PexelsResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var imagesAdapter: ImagesAdapter
    private lateinit var searchButton: Button
    private lateinit var queryEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializa os componentes da UI
        recyclerView = findViewById(R.id.rvImages)
        recyclerView.layoutManager = LinearLayoutManager(this)

        searchButton = findViewById(R.id.btnSearch)
        queryEditText = findViewById(R.id.etSearchQuery)

        // Configura o botão de pesquisa
        searchButton.setOnClickListener {
            val query = queryEditText.text.toString().trim()
            if (query.isNotEmpty()) {
                searchImages(query)
            } else {
                Toast.makeText(this, "Por favor, insira uma palavra-chave", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Função para buscar as imagens via Retrofit
    private fun searchImages(query: String) {
        RetrofitClient.pexelsApi.searchImages(query).enqueue(object : Callback<PexelsResponse> {
            override fun onResponse(call: Call<PexelsResponse>, response: Response<PexelsResponse>) {
                if (response.isSuccessful) {
                    val photos = response.body()?.photos ?: emptyList()
                    imagesAdapter = ImagesAdapter(photos)
                    recyclerView.adapter = imagesAdapter

                    // Salvar as imagens no banco de dados local (Room)
                    saveImagesLocally(photos)
                } else {
                    Toast.makeText(this@MainActivity, "Erro ao buscar as imagens", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<PexelsResponse>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Erro na conexão", Toast.LENGTH_SHORT).show()
            }
        })
    }

    // Função para salvar as imagens no banco de dados local
    private fun saveImagesLocally(photos: List<PexelsResponse.Photo>) {
        val database = ImageDatabase.getDatabase(applicationContext)
        val imageDao = database.imageDao()

        lifecycleScope.launch {
            for (photo in photos) {
                val imageEntity = ImageEntity(url = photo.src?.original ?: "", localPath = "") // Salve o URL da imagem, path vazio por enquanto
                imageDao.insertImage(imageEntity)
            }
        }
    }
}
