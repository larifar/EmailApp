import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.fiap.emailapp.database.dto.EmailDTO
import br.com.fiap.emailapp.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewModelApi : ViewModel() {

    private val _data = MutableLiveData<List<EmailDTO>?>()
    val data: LiveData<List<EmailDTO>?> get() = _data

    init {
        fetchData()
    }
    private fun fetchData() {
        RetrofitClient.api.getData().enqueue(object : Callback<List<EmailDTO>> {
            override fun onResponse(call: Call<List<EmailDTO>>, response: Response<List<EmailDTO>>) {
                if (response.isSuccessful) {
                    _data.postValue(response.body())
                } else {
                    _data.postValue(null)
                }
                println(data)
            }

            override fun onFailure(call: Call<List<EmailDTO>>, t: Throwable) {
                _data.postValue(null)
            }
        })
    }
}

