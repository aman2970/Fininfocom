package activities

import adapter.HomeAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fin.R
import com.example.fin.databinding.ActivityHomeBinding
import io.realm.Realm
import io.realm.mongodb.App
import io.realm.mongodb.AppConfiguration
import io.realm.mongodb.Credentials
import io.realm.mongodb.User
import io.realm.mongodb.mongo.MongoClient
import io.realm.mongodb.mongo.MongoCollection
import io.realm.mongodb.mongo.MongoDatabase
import io.realm.mongodb.mongo.iterable.MongoCursor
import model.Student
import org.bson.Document

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    val appID = "application-0-ovtue"
    private lateinit var realm: Realm
    private lateinit var homeAdapter: HomeAdapter
    private lateinit var mongoCollection: MongoCollection<Document>
    private lateinit var mongoClient: MongoClient
    private lateinit var mongoDatabase: MongoDatabase
    private lateinit var user: User
    private val studentList = mutableListOf<Student>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        showProgressBar()

        realm = Realm.getDefaultInstance()

        val app = App(AppConfiguration.Builder(appID).build())
        app.loginAsync(Credentials.anonymous(), App.Callback {
            if (it.isSuccess) {
                Log.d("log>>", "log_in_success")
                user = app.currentUser()!!
                mongoClient = user.getMongoClient("mongodb-atlas")
                mongoDatabase = mongoClient.getDatabase("Fin")
                mongoCollection = mongoDatabase.getCollection("Students")

                fetchDataAndSetupRecyclerView()

            } else {
                Log.d("log>>", "log_in_fail")
                hideProgressBar()
            }
        })
    }


    //For getting whole students data
    private fun fetchDataAndSetupRecyclerView() {
        mongoCollection.find().iterator().getAsync { task ->
            if (task.isSuccess) {
                val results = task.get()
                processQueryResults(results)
                setupRecyclerView()
            } else {
                Log.d("example>>>", "failed to find documents with: ${task.error}")
            }
        }
    }

    //For querying the data
    private fun processQueryResults(results: MongoCursor<Document>) {
        studentList.clear()
        while (results.hasNext()) {
            val document = results.next()
            val id = document.getObjectId("_id")
            val name = document.getString("name")
            val age = document.getInteger("age")
            val city = document.getString("city")
            val student = Student(id, name, age, city)
            studentList.add(student)
        }
    }

    //For setup Recyclerview on create
    private fun setupRecyclerView() {
        homeAdapter = HomeAdapter()
        binding.studentRv.layoutManager = LinearLayoutManager(this)
        binding.studentRv.adapter = homeAdapter
        homeAdapter.setList(studentList)
        homeAdapter.notifyDataSetChanged()
        hideProgressBar()
    }

    //For on click listener of menu item
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nameOption -> sortData("name")
            R.id.ageOption -> sortData("age")
            R.id.cityOption -> sortData("city")
        }
        return super.onOptionsItemSelected(item)
    }

    //For sorting and showing the data
    private fun sortData(sortField: String) {
        showProgressBar()
        val sortOptions = Document(sortField, 1)
        mongoCollection.find().sort(sortOptions).iterator().getAsync { task ->
            if (task.isSuccess) {
                val results = task.get()
                processQueryResults(results)
                homeAdapter.setList(studentList)
                homeAdapter.notifyDataSetChanged()
                hideProgressBar()
            } else {
                Log.d("example>>>", "failed to find documents, error: ${task.error}")
            }
        }
    }

    //For crating options menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)
        return true
    }

    private fun showProgressBar(){
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar(){
        binding.progressBar.visibility = View.GONE
    }
}

