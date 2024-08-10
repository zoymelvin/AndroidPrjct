package com.app.project

import RecyclerView.UserAdapter
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.app.project.model.UserResponse
import com.app.project.network.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ThirdScreenActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var userAdapter: UserAdapter
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    private var currentPage = 1
    private val perPage = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third_screen)

        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout)
        recyclerView = findViewById(R.id.rv_users)

        userAdapter = UserAdapter(mutableListOf()) { user ->
            // Kirim nama user yang dipilih kembali ke SecondScreenActivity
            val intent = Intent()
            intent.putExtra("selected_user_name", "${user.first_name} ${user.last_name}")
            setResult(Activity.RESULT_OK, intent)
            finish()
        }

        recyclerView.adapter = userAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        swipeRefreshLayout.setOnRefreshListener {
            currentPage = 1
            userAdapter.notifyDataSetChanged()
            fetchUsers()
        }

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (!recyclerView.canScrollVertically(1)) {
                    currentPage++
                    fetchUsers()
                }
            }
        })

        fetchUsers()
    }

    private fun fetchUsers() {
        val apiService = Retrofit.Builder()
            .baseUrl("https://reqres.in/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)

        apiService.getUsers(currentPage, perPage).enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                swipeRefreshLayout.isRefreshing = false
                if (response.isSuccessful) {
                    response.body()?.let {
                        if (currentPage == 1) {
                            userAdapter.addUsers(it.data)
                        } else {
                            userAdapter.addUsers(it.data)
                        }
                    }
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                swipeRefreshLayout.isRefreshing = false
                Toast.makeText(this@ThirdScreenActivity, "Failed to load data", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
