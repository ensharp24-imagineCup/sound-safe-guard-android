package net.azurewebsites.soundsafeguard.service

import com.microsoft.cognitiveservices.speech.audio.PullAudioInputStreamCallback

class ByteArrayAudioStream(private val audioData: ByteArray) : PullAudioInputStreamCallback() {
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
}