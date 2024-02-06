package activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.fin.R
import com.example.fin.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private val PREFS_NAME:String = "FinPref"
    private val PREF_KEY_LOGIN:String = "isLogin"
    private lateinit var binding:ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
        val isLogin = prefs.getBoolean(PREF_KEY_LOGIN, false)


        binding.loginBtn.setOnClickListener{
            val userNameInputText: String = binding.userNameEt.text.toString().trim()
            val passwordInputText: String = binding.passwordEt.text.toString().trim()
            val containsUppercase = passwordInputText.matches(Regex(".*[A-Z].*"))
            val containsSpecialCharacter = passwordInputText.matches(Regex(".*[^A-Za-z0-9].*"))
            val containsNumber = passwordInputText.matches(Regex(".*\\d.*"))
            if(userNameInputText.isNullOrEmpty()){
                binding.userNameEt.error = getString(R.string.str_please_enter_username)
            }else if(userNameInputText.length < 10){
                binding.userNameEt.error = getString(R.string.str_should_contain_ten)
            }else if(passwordInputText.isNullOrEmpty()){
                binding.passwordEt.error = getString(R.string.str_please_enter_password)
            }else if(passwordInputText.length < 7){
                binding.passwordEt.error = getString(R.string.str_password_contain)
            }else if(!containsUppercase){
                binding.passwordEt.error = getString(R.string.str_must_contain_alphabet)
            }else if(!containsSpecialCharacter){
                binding.passwordEt.error = getString(R.string.str_must_contain_special_char)
            }else if(!containsNumber){
                binding.passwordEt.error = getString(R.string.str_must_contain_numbers)
            }else{
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                prefs.edit().putBoolean(PREF_KEY_LOGIN,true).apply()
                finishAffinity()
            }
        }

    }
}