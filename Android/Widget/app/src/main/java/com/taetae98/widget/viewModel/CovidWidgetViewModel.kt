package com.taetae98.widget.viewModel

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.*
import androidx.savedstate.SavedStateRegistryOwner
import com.taetae98.widget.R
import com.taetae98.widget.dto.CovidStatusResponse
import com.taetae98.widget.dto.CovidWidget
import com.taetae98.widget.manager.CovidStatusManager
import com.taetae98.widget.repository.CovidWidgetRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CovidWidgetViewModel @AssistedInject constructor(
    @Assisted
    stateHandle: SavedStateHandle,
    @Assisted
    appWidgetId: Int,
    @ApplicationContext
    context: Context,
    private val covidStatusManager: CovidStatusManager,
    private val covidWidgetRepository: CovidWidgetRepository,
) : ViewModel() {
    val city by lazy { stateHandle.getLiveData("COVID_WIDGET_VIEW_MODEL_CITY","") }
    val textColor by lazy { stateHandle.getLiveData("COVID_WIDGET_VIEW_MODEL_TEXT_COLOR", Color.WHITE) }
    val backgroundColor by lazy { stateHandle.getLiveData("COVID_WIDGET_VIEW_MODEL_BACKGROUND_COLOR", Color.BLACK) }

    val wallpaper by lazy { MutableLiveData<Drawable>(ContextCompat.getDrawable(context, R.drawable.window_wallpaper)) }
    val covidStatusResponse by lazy { stateHandle.getLiveData<CovidStatusResponse>("COVID_WIDGET_VIEW_MODEL_RESPONSE", null) }

    val isLoading by lazy {
        MediatorLiveData<Boolean>().apply {
            addSource(covidStatusResponse) {
                postValue(it == null)
            }
        }
    }

    var onCovidWidget: ((covidWidget: CovidWidget) -> Unit)? = null

    init {
        initCovidStatusResponse(context)
        initCovidWidget(appWidgetId)
    }

    private fun initCovidStatusResponse(context: Context) {
        covidStatusManager.onLatestCovidStatus().enqueue(
            object : Callback<CovidStatusResponse> {
                override fun onResponse(call: Call<CovidStatusResponse>, response: Response<CovidStatusResponse>) {
                    val body = response.body()
                    if (body != null) {
                        covidStatusResponse.postValue(body)
                    } else {
                        viewModelScope.launch(Dispatchers.IO) {
                            retry(call)
                        }
                    }
                }

                override fun onFailure(call: Call<CovidStatusResponse>, t: Throwable) {
                    Toast.makeText(context, "Error : $t", Toast.LENGTH_LONG).show()
                    viewModelScope.launch(Dispatchers.IO) {
                        retry(call)
                    }
                }

                private suspend fun retry(call: Call<CovidStatusResponse>) {
                    delay(1000L)
                    call.clone().enqueue(this)
                }
            }
        )
    }

    private fun initCovidWidget(appWidgetId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            covidWidgetRepository.findById(appWidgetId)?.let { widget ->
                city.postValue(widget.city)
                textColor.postValue(widget.textColor)
                backgroundColor.postValue(widget.backgroundColor)
            }
        }
    }

    fun onCovidWidget() {
        onCovidWidget?.invoke(
            CovidWidget(
                city = city.value ?: "",
                textColor = textColor.value ?: Color.WHITE,
                backgroundColor = backgroundColor.value ?: Color.BLACK
            )
        )
    }

    @AssistedFactory
    interface HiltFactory {
        fun create(stateHandle: SavedStateHandle, appWidgetId: Int): CovidWidgetViewModel
    }

    class Factory(
        private val hiltFactory: HiltFactory,
        private val appWidgetId: Int,
        owner: SavedStateRegistryOwner
    ) : AbstractSavedStateViewModelFactory(owner, null) {
        override fun <T : ViewModel?> create(key: String, modelClass: Class<T>, handle: SavedStateHandle): T {
            return hiltFactory.create(handle, appWidgetId) as T
        }
    }
}