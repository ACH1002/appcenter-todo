package com.example.appcenter_todolist.ui.components.textfield

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.appcenter_todolist.ui.theme.BlackTextColor
import com.example.appcenter_todolist.ui.theme.CustomTypography
import com.example.appcenter_todolist.ui.theme.GrayTextColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContentTextField(
    content: TextFieldValue,
    setContent: (TextFieldValue) -> Unit,
){
    OutlinedTextField(
        value = content,
        onValueChange = {setContent(it)},
        shape = RoundedCornerShape(20.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            errorBorderColor = GrayTextColor,
            disabledBorderColor = GrayTextColor,
            focusedBorderColor = GrayTextColor,
            unfocusedBorderColor = GrayTextColor
        ),
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.None,
            autoCorrect = true,
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done
        ),
        textStyle = CustomTypography.textFieldInter15.copy(color = BlackTextColor),
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
    )
}