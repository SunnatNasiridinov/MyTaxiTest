package uz.mytaxi.test.domain.usecase

import uz.mytaxi.test.data.model.LocationModel
import uz.mytaxi.test.domain.repository.LocationRepository
import javax.inject.Inject

class LocationUseCaseImpl @Inject constructor(
    private val locationRepository: LocationRepository
): LocationUseCase {
    override fun setLocation(locationModel: LocationModel) {
            locationRepository.addLocation(locationModel)
    }
}