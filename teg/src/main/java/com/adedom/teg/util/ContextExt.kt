package com.adedom.teg.util

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

fun Context.convertMultipartBodyPart(fileUri: Uri, keyName: String): MultipartBody.Part {
    val parcelFileDescriptor = contentResolver.openFileDescriptor(fileUri, "r", null)

    val inputStream = FileInputStream(parcelFileDescriptor?.fileDescriptor)
    val file = File(cacheDir, contentResolver.getFileName(fileUri))
    val outputStream = FileOutputStream(file)
    inputStream.copyTo(outputStream)

    val requestFile = file.asRequestBody(contentResolver.getType(fileUri)?.toMediaTypeOrNull())
    return MultipartBody.Part.createFormData(keyName, file.name, requestFile)
}

fun ContentResolver.getFileName(fileUri: Uri): String {
    var name = ""
    val returnCursor = this.query(fileUri, null, null, null, null)
    if (returnCursor != null) {
        val nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
        returnCursor.moveToFirst()
        name = returnCursor.getString(nameIndex)
        returnCursor.close()
    }
    return name
}
