package com.kaushik.cryptoshow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.kaushik.cryptoshow.retrofit.Crypto
import com.kaushik.cryptoshow.retrofit.CryptoService
import com.kaushik.cryptoshow.retrofit.ServiceBuilder
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var mAdapter: CryptoAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fetchData()

        cryptoRecyclerView.layoutManager = LinearLayoutManager(this)
        mAdapter = CryptoAdapter(this)

        cryptoRecyclerView.adapter = mAdapter
    }

   private fun fetchData() {
        progressBar.visibility = View.VISIBLE
       // crypto service builder created
       val cryptoService = ServiceBuilder.buildService(CryptoService::class.java)

       // Query Map
       val query = HashMap<String,String>()
       query["vs_currency"] = "inr"
       query["order"] = "market_cap_desc"
       query["per_page"] = "100"
       query["page"] = "1"

       // API call
       val requestCall = cryptoService.getCrypto(query)
       requestCall.enqueue(object: Callback<ArrayList<Crypto>>{
           override fun onResponse(
               call: Call<ArrayList<Crypto>>,
               response: Response<ArrayList<Crypto>>
           ) {
               if(response.isSuccessful ) {
                   progressBar.visibility = View.GONE
                   Toast.makeText(this@MainActivity,"Got data",Toast.LENGTH_LONG).show()
                   Log.d("success","Got data")
                   val data = response.body()!!
                   mAdapter.updatedCrypto(data)
               } else if(response.isSuccessful && response.body() == null) {
                   progressBar.visibility = View.GONE
                   Log.d("error","Null Body")
               } else {
                   progressBar.visibility = View.GONE
                   Toast.makeText(this@MainActivity,"Failed to retrieve data",Toast.LENGTH_LONG).show()
               }
           }
           override fun onFailure(call: Call<ArrayList<Crypto>>?, t: Throwable?) {
               progressBar.visibility = View.GONE
               val errorMsg = t?.message
               Log.d("error", errorMsg.toString())
               Toast.makeText(this@MainActivity,"Error Encountered",Toast.LENGTH_LONG).show()
           }
       })
   }
}