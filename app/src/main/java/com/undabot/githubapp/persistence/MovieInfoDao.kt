package com.undabot.githubapp.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.undabot.githubapp.model.MovieInfo

@Dao
interface MovieInfoDao {

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertMovieInfo(movieInfo: MovieInfo)

  @Query("SELECT * FROM MovieInfo WHERE imdbID = :imdbID")
  suspend fun getMovieInfo(imdbID: String): MovieInfo?
}
