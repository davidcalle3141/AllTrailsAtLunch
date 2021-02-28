package com.example.atlunch.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Favorite(@PrimaryKey val  place_id: String)
