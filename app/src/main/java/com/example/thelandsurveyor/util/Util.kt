package com.example.thelandsurveyor.util

import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import java.text.SimpleDateFormat
import java.util.*

fun createFileName(): String {
    val date = Calendar.getInstance().time
    val formatter = SimpleDateFormat("dd-MM-yyyy-hh:mm:ss", Locale.getDefault())
    val currentDateTime = formatter.format(date)
    return "${currentDateTime}.crd"
}

fun showMessageToUser(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

fun callPermissionDialog(context: Context,
               activity: Activity,
               message: String,
               permissions: Array<String>,
               permissionCode: Int) {

    val permissionDialog = AlertDialog.Builder(context)
        .setTitle("Requested permission")
        .setMessage(message)
        .setPositiveButton("Ok")
        { dialog, _ ->
            ActivityCompat.requestPermissions(activity, permissions, permissionCode)
            dialog.dismiss()
        }
        .setNegativeButton("Cancel")
        { dialog, _ ->
            dialog.dismiss()
        }
    permissionDialog.show()
}
