package com.example.appcenter_todolist.ui.components.button

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.appcenter_todolist.ui.theme.CustomTypography
import com.example.appcenter_todolist.ui.theme.OrangeButtonContainer
import com.example.appcenter_todolist.ui.theme.OrangeButtonContent

@Composable
fun ButtonAfterLogin(
    modifier : Modifier = Modifier,
    content: String,
    roundedCornerDp: Dp = 15.dp,
    onClick: () -> Unit,
    containerColor: Color = OrangeButtonContainer
){
    Button(
        onClick = { onClick() },
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = OrangeButtonContent
        ),
        shape = RoundedCornerShape(roundedCornerDp),
        modifier = modifier
            .fillMaxWidth(),
        contentPadding = PaddingValues(0.dp)
    ) {
        Text(
            text = content,
            style = CustomTypography.bodyJamsil17,
        )
    }
}