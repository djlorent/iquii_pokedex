package it.djlorent.iquii.pokedex.data.sources.local.database.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

interface BaseDao<T> {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: T): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(items: List<T>): List<Long>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(item: T) : Int

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(items: List<T>)

    @Delete
    suspend fun delete(item: T) : Int

    @Delete
    suspend fun delete(items: List<T>) : Int
}