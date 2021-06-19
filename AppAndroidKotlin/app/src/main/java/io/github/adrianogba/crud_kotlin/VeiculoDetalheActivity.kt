package io.github.adrianogba.crud_kotlin

import android.app.ProgressDialog

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

import android.widget.Toast

import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response

import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonParser

import java.text.SimpleDateFormat
import java.util.HashMap

import io.github.adrianogba.crud_kotlin.model.Veiculo
import kotlinx.android.synthetic.main.activity_veiculo_detalhe.*


class VeiculoDetalheActivity:AppCompatActivity() {

    internal lateinit var progressDialog:ProgressDialog
    private var jsonParser:JsonParser = JsonParser()
    private var gson:Gson = Gson()
    internal lateinit var queue:RequestQueue
    internal lateinit var bundle:Bundle
    internal lateinit var veiculo:Veiculo

    override fun onCreate(savedInstanceState:Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_veiculo_detalhe)

        bundle = intent.extras
        queue = Volley.newRequestQueue(this)

        progressDialog = ProgressDialog(this@VeiculoDetalheActivity)
        progressDialog.setMessage("Carregando...")
        progressDialog.setCancelable(false)
        progressDialog.show()

        btnvoltar.setOnClickListener { onBackPressed() }

        btneditar.setOnClickListener { v ->
            val i = Intent(v.context, AddVeiculo::class.java)
            i.putExtra("editar", "editar")
            i.putExtra("marca", veiculo.marca)
            i.putExtra("modelo", veiculo.modelo)
            i.putExtra("cor", veiculo.cor)
            i.putExtra("ano", veiculo.ano)
            i.putExtra("preco", veiculo.preco)
            i.putExtra("ehnovo", veiculo.ehnovo)
            i.putExtra("descricao", veiculo.descricao)
            i.putExtra("id", veiculo.id)
            i.putExtra("dt_cadastro", veiculo.dt_cadastro)
            v.context.startActivity(i)
        }

        btnremover.setOnClickListener {
            val lista = arrayOfNulls<String>(2)
            lista[0] = "Sim"
            lista[1] = "Não"
            val builder = AlertDialog.Builder(this@VeiculoDetalheActivity)
            builder.setTitle("Tem certeza que deseja remover este veículo?")
            builder.setItems(lista) { _, which ->
                if (which == 0) {

                    val stringRequest = object:StringRequest(Request.Method.POST,
                            getString(R.string.webservice) + "deleteVeiculo.php", Response.Listener<String> { response ->
                        try {
                            progressDialog.cancel()
                            Toast.makeText(this@VeiculoDetalheActivity, response, Toast.LENGTH_LONG).show()
                            val i = Intent(this@VeiculoDetalheActivity, MainActivity::class.java)
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                            startActivity(i)
                        } catch (e:Exception) {
                            Toast.makeText(this@VeiculoDetalheActivity, "Problemas na comuncação com o servidor.", Toast.LENGTH_SHORT).show()
                            e.printStackTrace()
                            progressDialog.cancel()
                        }
                    }, Response.ErrorListener {
                        progressDialog.cancel()
                        Toast.makeText(this@VeiculoDetalheActivity,
                                "Problema na comunicação com o servidor!",
                                Toast.LENGTH_LONG).show()
                    }) {
                        @Throws(AuthFailureError::class)
                        override fun getHeaders():Map<String, String> {
                            val params = HashMap<String, String>()
                            params["PATH"] = "deleteVeiculo"
                            params["ID"] = bundle.getString("id")
                            return params
                        }
                    }
                    queue.add<String>(stringRequest)
                }
            }
            val alertDialog = builder.create()
            alertDialog.show()
        }

        val stringRequest = object:StringRequest(Request.Method.POST,
                getString(R.string.webservice) + "getVeiculo.php", Response.Listener<String> { response ->
            try {
                val mJson = jsonParser.parse(response) as JsonArray
                veiculo = gson.fromJson<Veiculo>(mJson.get(0), Veiculo::class.java)

                txtmarca.text = veiculo.marca
                txtmodelo.text = veiculo.modelo
                txtcor.text = veiculo.cor
                txtano.text = veiculo.ano
                txtpreco.text = veiculo.preco
                txtdescricao.text = veiculo.descricao

                if (veiculo.ehnovo==("1")) {
                    txtehnovo.text = "Novo"
                } else {
                    txtehnovo.text = "Usado"
                }

                val formatter = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
                val formatterbr = SimpleDateFormat("dd/MM/yyyy 'às' hh:mm")
                var result = formatter.parse(veiculo.dt_cadastro)
                txtdt_cadastro.text = formatterbr.format(result)

                try {
                    if (veiculo.dt_atualizacao.trim() != ("")) {
                        result = formatter.parse(veiculo.dt_atualizacao)
                        txtdt_atualizacao.text = formatterbr.format(result)
                    }
                } catch (e:Exception) {
                    e.printStackTrace()
                    txtdt_atualizacao.text = formatterbr.format(result)
                }
                progressDialog.cancel()
            } catch (e:Exception) {
                Toast.makeText(this@VeiculoDetalheActivity, "Problemas na comuncação com o servidor.", Toast.LENGTH_SHORT).show()
                e.printStackTrace()
                progressDialog.cancel()
            }
        }, Response.ErrorListener {
            progressDialog.cancel()
            Toast.makeText(this@VeiculoDetalheActivity,
                    "Problema na comunicação com o servidor!",
                    Toast.LENGTH_LONG).show()
        }) {

            @Throws(AuthFailureError::class)
            override fun getHeaders():Map<String, String> {

                val params = HashMap<String, String>()
                params["PATH"] = "getVeiculoDetalhe"
                params["ID"] = bundle.getString("id")

                return params
            }

        }
        queue.add<String>(stringRequest)

    }
}
