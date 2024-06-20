package com.example.appcenter_todolist.ui.components.button

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.appcenter_todolist.ui.theme.ButtonContainer
import com.example.appcenter_todolist.ui.theme.ButtonContent
import com.example.appcenter_todolist.ui.theme.CustomTypography

@Composable
fun ButtonBeforeLogin(
    content: String,
    onclick: () -> Unit
){
    Button(
        onClick = { onclick() },
        colors = ButtonDefaults.buttonColors(
            containerColor = ButtonContainer,
            contentColor = ButtonContent
        ),
        shape = RoundedCornerShape(15.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(57.dp)
    ) {
        Text(
            text = content,
            style = CustomTypography.bodyJamsil17,
        )
    }
}