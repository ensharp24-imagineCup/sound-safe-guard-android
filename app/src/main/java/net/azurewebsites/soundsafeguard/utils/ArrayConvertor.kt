package net.azurewebsites.soundsafeguard.utils

import java.nio.ByteBuffer
import java.nio.ByteOrder

class ArrayConvertor {
    fun byteArrayToFloatArray(byteArray: ByteArray):FloatArray{
        // ByteBuffer 생성 (ByteArray를 wrap)
        val byteBuffer = ByteBuffer.wrap(byteArray)

        // ByteOrder 설정 (데이터의 엔디언 맞춤)
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN)

        // FloatArray로 변환
        val floatArray = FloatArray(byteArray.size / 4)

        for (i in floatArray.indices) {
            floatArray[i] = byteBuffer.getFloat(i * 4)
        }

        return floatArray
    }
}