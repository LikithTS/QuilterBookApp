package com.likith.data.repository

class JsonFileReader {

    fun readSampleJsonFromResource(fileName: String): String {
        val inputStream = this::class.java.classLoader?.getResourceAsStream(fileName)
        return inputStream?.bufferedReader().use {
            it!!.readText()
        }
    }

}