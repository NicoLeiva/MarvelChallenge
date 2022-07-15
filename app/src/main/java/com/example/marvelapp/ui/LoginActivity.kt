package com.example.marvelapp.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.marvelapp.R
import com.example.marvelapp.databinding.ActivityLoginBinding
import com.example.marvelapp.utils.ToastUtils.showCustomToast
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val callbackManager: CallbackManager = CallbackManager.Factory.create()

    override fun onStart() {
        binding.loginView.visibility = View.VISIBLE
        super.onStart()
    }
    private fun session(){
        val prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE)
        val email = prefs.getString(getString(R.string.text_email), null)
        val provider = prefs.getString(getString(R.string.text_provider),null)
        if (email != null && provider != null) {
            binding.loginView.visibility = View.INVISIBLE
            showMain(email, provider)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        session()
        setupScreen()
    }

    private fun setupScreen(){
        loginUser()
        createUser()
        loginFacebook()
    }

    private fun loginUser(){
        binding.loginButton.setOnClickListener{
            if(binding.user.text.isNotEmpty() && binding.pass.text.isNotEmpty()){
                FirebaseAuth.getInstance()
                    .signInWithEmailAndPassword(binding.user.text.toString(),
                        binding.pass.text.toString()).addOnCompleteListener{
                        if(it.isSuccessful){
                            showMain(it.result.user?.email?: "" , ProviderType.BASIC.name)
                        } else
                            showAlert(getString(R.string.user_or_password_error))
                    }

            }  else
                showErrors()
        }
    }
    private fun createUser() {
        binding.registerButton.setOnClickListener{
            if(binding.user.text.isNotEmpty() && binding.pass.text.isNotEmpty()){
                FirebaseAuth.getInstance()
                    .createUserWithEmailAndPassword(binding.user.text.toString(),
                        binding.pass.text.toString()).addOnCompleteListener{
                        if(it.isSuccessful){
                            showMain(it.result.user?.email?: "" , ProviderType.BASIC.name)
                        } else
                            showAlert(getString(R.string.user_error))
                    }
            } else
                showErrors()
        }
    }

    private fun showErrors(){
        Toast(this).showCustomToast(getString(R.string.user_or_password_error), this)
        binding.tvUser.error = "error"
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode,data)
        super.onActivityResult(requestCode, resultCode, data)

    }

    private fun loginFacebook() {
        binding.facebookButton.setOnClickListener {
            binding.progressBar.visibility = View.VISIBLE
        LoginManager.getInstance().logInWithReadPermissions(this, listOf(getString(R.string.text_email)))

            LoginManager.getInstance().registerCallback( callbackManager,
                object : FacebookCallback<LoginResult> {
                    override fun onCancel() {
                    }

                    override fun onError(error: FacebookException) {
                        showAlert(getString(R.string.facebook_error))
                    }

                    override fun onSuccess(result: LoginResult) {
                        result.let {
                            val token = result.accessToken
                            val credential = FacebookAuthProvider.getCredential(token.token)
                            FirebaseAuth.getInstance().signInWithCredential(credential)
                                .addOnCompleteListener {
                                    if (it.isSuccessful) {
                                        binding.progressBar.visibility = View.INVISIBLE
                                        showMain(
                                            it.result?.user?.email ?: "",
                                            ProviderType.FACEBOOK.name
                                        )
                                    } else
                                        showAlert(getString(R.string.facebook_error))
                                }
                        }
                    }

                })
        }
    }

    private fun showAlert(msj:String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.title_error))
        builder.setMessage(msj)
        builder.setPositiveButton(getString(R.string.btn_accept),null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    enum class ProviderType {
        BASIC,FACEBOOK
    }
    private fun showMain(username:String, provider:String ){
        val mainIntent = Intent(this, MainActivity::class.java).apply {
            putExtra(getString(R.string.text_email), username)
            putExtra(getString(R.string.text_provider), provider)
        }
        startActivity(mainIntent)

    }
}