package activities

import adapter.HomeAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fin.R
import com.example.fin.databinding.ActivityHomeBinding
import io.realm.Realm
import io.realm.RealmResults
import model.Student
import java.lang.Exception

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private val PREFS_NAME:String = "FinPref"
    private val PREF_KEY_DATA_ADDED:String = "dataAdded"
    private lateinit var homeAdapter:HomeAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        //Check if data is added before or not
        val prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
        val dataAdded = prefs.getBoolean(PREF_KEY_DATA_ADDED, false)

        //If data is not added then add
        if(!dataAdded){
            addDummyData()
            prefs.edit().putBoolean(PREF_KEY_DATA_ADDED, true).apply()
        }

        //To show data in recyclerview
        setupRecyclerView()
    }

    //Add dummy data of students
    private fun addDummyData() {
        var realm: Realm = Realm.getDefaultInstance()
        try {
            realm.executeTransaction {
                val students = listOf(
                    Student("Aarav", 18, "Mumbai"),
                    Student("Arya", 21, "Delhi"),
                    Student("Advait", 19, "Bangalore"),
                    Student("Ananya", 20, "Kolkata"),
                    Student("Aryan", 22, "Chennai"),
                    Student("Diya", 23, "Hyderabad"),
                    Student("Ishaan", 19, "Ahmedabad"),
                    Student("Kavya", 18, "Pune"),
                    Student("Vihaan", 20, "Jaipur"),
                    Student("Riya", 21, "Lucknow"),
                    Student("Arjun", 22, "Kanpur"),
                    Student("Anika", 19, "Nagpur"),
                    Student("Aanya", 20, "Patna"),
                    Student("Aadi", 24, "Indore"),
                    Student("Neha", 18, "Thane"),
                    Student("Vivaan", 21, "Bhopal"),
                    Student("Sara", 20, "Visakhapatnam"),
                    Student("Yash", 18, "Surat"),
                    Student("Zara", 19, "Varanasi"),
                    Student("Kabir", 23, "Kochi"),
                    Student("Priyanshu", 24, "Lucknow"),
                    Student("Amit", 25, "Gurugram"),
                    Student("Arsh", 22, "Ghaziabad"),
                    Student("Shivansh",21,"Agra"),
                    Student("Aman",22,"Mathura"),
                    Student("Naman",22,"Pune"),
                )
                it.copyToRealm(students)
            }
        } catch (e: Exception) {
            realm.cancelTransaction()
        } finally {
            realm.close()
        }
    }

    //Get all students data from database and show in recyclerview
    private fun setupRecyclerView() {
        val realm = Realm.getDefaultInstance()
        val students = realm.where(Student::class.java).findAll()
        homeAdapter = HomeAdapter()
        binding.studentRv.layoutManager = LinearLayoutManager(this)
        binding.studentRv.adapter = homeAdapter
        homeAdapter.setList(students)
        homeAdapter.notifyDataSetChanged()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.options_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.nameOption ->sortBy(1)
            R.id.ageOption ->sortBy(2)
            R.id.cityOption ->sortBy(3)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun sortBy(from : Int){
        val realm = Realm.getDefaultInstance()
        val students: RealmResults<Student> = when (from){
            1 -> realm.where(Student::class.java).sort("name").findAll()
            2 -> realm.where(Student::class.java).sort("age").findAll()
            3 -> realm.where(Student::class.java).sort("city").findAll()
            else -> realm.where(Student::class.java).findAll()
        }

        homeAdapter.setList(students)
        homeAdapter.notifyDataSetChanged()
    }
}