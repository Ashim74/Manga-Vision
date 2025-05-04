package com.droidnova.mangavision.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.droidnova.mangavision.data.local.dao.MangaDao
import com.droidnova.mangavision.data.local.dao.UserDao
import com.droidnova.mangavision.data.local.entities.MangaEntity
import com.droidnova.mangavision.data.local.entities.UserEntity

@Database(entities = [UserEntity::class,MangaEntity::class], version = 1, exportSchema = false)
abstract class MangaDatabase : RoomDatabase() {
    abstract val userDao: UserDao
    abstract val mangaDao: MangaDao
}
