package uz.mytaxi.test.presenter.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import uz.mytaxi.test.data.model.LocationModel
import uz.mytaxi.test.domain.usecase.LocationUseCase
import javax.inject.Inject

@HiltViewModel
class MapViewModelImpl @Inject constructor(
    private val locationUseCase: LocationUseCase
): MapViewModel,ViewModel() {
    override fun setLocation(locationModel: LocationModel) {
        locationUseCase.setLocation(locationModel)
    }
}