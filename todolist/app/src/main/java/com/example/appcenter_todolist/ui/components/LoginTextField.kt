package com.example.appcenter_todolist.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.appcenter_todolist.ui.theme.CustomTypography
import com.example.appcenter_todolist.ui.theme.GrayTextColor
import com.example.appcenter_todolist.ui.theme.GrayTextFieldColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginTextField(
    placeholder: String,
    content: TextFieldValue,
    updateContent: (TextFieldValue) -> Unit,
    isPassword: Boolean = false,
    isDone: Boolean = false,
    topLabel: String = ""
) {
    if (topLabel != ""){
        Text(
            text = topLabel,
            style = CustomTypography.bodyJamsil15.copy(color = GrayTextColor)
        )
    }
    Spacer(modifier = Modifier.height(6.dp))
    TextField(
        value = content,
        onValueChange = { updateContent(it) },
        modifier = Modifier
            .height(56.dp)
            .fillMaxWidth(),
        maxLines = 1,
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.None,
            autoCorrect = true,
            keyboardType = if (isPassword) KeyboardType.Password else KeyboardType.Email,
            imeAction = if (isDone) ImeAction.Done else ImeAction.Next
        ),
        colors = TextFieldDefaults.textFieldColors(
            containerColor = GrayTextFieldColor,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,

            ),
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
        textStyle = CustomTypography.textFieldInter15.copy(color = GrayTextColor),
        placeholder = { Text(text = placeholder, style = CustomTypography.textFieldInter15) },
        shape = RoundedCornerShape(
            topStart = 4.dp,
            topEnd = 4.dp,
            bottomEnd = 0.dp,
            bottomStart = 0.dp
        )
    )
    Divider(color = GrayTextColor, thickness = 1.dp)
}