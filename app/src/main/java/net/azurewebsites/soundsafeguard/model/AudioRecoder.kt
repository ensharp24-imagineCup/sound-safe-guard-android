package net.azurewebsites.soundsafeguard.model

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import android.provider.MediaStore.Audio
import androidx.core.app.ActivityCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.tensorflow.lite.Interpreter

class AudioRecoder(context: Context, interpreter: Interpreter) {
    private val sampleRate = 16000

    val frameSize = 15600  // 16kHz * 0.96초 <- Yamnet의 프레임 단위
    val hopSize = 7800     // 16kHz * 0.48초 <- 윈도우 단위

    //버퍼 형태 정의
    private val bufferSize = AudioRecord.getMinBufferSize(
        sampleRate,
        AudioFormat.CHANNEL_IN_MONO,
        AudioFormat.ENCODING_PCM_16BIT
    )

    private var interpreter : Interpreter? = null

    private val context = context

    var isRecording : Boolean = false

    private var audioRecord: AudioRecord? = null

    val audioQueue = ArrayDeque<Float>()

    //생성자
    init {
        initRecorder()
        this.interpreter = interpreter
    }

    //Record 클래스 초기화
    fun initRecorder(){
        //Audio Record 권한 체크
        val recoder = if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.RECORD_AUDIO
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            //권한이 없을 때 추후 권한 추가 안내 메세지 팝업 창 추가 해야함.
            return
        }else{
            audioRecord = AudioRecord(
                MediaRecorder.AudioSource.MIC,
                sampleRate,
                AudioFormat.CHANNEL_IN_MONO,
                AudioFormat.ENCODING_PCM_16BIT,
                bufferSize
            )
        }
    }

    //녹화 시작
    fun startRecord(){
        audioRecord?.startRecording()
        isRecording = true

        CoroutineScope(Dispatchers.IO).launch {
            val audioBuffer = ShortArray(15600)
            val floatBuffer = FloatArray(15600)

            while (isRecording) {
                val readCount = audioRecord?.read(audioBuffer, 0, 15600) ?: 0
                println(readCount)
                // float 변환 및 처리
                for (i in 0 until readCount) {

                    floatBuffer[i] = audioBuffer[i] / 32768.0f
                }
                println("BB"+floatBuffer.joinToString (", "))
                // 오디오 프레임 처리
                processAudioFrame(floatBuffer)
                delay(1000)
            }
        }
    }

    fun processAudioFrame(buffer: FloatArray) {
        println("buffer: "+ buffer.joinToString { ", " })
        audioQueue.addAll(buffer.toList())  // 현재 오디오 데이터 추가

        // 오디오 큐에서 필요한 만큼 데이터가 차면 슬라이딩 윈도우 처리
        while (audioQueue.size >= frameSize) {
            // 1초 분량의 프레임을 꺼내서 처리
            val frame = audioQueue.take(frameSize).toFloatArray()
            println(frame.joinToString { ", " })
            // 프레임을 모델에 전달하여 처리
            runYamNetModel(frame)

            // 슬라이딩 윈도우 적용: 앞부분 hopSize만큼 제거
            repeat(hopSize) { audioQueue.removeFirst() }
        }
    }

    // YAMNet 모델 실행
    fun runYamNetModel(frame: FloatArray): Array<FloatArray> {
        // 모델 호출 및 결과 처리
        val outputBuffer = Array(1) { FloatArray(521) }
        println("AA:"+outputBuffer[0].joinToString(", "))  // 1024차원 임베딩 벡터 출력
        this.interpreter!!.run(frame, outputBuffer)
        return outputBuffer
    }

    //녹화 정지
    fun stopRecord(){
        isRecording=false
        audioRecord?.stop()
        audioRecord?.release()
    }

}