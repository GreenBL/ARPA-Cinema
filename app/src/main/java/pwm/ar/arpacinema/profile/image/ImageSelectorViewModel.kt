package pwm.ar.arpacinema.profile.image

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pwm.ar.arpacinema.model.ProfileImage
import pwm.ar.arpacinema.repository.DTO
import pwm.ar.arpacinema.repository.DTO.Stat.*
import pwm.ar.arpacinema.repository.RetrofitClient

class ImageSelectorViewModel : ViewModel() {

    private val _imageList : MutableLiveData<List<ProfileImage>?> = MutableLiveData()
    val imageList : LiveData<List<ProfileImage>?> = _imageList

    val api = RetrofitClient.service

    // todo status

    init {
        viewModelScope.launch {
            val resp = api.loadProfileImages()
            val body = resp.body()

            if (body == null) {
                _imageList.postValue(listOf())
                return@launch
            }

            if(body.status == SUCCESS) {
                _imageList.postValue(body.imageList)
            }
        }

    }
}