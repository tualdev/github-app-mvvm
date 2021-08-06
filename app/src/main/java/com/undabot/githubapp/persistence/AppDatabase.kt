package com.undabot.githubapp.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import com.undabot.githubapp.model.Movie
import com.undabot.githubapp.model.MovieInfo

@Database(entities = [Movie::class, MovieInfo::class], version = 2, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {

  abstract fun movieDao(): MovieDao
  abstract fun movieInfoDao(): MovieInfoDao
}
