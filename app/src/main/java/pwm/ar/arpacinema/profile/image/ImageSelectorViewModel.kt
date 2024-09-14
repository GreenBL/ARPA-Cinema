package pwm.ar.arpacinema.profile.image

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import pwm.ar.arpacinema.Session
import pwm.ar.arpacinema.model.ProfileImage
import pwm.ar.arpacinema.repository.DTO
import pwm.ar.arpacinema.repository.DTO.Stat.*
import pwm.ar.arpacinema.repository.RetrofitClient

class ImageSelectorViewModel : ViewModel() {

    private val _imageList : MutableLiveData<List<ProfileImage>?> = MutableLiveData()
    val imageList : LiveData<List<ProfileImage>?> = _imageList

    val api = RetrofitClient.service

    private val _status : MutableLiveData<DTO.Stat> = MutableLiveData(DTO.Stat.DEFAULT)
    val status : LiveData<DTO.Stat> = _status


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

    suspend fun updateImage(profileImage: ProfileImage) {
        delay(452) // localhost is too fast
        try {
            val actualImageId = profileImage.imageId
            val resp = api.associateImage(DTO.ImageSwapRequest(Session.userIdStr, actualImageId.toString()))
            val body = resp.body()
            if (resp.isSuccessful.not()) {
                _status.postValue(ERROR)
                return
            }
            if (body?.status == SUCCESS) {
                Session.setUserImageURL(profileImage.imageUrl)
                _status.postValue(SUCCESS)
            } else {
                _status.postValue(ERROR)
            }
        } catch (e: Exception) {
            _status.postValue(ERROR)
            Log.e("ImageSelectorViewModel", "Error updating image: ${e.message}")
        }
    }
}