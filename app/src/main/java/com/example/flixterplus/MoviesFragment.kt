package com.example.flixterplus

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.ContentLoadingProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.RequestParams
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.example.flixterplus.MoviesRecyclerViewAdapter
import com.example.flixterplus.R
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.Headers
import org.json.JSONObject



// --------------------------------//
// CHANGE THIS TO BE YOUR API KEY  //
// --------------------------------//
// private const val API_KEY = "a07e22bc18f5cb106bfe4cc1f83ad8ed"

/*
 * The class for the only fragment in the app, which contains the progress bar,
 * recyclerView, and performs the network calls to the Movie Database API.
 */
class MoviesFragment : Fragment(), OnListFragmentInteractionListener {

    /*
     * Constructing the view
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_movies_list, container, false)
//        val progressBar = view.findViewById<View>(R.id.progress) as ContentLoadingProgressBar
        val recyclerView = view.findViewById<View>(R.id.list) as RecyclerView
//        val adapter = MoviesRecyclerViewAdapter(listOfMovies)
//        recyclerView.adapter = adapter
        val context = view.context
        recyclerView.layoutManager = GridLayoutManager(context, 1)
        updateAdapter(recyclerView)
        return view
    }

    /*
     * Updates the RecyclerView adapter with new data.  This is where the
     * networking magic happens!
     */
    private fun updateAdapter(recyclerView: RecyclerView) {
//        progressBar.show()

        // Create and set up an AsyncHTTPClient() here
        val client = AsyncHttpClient()
        val params = RequestParams()
     //   params["api-key"] = "a07e22bc18f5cb106bfe4cc1f83ad8ed"

        // Using the client, perform the HTTP request
        client[
                "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed",
                params,
                object : JsonHttpResponseHandler() {
                    override fun onFailure(
                        statusCode: Int,
                        headers: Headers?,
                        response: String?,
                        throwable: Throwable?
                    ) {
                        if (response != null) {
                            Log.e("Failure", response)
                        } else {
                            Log.e("GD", "response is null")
                        }

                    }

                    override fun onSuccess(
                        statusCode: Int,
                        headers: Headers?,
                        json: JSON? // jason response
                    ) {
                        Log.e("Success", json.toString())

                        val JsonOjbect = json?.jsonObject
                        val resultArray = JsonOjbect?.getJSONArray("results")
                        val listOfMovies: MutableList<Movie> = mutableListOf()
                        if (resultArray != null) {

                            for (i in 0 until resultArray.length()) {
                                val resultObject = resultArray.getJSONObject(i)
                                val posterPath = resultObject.getString("poster_path")
                                val title = resultObject.getString("title")
                                val description = resultObject.getString("overview")
                                val movie_data = Movie(title, description, posterPath)
                                listOfMovies.add(movie_data)

                            }

                        }

                        for (movieData in listOfMovies) {
                            movieData.title?.let { Log.e("title", it) }
                            movieData.description?.let { Log.e("description", it) }
                            movieData.movieImageUrl?.let { Log.e("posterPath", it) }
                        }

                        val recyclerView = view?.findViewById<View>(R.id.list) as RecyclerView
                        recyclerView.adapter = MoviesRecyclerViewAdapter(listOfMovies)
                        recyclerView.layoutManager = GridLayoutManager(context, 1)


                    }
                }
        ]

    }

    /*
     * What happens when a particular movie is clicked.
     */
    override fun onItemClick(item: Movie) {
        Toast.makeText(context, "test: " + item.title, Toast.LENGTH_LONG).show()
    }

}
