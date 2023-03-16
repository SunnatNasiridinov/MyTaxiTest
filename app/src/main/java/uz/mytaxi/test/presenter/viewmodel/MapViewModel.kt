package uz.mytaxi.test.presenter.viewmodel

import uz.mytaxi.test.data.model.LocationModel

interface MapViewModel {
    fun setLocation(locationModel: LocationModel)
}