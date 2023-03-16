package uz.mytaxi.test.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LocationModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    var latLng: String
)