package com.flux.launcher.presentation.activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flux.launcher.domain.mapper.toUiModel
import com.flux.launcher.domain.model.AppUiModel
import com.flux.launcher.domain.repository.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LauncherViewModel @Inject constructor(
    private val appRepository: AppRepository
) : ViewModel() {

    private val _appListLiveData = MediatorLiveData<MutableList<AppUiModel>>()
    val appListLiveData: LiveData<MutableList<AppUiModel>> get() = _appListLiveData

    init {
        viewModelScope.launch {
            appRepository.getApps().collect {
                val resultList = mutableListOf<AppUiModel>()
                var currentLetter = ' '
                it.map { appEntity ->
                    appEntity.toUiModel()
                }.forEach { app ->
                    val firstLetter = app.appName.first().uppercaseChar()
                    if (currentLetter != firstLetter) {
                        resultList.add(
                            AppUiModel(
                                id = app.id,
                                isHeader = true,
                                appName = firstLetter.toString(),
                            )
                        )
                    }
                    resultList.add(
                        AppUiModel(
                            id = app.id,
                            isHeader = false,
                            appName = app.appName,
                            appIcon = app.appIcon,
                            packageName = app.packageName
                        )
                    )
                    currentLetter = firstLetter
                    _appListLiveData.postValue(resultList)
                }
            }
        }
    }

}