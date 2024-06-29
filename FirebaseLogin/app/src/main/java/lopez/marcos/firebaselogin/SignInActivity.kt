package lopez.marcos.firebaselogin

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import lopez.marcos.firebaselogin.databinding.ActivitySignInBinding

class SignInActivity : ComponentActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivitySignInBinding
    private val REQ_ONE_TAP = 2  // Can be any integer unique to the Activity
    private var showOneTapUI = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)

        setContentView(binding.root)
        auth = Firebase.auth

        binding.login.setOnClickListener {
            val mEmail = binding.email.text.toString()
            val mPassword = binding.contrasena.text.toString()

            when{
                mEmail.isBlank() || mPassword.isBlank() ->{
                    Toast.makeText(baseContext,"Mail o contraseña incorrecta",Toast.LENGTH_SHORT).show()
                }else ->{
                    signIn(mEmail,mPassword)
                }
            }

        }
    }

    private fun signIn(email: String, password: String){
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success")
                    reload()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }
    }

    private fun reload(){
        val intent = Intent(this,MainActivity::class.java)
        this.startActivity(intent)
    }
}
