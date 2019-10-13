package com.smartherd.globofly.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Toast
import com.smartherd.globofly.R
import com.smartherd.globofly.services.MessageService
import com.smartherd.globofly.services.ServiceBuilder
import kotlinx.android.synthetic.main.activity_welcome.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WelcomeActivity : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_welcome)

		val messageService = ServiceBuilder.buildService(MessageService::class.java)
		val requestCall = messageService.getMessages("http://192.168.0.102:9000/messages")

		requestCall.enqueue(object :Callback<String>{
			override fun onFailure(call: Call<String>, t: Throwable) {
			Log.e("Error",t.message)
			}

			override fun onResponse(call: Call<String>, response: Response<String>) {

				if(response.isSuccessful){
					val msg = response.body()!!
					msg?.let {
						message.text = msg
					}
				}
			}

		})
	}

	fun getStarted(view: View) {
		val intent = Intent(this, DestinationListActivity::class.java)
		startActivity(intent)
		finish()
	}
}
