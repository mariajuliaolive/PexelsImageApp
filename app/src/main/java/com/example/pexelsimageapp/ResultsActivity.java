import android.content.Context
import org.json.JSONObject
import java.io.InputStream

fun readJsonFromAssets(context: Context, fileName: String): JSONObject {
    val inputStream: InputStream = context.assets.open(fileName)
    val json = inputStream.bufferedReader().use { it.readText() }
    return JSONObject(json)
}

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import org.json.JSONObject

class ResultsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_results)

        val chart = findViewById<LineChart>(R.id.lineChart)

                // Lendo o JSON (arquivo salvo nos assets)
                val json = readJsonFromAssets(this, "test_results.json")

        // Convertendo os dados para gráfico
        val entries = mutableListOf<Entry>()
        val resultsArray = json.getJSONArray("results")
        for (i in 0 until resultsArray.length()) {
            val result = resultsArray.getJSONObject(i)
            val time = result.getDouble("time").toFloat()
            val responseTime = result.getDouble("response_time").toFloat()
            entries.add(Entry(time, responseTime))
        }

        // Configurando o gráfico
        val dataSet = LineDataSet(entries, "Tempo de Resposta")
        chart.data = LineData(dataSet)
        chart.invalidate()
    }
}
