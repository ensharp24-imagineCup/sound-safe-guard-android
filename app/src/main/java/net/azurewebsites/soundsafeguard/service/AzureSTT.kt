package net.azurewebsites.soundsafeguard.service

import com.microsoft.cognitiveservices.speech.ResultReason
import com.microsoft.cognitiveservices.speech.SpeechConfig
import com.microsoft.cognitiveservices.speech.SpeechRecognizer
import com.microsoft.cognitiveservices.speech.audio.AudioConfig
import com.microsoft.cognitiveservices.speech.audio.AudioStreamFormat
import com.microsoft.cognitiveservices.speech.audio.PullAudioInputStream
import com.microsoft.cognitiveservices.speech.audio.PullAudioInputStreamCallback
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import net.azurewebsites.soundsafeguard.BuildConfig

class AzureSTT(
    private val subscriptionKey: String = BuildConfig.AZURE_SUBSCRIPTION_KEY,
    private val serviceRegion: String = BuildConfig.AZURE_REGION
) {
    /*
    * Azure Speech to Text API를 이용하여 음성을 텍스트로 변환하는 함수
    * @param audioData 변환할 음성 데이터
    * @param language 언어 코드
    * @return 변환된 텍스트
     */
    suspend fun recognizeSpeechFromByteArray(audioData: ByteArray, language: String): String? =
        withContext(
            Dispatchers.IO
        ) {
            try {
                val speechConfig = SpeechConfig.fromSubscription(subscriptionKey, serviceRegion)
                speechConfig.speechRecognitionLanguage = language

                // Audio Input 생성
                val audioFormat =
                    AudioStreamFormat.getWaveFormatPCM(16000, 16, 1) // 샘플링 속도, 비트 심도, 채널
                val audioStream =
                    PullAudioInputStream.create(object : PullAudioInputStreamCallback() {
                        private var position: Int = 0

                        override fun read(buffer: ByteArray): Int {
                            val remaining = audioData.size - position
                            if (remaining <= 0) return 0

                            val count = buffer.size.coerceAtMost(remaining)
                            System.arraycopy(audioData, position, buffer, 0, count)
                            position += count
                            return count
                        }

                        override fun close() {
                            position = audioData.size
                        }
                    }, audioFormat)
                val audioConfig = AudioConfig.fromStreamInput(audioStream)

                // Speech Recognizer 생성
                val recognizer = SpeechRecognizer(speechConfig, audioConfig)

                // 음성 인식 실행
                val result = recognizer.recognizeOnceAsync().get()

                if (result.reason == ResultReason.RecognizedSpeech) {
                    return@withContext result.text
                } else {
                    println("Recognition failed. Reason: ${result.reason}")
                    println("Error details: ${result.text}")
                    return@withContext null
                }
            } catch (e: Exception) {
                e.printStackTrace()
                return@withContext null
            }
        }
}