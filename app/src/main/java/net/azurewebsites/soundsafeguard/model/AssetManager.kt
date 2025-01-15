package net.azurewebsites.soundsafeguard.model

import android.content.Context
import org.tensorflow.lite.Interpreter
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

class AssetManager {

    fun loadInterpreter(context: Context, modelName: String): Interpreter {
        //assets파일에서 모델 파일 열기 (InputStream에 저장)
        val assetManager = context.assets
        val inputStream: InputStream = assetManager.open(modelName)

        //임시 파일 생성
        val file = File(context.filesDir, modelName)
        val outputStream = FileOutputStream(file)

        //파일 복사
        inputStream.copyTo(outputStream)
        inputStream.close()
        outputStream.close()

        return Interpreter(file)
    }
}

