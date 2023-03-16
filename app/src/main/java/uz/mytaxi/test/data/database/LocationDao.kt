package uz.mytaxi.test.data.database

import androidx.room.Dao
import androidx.room.Insert
import uz.mytaxi.test.data.model.LocationModel

@Dao
interface LocationDao {

    @Insert
    fun addLocation(locationModel: LocationModel)
}