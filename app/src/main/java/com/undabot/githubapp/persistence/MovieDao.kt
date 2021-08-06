package com.undabot.githubapp.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.undabot.githubapp.model.Movie

@Dao
interface MovieDao {

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertMovieList(movieList: List<Movie>)

  @Query("SELECT * FROM Movie")
  suspend fun getMovieList(): List<Movie>

  @Query("SELECT * FROM Movie")
  suspend fun getAllMovieList(): List<Movie>
}
