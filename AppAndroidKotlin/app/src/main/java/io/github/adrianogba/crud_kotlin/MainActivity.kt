package io.github.adrianogba.crud_kotlin

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.widget.Toast
import com.android.volley.*
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonParser
import io.github.adrianogba.crud_kotlin.adapter.VeiculoListAdapter
import io.github.adrianogba.crud_kotlin.model.Veiculo
import kotlinx.android.synthetic.main.activity_main.*
import java.util.ArrayList
import java.util.HashMap

class MainActivity : AppCompatActivity() {


    internal lateinit var progressDialog: ProgressDialog

    internal var veiculosList: ArrayList<Veiculo> = ArrayList()

    internal lateinit var queue: RequestQueue

    internal lateinit var adapter: VeiculoListAdapter

    private var jsonParser: JsonParser? = null
    private var gson: Gson? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        jsonParser = JsonParser()
        gson = Gson()

        swipeRefresh.setOnRefreshListener { carregarLista() }

        btnAddVeiculo.setOnClickListener { v ->
            val i = Intent(v.context, AddVeiculo::class.java)
            v.context.startActivity(i)
        }

        progressDialog = ProgressDialog(this@MainActivity)
        progressDialog.setMessage("Carregando...")
        progressDialog.setCancelable(false)
        progressDialog.show()

        queue = Volley.newRequestQueue(this)

        carregarLista()

    }


    fun carregarLista() {
        veiculosList.clear()
        val stringRequest = object : StringRequest(Request.Method.POST,
                getString(R.string.webservice) + "getAllVeiculos.php", Response.Listener { response ->
            try {

                val mJson = jsonParser!!.parse(response) as JsonArray
                veiculosList = ArrayList()

                (0 until mJson.size())
                        .map {
                            gson!!.fromJson(mJson.get(it),
                                    Veiculo::class.java)
                        }
                        .forEach { veiculosList.add(it) }

                adapter = VeiculoListAdapter(this, veiculosList)

                veiculosListView?.adapter = adapter
                adapter.notifyDataSetChanged()

                errormessage.text = "Sem veículos cadastrados no momento."
                veiculosListView.emptyView = errormessage

                veiculosListView.setOnItemClickListener { _, view, position, _ ->
                    val i = Intent(view.context, VeiculoDetalheActivity::class.java)
                    i.putExtra("id", veiculosList[position].id)
                    view.context.startActivity(i)
                }

                progressDialog.cancel()
                swipeRefresh.isRefreshing = false

            } catch (e: Exception) {
                Toast.makeText(this@MainActivity, "Problemas na comuncação com o servidor.", Toast.LENGTH_SHORT).show()
                e.printStackTrace()
                progressDialog.cancel()
                swipeRefresh.isRefreshing = false
            }
        }, Response.ErrorListener {
            progressDialog.cancel()
            swipeRefresh.isRefreshing = false
            Toast.makeText(this@MainActivity,
                    "Problema na comunicação com o servidor!",
                    Toast.LENGTH_LONG).show()
        }) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val params = HashMap<String, String>()
                params["PATH"] = "getVeiculos"

                return params
            }
        }
        queue.add(stringRequest)
    }
}
