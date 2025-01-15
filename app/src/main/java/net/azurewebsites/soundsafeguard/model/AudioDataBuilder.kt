package net.azurewebsites.soundsafeguard.model

import kotlin.math.max
import kotlin.math.min
import kotlin.random.Random


class AudioDataBuilder {

    //Noise Level에 따른 노이즈 추가 메소드 (level이 높을수록 노이즈 증가)
    fun addNoise(data: FloatArray, noiseLevel: Float = 0.005f): FloatArray {
        //랜덤 값 생성
        val noise = FloatArray(data.size) { Random.nextFloat() * 2 - 1 }
        val augmentedData = FloatArray(data.size)

        for (i in data.indices) {
            augmentedData[i] = data[i] + noiseLevel * noise[i]

            // 값이 -1과 1 사이로 유지되도록 클리핑
            augmentedData[i] = max(-1f, min(1f, augmentedData[i]))
        }

        return augmentedData

    }

    /*
    //TimeStretch -> 배속 조정 메소드
    fun applyTimeStretch(input: FloatArray, factor: Float, sampleRate: Int): FloatArray {
        val processor = TimeStrech(factor, sampleRate)

    }*/

    //음성의 시간을 shiftSamples 만큼 앞 뒤로 조정
    fun timeShift(input: FloatArray, shiftSamples: Int): FloatArray {
        val shiftedAudio = FloatArray(input.size)
        if (shiftSamples > 0) {
            for (i in shiftSamples until input.size) {
                shiftedAudio[i] = input[i - shiftSamples]
            }
        } else {
            for (i in 0 until input.size + shiftSamples) {
                shiftedAudio[i] = input[i - shiftSamples]
            }
        }
        return shiftedAudio
    }

}