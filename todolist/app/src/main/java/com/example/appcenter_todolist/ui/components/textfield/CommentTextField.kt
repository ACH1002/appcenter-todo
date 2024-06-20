package com.example.appcenter_todolist.ui.components.textfield

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.appcenter_todolist.ui.theme.BlackTextColor
import com.example.appcenter_todolist.ui.theme.CustomTypography
import com.example.appcenter_todolist.ui.theme.GrayTextFieldColor

@Composable
fun CommentTextField(
    content: TextFieldValue,
    updateContent: (TextFieldValue) -> Unit,
    height: Dp,
    placeholder: String = "",
    modifier : Modifier = Modifier
) {
    Box(
        modifier = modifier
            .height(height)
            .background(color = GrayTextFieldColor, shape = RoundedCornerShape(10.dp))
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        BasicTextField(
            value = content,
            onValueChange = { updateContent(it) },
            modifier = Modifier
                .padding(start = 10.dp)
                .fillMaxWidth(),
            maxLines = 1,
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.None,
                autoCorrect = true,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            visualTransformation = VisualTransformation.None,
            textStyle = CustomTypography.textFieldInter15.copy(
                color = BlackTextColor,
                fontSize = 15.sp
            ),
            decorationBox = { innerTextField ->
                if (content.text.isEmpty()) {
                    Text(
                        text = placeholder,
                        style = CustomTypography.textFieldInter15.copy(
                            color = BlackTextColor.copy(
                                alpha = 0.5f
                            )
                        ),
                        modifier = Modifier.align(Alignment.CenterStart)
                    )
                }
                innerTextField()
            }
        )
    }
}