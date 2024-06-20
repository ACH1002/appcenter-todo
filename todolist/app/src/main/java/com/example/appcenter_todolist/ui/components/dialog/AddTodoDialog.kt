package com.example.appcenter_todolist.ui.components.dialog

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.appcenter_todolist.model.todo.AddTodoReq
import com.example.appcenter_todolist.ui.components.button.ButtonAfterLogin
import com.example.appcenter_todolist.ui.components.textfield.ContentTextField
import com.example.appcenter_todolist.ui.theme.Background
import com.example.appcenter_todolist.ui.theme.BlackTextColor
import com.example.appcenter_todolist.ui.theme.CustomTypography
import com.example.appcenter_todolist.ui.theme.Dimensions
import com.example.appcenter_todolist.ui.theme.GrayButtonContainer
import com.example.appcenter_todolist.viewmodel.BucketState
import com.example.appcenter_todolist.viewmodel.TodoViewModel
import es.dmoral.toasty.Toasty

@Composable
fun AddTodoDialog(
    selectedBucket: BucketState,
    todoViewModel: TodoViewModel,
    setAddTodoDialog: (Boolean) -> Unit
){

    val context = LocalContext.current

    var addTodoContent by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue())
    }

    val addBucketState by todoViewModel.addTodoState.collectAsState()

    Dialog(
        onDismissRequest = { setAddTodoDialog(false) },
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        Surface(
            modifier = Modifier
                .padding(horizontal = Dimensions.SidePadding)
                .width(360.dp)
                .height(280.dp),
            shape = RoundedCornerShape(30.dp),
            color = Background
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 28.5.dp, vertical = 33.dp)
            ) {
                Text(
                    text = "내용을 작성해주세요",
                    style = CustomTypography.bodyJamsil15.copy(
                        color = BlackTextColor,
                        letterSpacing = (-0.41).sp,
                        lineHeight = 24.sp,
                        fontSize = 16.sp
                    )
                )
                Spacer(modifier = Modifier.height(11.dp))
                ContentTextField(
                    content = addTodoContent,
                    setContent = { addTodoContent = it }
                )
                Spacer(modifier = Modifier.height(22.dp))
                ButtonAfterLogin(
                    content = "등록하기",
                    onClick = {
                        todoViewModel.addTodo(
                            bucketId = (selectedBucket as BucketState.Success).bucket.bucketId,
                            addTodoReq = AddTodoReq(
                                content = addTodoContent.text,
                                deadLine = "2024-05-25"
                            )
                        )
                    },
                    modifier = Modifier.height(57.dp),
                    containerColor = GrayButtonContainer
                )
            }
        }
    }

    LaunchedEffect(addBucketState){
        if (addBucketState == true){
            addTodoContent = TextFieldValue("")
            todoViewModel.clearAddTodoState()
            Toasty.success(context, "투두 만들기 성공", Toast.LENGTH_SHORT).show()
            setAddTodoDialog(false)
        } else if (addBucketState == false){
            addTodoContent = TextFieldValue("")
            todoViewModel.clearAddTodoState()
            Toasty.error(context, "투두 만들기 실패", Toast.LENGTH_SHORT).show()
            setAddTodoDialog(false)
        }
    }
}