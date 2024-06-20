package com.example.appcenter_todolist.ui.components.item

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.appcenter_todolist.R
import com.example.appcenter_todolist.model.bucket.BucketResponse
import com.example.appcenter_todolist.model.member.MemberResponse
import com.example.appcenter_todolist.model.todo.TodoResponse
import com.example.appcenter_todolist.ui.components.dialog.AddCommentDialog
import com.example.appcenter_todolist.ui.theme.Background
import com.example.appcenter_todolist.ui.theme.BlackIconColor
import com.example.appcenter_todolist.ui.theme.BlackTextColor
import com.example.appcenter_todolist.ui.theme.CustomTypography
import com.example.appcenter_todolist.ui.theme.Dimensions
import com.example.appcenter_todolist.ui.theme.GrayIconColor
import com.example.appcenter_todolist.viewmodel.CommentViewModel
import com.example.appcenter_todolist.viewmodel.TodoViewModel

@Composable
fun TodoItem(
    selectedTodo: TodoResponse,
    selectedBucket: BucketResponse,
    todoViewModel: TodoViewModel,
    commentViewModel: CommentViewModel,
    myInfo: MemberResponse,
    isMine: Boolean
) {
    val (addCommentDialog, setAddCommentDialog) = remember {
        mutableStateOf(false)
    }

    if (addCommentDialog) {
        AddCommentDialog(
            selectedBucket = selectedBucket,
            selectedTodo = selectedTodo,
            setAddCommentDialog = { setAddCommentDialog(it) },
            commentViewModel = commentViewModel,
            todoViewModel = todoViewModel,
            myInfo = myInfo,
            isMine = myInfo.id == selectedBucket.memberId
        )
    }

    Row(
        modifier = Modifier
            .padding(horizontal = Dimensions.SidePadding)
            .shadow(
                4.dp,
                shape = RoundedCornerShape(6.dp), // 모서리를 둥글게
            )
            .background(Background, shape = RoundedCornerShape(6.dp))
            .fillMaxWidth()
            .height(55.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        IconButton(onClick = {
            if (isMine) {
                todoViewModel.completeTodoById(
                    bucketId = selectedBucket.bucketId,
                    todoId = selectedTodo.id
                )
            }
        }) {
            Icon(
                painter = painterResource(id = if (selectedTodo.completed) R.drawable.checked_box else R.drawable.unchecked_box),
                contentDescription = null,
                tint = BlackIconColor
            )
        }
        Text(
            text = selectedTodo.content,
            style = CustomTypography.bodyInter20.copy(color = BlackTextColor.copy(alpha = 0.81f)),
            modifier = Modifier
                .weight(1f)
                .clickable { setAddCommentDialog(true) },
            textAlign = TextAlign.Start
        )
        if (isMine) {
            IconButton(onClick = {
                todoViewModel.deleteTodoById(
                    bucketId = selectedBucket.bucketId,
                    todoId = selectedTodo.id
                )
            }) {
                Icon(imageVector = Icons.Default.Close, contentDescription = null, tint = GrayIconColor)
            }
        }
    }
}