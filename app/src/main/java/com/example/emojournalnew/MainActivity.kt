package com.example.emojournalnew

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.emojournal2.ApiConfig
import com.example.emojournalnew.ui.theme.EmoJournalNewTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.ResponseBody
import org.json.JSONObject
import java.io.File
import java.io.FileOutputStream

class MainActivity : ComponentActivity() {
    private lateinit var filePickerLauncher: ActivityResultLauncher<Intent>
    private val config = ApiConfig()
    private val emotionState = mutableStateOf("")
    private val confidenceState = mutableStateOf("")

    val emotionEmojiMap = mapOf(
        "angry" to R.drawable.angry,
        "disgust" to R.drawable.disguised,
        "sad" to R.drawable.sad,
        "happy" to R.drawable.happy,
        "neutral" to R.drawable.neutral,
        "surprise" to R.drawable.surprise,
        "fear" to R.drawable.fear,
        // Add more emotions and their corresponding emoji images as needed
    )

    data class EmotionData(val emojiImageRes: Int, val adviceMessage: String)
    val emotionDataMap = mapOf(
        "angry" to EmotionData(R.drawable.angry, "Take a deep breath and count to ten before responding."),
        "disgust" to EmotionData(R.drawable.disguised, "Find a positive distraction to shift your focus."),
        "sad" to EmotionData(R.drawable.sad, "Reach out to someone you trust and share your feelings."),
        "happy" to EmotionData(R.drawable.happy, "Celebrate your achievements and spread joy to others."),
        "neutral" to EmotionData(R.drawable.neutral, "Take a moment for self-reflection and self-care."),
        "surprise" to EmotionData(R.drawable.surprise, "Embrace the unexpected and stay open to new opportunities."),
        "fear" to EmotionData(R.drawable.fear, "Challenge your fears and take small steps toward overcoming them.")
        // Add more emotions, emoji images, and advice messages as needed
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EmoJournalNewTheme {
                val viewModel: FileViewModel by viewModels()

                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(bottom = 70.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Button(onClick = {
                            openFilePicker()
                        }) {
                            Text(text = "Upload Audio")
                        }
                    }

                    // Display the emotion and confidence to the user
                    Column(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(top = 16.dp)
                    ) {
                        EmotionScreen(emotionState.value, confidenceState.value)
                    }
                }
            }
        }
        filePickerLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val data: Intent? = result.data
                val uri = data?.data
                uri?.let {
                    val selectedFile = getFileFromUri(it)
                    selectedFile?.let {
                        uploadSoundFile(it)
                        hit_api()
                    }
                }
            }
        }
    }
    private fun openFilePicker() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "audio/*" // Set the desired file type here
        }
        filePickerLauncher.launch(intent)
    }

    private fun uploadSoundFile(file: File) {

        val requestBody = file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
        val filePart = MultipartBody.Part.createFormData("file", file.name, requestBody)
        val call = config.getApiService().uploadImage(filePart, requestBody)
        try {
            call.enqueue(object : retrofit2.Callback<ResponseBody> {
                override fun onResponse(
                    call: retrofit2.Call<ResponseBody>,
                    response: retrofit2.Response<ResponseBody>
                ) {
                    Log.d("succeed", response.raw().toString())
                }

                override fun onFailure(call: retrofit2.Call<ResponseBody>, t: Throwable) {
                    Log.d("onFailure", t.message.toString())
                }

            })
        } catch (ex: Exception) {
            ex.message?.let { Log.d("Exception", it) }
        }
    }
    private fun getFileFromUri(uri: Uri): File? {
        val inputStream = contentResolver.openInputStream(uri)
        val destinationFile = File(cacheDir, "selected_file")
        inputStream?.use { input ->
            FileOutputStream(destinationFile).use { output ->
                input.copyTo(output)
            }
        }
        return destinationFile
    }
    fun hit_api() {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val client = OkHttpClient()
                val request = Request.Builder()
                    .url("https://ml-api-3u667offna-uc.a.run.app/predict_from_bucket/selected_file")
                    .build()
                val response = client.newCall(request).execute()
                val body = response.body?.string()

                // Parse the JSON response
                val jsonObject = JSONObject(body)
                val emotion = jsonObject.getString("Emotion")
                val confidence = jsonObject.getString("Confidence")

                // Display the emotion and confidence to the user (e.g., using Log or Toast)
                emotionState.value = emotion
                confidenceState.value = confidence
                // Log.d("Emotion", emotion)
                // Log.d("Confidence", confidence)

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    @Composable
    fun EmotionScreen(emotionState: String, confidenceState: String) {
        val emotionData = emotionDataMap[emotionState]
        val emojiImagePainter: Painter? = emotionData?.emojiImageRes?.let { painterResource(it) }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Application name at the top
            Text(
                text = "EmoJournal",
                style = TextStyle(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier.padding(vertical = 16.dp)
            )

            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    emojiImagePainter?.let {
                        Image(
                            painter = it,
                            contentDescription = "Emoji",
                            modifier = Modifier.size(64.dp) // Adjust the size as needed
                        )
                    }
                    Text(text = "Emotion: $emotionState")
                    Text(text = "Confidence: $confidenceState")

                    emotionData?.adviceMessage?.let {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                                .background(color = Color.LightGray, shape = RoundedCornerShape(8.dp))
                                .padding(8.dp)
                        ) {
                            Text(
                                text = "Advice: $it",
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }
    }
}
