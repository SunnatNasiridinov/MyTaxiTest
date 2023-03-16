package uz.mytaxi.test.domain.repository

import uz.mytaxi.test.data.database.AppDatabase
import uz.mytaxi.test.data.model.LocationModel
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(
    private var appDatabase: AppDatabase
) : LocationRepository {

    override fun addLocation(locationModel: LocationModel)   {
        appDatabase.locationDao().addLocation(locationModel)
    }
}