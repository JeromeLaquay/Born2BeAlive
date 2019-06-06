package com.example.born2bealive.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.example.born2bealive.R
import com.example.born2bealive.entities.User
import com.example.born2bealive.entities.UserToken
import com.example.born2bealive.fragments.HomeFragment
import com.example.born2bealive.managers.UserManager
import com.example.born2bealive.resources.RetrofitFactory
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    lateinit var username : String
    lateinit var password : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar!!.hide()

        connexionBtn.setOnClickListener {
            username = usernameTxt.text.toString()
            password = passwordTxt.text.toString()
            if(username == "" || password == ""){
                Toast.makeText(this@LoginActivity, "Veuillez entrer votre pseudo et votre mot de passe", Toast.LENGTH_LONG).show()
            }else{
                login()
            }

        }


        inscriptionBtn.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

    }

    fun login()  {
        val service = RetrofitFactory.makeRetrofitService()
        val call = service!!.login(username, password,"password")
        var token : String
        call.enqueue(object:  Callback<UserToken> {

            override fun onResponse(call: Call<UserToken>, response: Response<UserToken>) {

                if(response.isSuccessful){

                        token = response.body()!!.access_token
                        UserManager.instance(this@LoginActivity).setToken(token)
                        getCurrentUser(token)
                        /*val intent = Intent(this@LoginActivity, BottomMenuActivity::class.java)
                        intent.putExtra("token",token)
                        startActivity(intent)
                        Toast.makeText(this@LoginActivity, token, Toast.LENGTH_SHORT).show()*/

                }else {
                    Toast.makeText(this@LoginActivity, "pseudo ou mot de passe incorrect", Toast.LENGTH_LONG).show()
                }

            }
            override fun onFailure(call: Call<UserToken>, t: Throwable) {
                Toast.makeText(this@LoginActivity, "pseudo ou mot de passe incorrect", Toast.LENGTH_LONG).show()
            }
        })

    }

    fun getCurrentUser(token : String)  {
        val service = RetrofitFactory.userServiceWithToken(token)
        val call = service!!.currentUser()
        call.enqueue(object: Callback<User> {

            override fun onResponse(call: Call<User>, response: Response<User>) {

                if(response.isSuccessful){
                    UserManager.instance(this@LoginActivity).setUser(response.body()!!)
                    val intent = Intent(this@LoginActivity, BottomMenuActivity::class.java)
                    startActivity(intent)

                }else {
                    Toast.makeText(this@LoginActivity, "cuurent user not found", Toast.LENGTH_LONG).show()
                }

            }
            override fun onFailure(call: Call<User>, t: Throwable) {
            }
        })

    }

}
