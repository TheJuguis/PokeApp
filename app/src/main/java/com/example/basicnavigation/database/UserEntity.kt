package com.example.basicnavigation.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = TABLE_USERS)
data class UserEntity(
    @PrimaryKey val id: Int,
    val username: String
)

fun UserEntity.toUser() = User (
    id, username
)
