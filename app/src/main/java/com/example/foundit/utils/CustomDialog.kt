package com.example.foundit.utils

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
fun CustomDialog(
    onDismiss: () -> Unit,
    title: String,
    description: String,
    positiveAction: () -> Unit,
) {
    Dialog(
        onDismissRequest = { onDismiss() },
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
            dismissOnBackPress = true,
            dismissOnClickOutside = true
        )
    ) {
        Card(
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .padding(16.dp)
                .border(1.dp, Color.Black, RoundedCornerShape(8.dp))
        ) {
            Text(text = title, modifier = Modifier.padding(16.dp))
            Text(text = description, modifier = Modifier.padding(16.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = "Cancel",
                    modifier = Modifier.padding(end = 16.dp),
                    color = Color.Red
                )
                Text(
                    text = "Ok",
                    modifier = Modifier.padding(end = 16.dp),
                    color = Color.Green
                )
            }
        }
    }
}