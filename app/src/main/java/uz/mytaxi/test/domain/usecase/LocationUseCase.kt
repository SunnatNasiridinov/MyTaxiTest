package uz.mytaxi.test.domain.usecase

import uz.mytaxi.test.data.model.LocationModel

interface LocationUseCase {
    fun setLocation(locationModel: LocationModel)
}