package com.example.appcenter_todolist.ui.components.item

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.appcenter_todolist.model.comment.CommentResponse
import com.example.appcenter_todolist.model.member.MemberResponse
import com.example.appcenter_todolist.model.todo.TodoResponse
import com.example.appcenter_todolist.ui.theme.BlackTextColor
import com.example.appcenter_todolist.ui.theme.CustomTypography
import com.example.appcenter_todolist.viewmodel.CommentViewModel
import com.example.appcenter_todolist.viewmodel.MyInfoState

@Composable
fun CommentItem(
    selectedTodo: TodoResponse,
    selectedComment: CommentResponse,
    myInfo: MemberResponse,
    commentViewModel: CommentViewModel
) {
    Row(
        modifier = Modifier
            .padding(vertical = 2.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = selectedComment.content,
            style = CustomTypography.textFieldInter15.copy(color = BlackTextColor)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = selectedComment.nickname,
                style = CustomTypography.textFieldInter15.copy(color = BlackTextColor)
            )
            if (selectedComment.memberId == myInfo.id) {
                IconButton(onClick = {
                    commentViewModel.deleteCommentByTodo(
                        todoId = selectedTodo.id,
                        commentId = selectedComment.commentId
                    )
                }) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = null, tint = BlackTextColor)
                }
            }
        }

    }
}