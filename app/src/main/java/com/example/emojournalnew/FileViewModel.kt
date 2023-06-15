package com.example.emojournalnew

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.io.File

class FileViewModel (
    private val repository: FileRepository = FileRepository()
): ViewModel() {

    fun uploadAudio(file: File) {
        viewModelScope.launch {
            repository.uploadAudio(file)
        }
    }
}