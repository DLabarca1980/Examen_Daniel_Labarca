package com.example.examen_daniel_labarca

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Environment
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.BlendMode.Companion.Screen
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.example.examen_daniel_labarca.db.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
//import java.lang.reflect.Modifier
import java.text.Normalizer.Form

@Composable
fun CameraUI(cameraController: LifecycleCameraController, permissionLauncher : ActivityResultLauncher<Array<String>>,
             appVM: AppVM, formVM : FormVM) {
    permissionLauncher.launch(arrayOf(android.Manifest.permission.CAMERA))

    val context = LocalContext.current
    val routineScope = rememberCoroutineScope()

    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = {
            PreviewView(it).apply { controller = cameraController }
        })

    Button(onClick = {
        takePhoto (cameraController = cameraController,
                   file = makePrivatePhotoFile (context),
                    context = context)  {
            routineScope.launch (Dispatchers.IO) {
                val dao = AppDatabase.getInstance(context).placeDao()
                formVM.photo.value = it
                dao.updateImgRef(formVM.id.value, it)
            }
                    appVM.currentScreen.value = com.example.examen_daniel_labarca.Screen.Form
        }
         },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
                    Text ( text = "Tomar Foto")
}
        }

fun takePhoto (
    cameraController: LifecycleCameraController,
    file : File,
    context : Context,
    onCaptureImage : (Bitmap) -> Unit
) {
    val options = ImageCapture.OutputFileOptions.Builder(file).build()

    cameraController.takePicture(
        options, ContextCompat.getMainExecutor(context),
        object : ImageCapture.OnImageSavedCallback {
            override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                outputFileResults.savedUri?.let { uri ->
                    val bitmap =
                        BitmapFactory.decodeStream(context.contentResolver.openInputStream(uri))
                    bitmap?.let {
                        onCaptureImage(it)
                    }
                }

            }

            override fun onError(exception: ImageCaptureException) {
                Log.e("Camara", "Error taking photo", exception)
            }
        }
    )

    }

fun makePrivatePhotoFile(context: Context) : File = File (
    context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "${System.currentTimeMillis()}.jpg"
)


