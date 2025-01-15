package net.azurewebsites.soundsafeguard.model

import android.content.Context
import androidx.compose.runtime.mutableStateOf

class AudioClassifier (context: Context) {
    val labelList = mutableListOf<String>()
    val inputStream = context.assets.open("yamnet_class_map.csv")

    //생성자
    init {
        loadLabels()
    }

    //라벨 값을 불러오기 (assets의 yamnet 클래스들을 불러옴)
    fun loadLabels(){
        inputStream.bufferedReader().use {reader ->
            reader.readLine()
            reader.forEachLine { line ->
                val columns = line.split(",")
                labelList.add(columns[2])
            }
        }
    }

    //가장 높은 스코어의 라벨 값을 반환함
    fun getTopLabel(outputScores: FloatArray) : String{
        //max값을 가진 index 출력
        val maxIndex = outputScores.indices.maxByOrNull { outputScores[it] } ?:-1

        //최대 인덱스 값을 결과 값으로 보내기
        if(maxIndex != -1){
            return labelList[maxIndex]
        }

        //인덱스 값이 없을 경우 공백 보내기
        return ""
    }
}
