package uz.mytaxi.test.domain.repository


import uz.mytaxi.test.data.model.LocationModel

interface LocationRepository {
    fun addLocation(locationModel: LocationModel)
}