package com.example.born2bealive.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.example.born2bealive.R
import com.example.born2bealive.entities.User
import com.example.born2bealive.resources.RetrofitFactory
import kotlinx.android.synthetic.main.activity_register.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {

    lateinit var user : User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        supportActionBar!!.hide()


        inscBtn.setOnClickListener {
            user = User()
            user.username=editUsername.text.toString()
            user.firstName=editFirstname.text.toString()
            user.lastName=editLastname.text.toString()
            user.email=editEmail.text.toString()
            user.badge=editBadge.text.toString()
            user.password=editPassword.text.toString()
            user.confirmPassword=editPasswordConfirm.text.toString()


            if(user.username == "" || user.firstName == "" || user.lastName == "" || user.email == "" || user.badge == "" || user.password == "" || user.confirmPassword == "" ){
                Toast.makeText(this@RegisterActivity, "Veuillez remplir tous les champs !", Toast.LENGTH_LONG).show()
            }else{
                register()
            }

        }

        connectBtn.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    fun register(){
        val service = RetrofitFactory.makeRetrofitService()
        val call = service!!.register(user)

        call.enqueue(object: Callback<User> {

            override fun onResponse(call: Call<User>, response: Response<User>) {

                if(response.isSuccessful){
                    runOnUiThread() {
                        val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                        startActivity(intent)
                        Toast.makeText(this@RegisterActivity, "inscription effectuée !" , Toast.LENGTH_LONG).show()
                    }
                }else {
                    Toast.makeText(this@RegisterActivity, "erreur pendant l'inscription !", Toast.LENGTH_LONG).show()
                }

            }
            override fun onFailure(call: Call<User>, t: Throwable) {
                Toast.makeText(this@RegisterActivity, "erreur pendant l'inscription !", Toast.LENGTH_LONG).show()
            }
        })
    }

}
