package com.example.appcenter_todolist.ui.components.dialog

import android.annotation.SuppressLint
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
import com.example.appcenter_todolist.model.bucket.AddBucketRequest
import com.example.appcenter_todolist.ui.components.button.ButtonAfterLogin
import com.example.appcenter_todolist.ui.components.textfield.ContentTextField
import com.example.appcenter_todolist.ui.theme.Background
import com.example.appcenter_todolist.ui.theme.BlackTextColor
import com.example.appcenter_todolist.ui.theme.CustomTypography
import com.example.appcenter_todolist.ui.theme.Dimensions
import com.example.appcenter_todolist.viewmodel.BucketViewModel
import es.dmoral.toasty.Toasty

@SuppressLint("CheckResult")
@Composable
fun AddBucketDialog(
    setAddBucketDialog: (Boolean) -> Unit,
    bucketViewModel: BucketViewModel
){
    val context = LocalContext.current

    var addBucketContent by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue())
    }

    val addBucketState by bucketViewModel.addBucketState.collectAsState()

    Dialog(
        onDismissRequest = { setAddBucketDialog(false) },
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
                    content = addBucketContent,
                    setContent = { addBucketContent = it }
                )
                Spacer(modifier = Modifier.height(22.dp))
                ButtonAfterLogin(
                    content = "등록하기",
                    onClick = {
                        bucketViewModel.addBucket(
                            addBucketRequest = AddBucketRequest(
                                content = addBucketContent.text,
                                deadLine = "2024-05-25"
                            )
                        )
                    },
                    modifier = Modifier.height(57.dp)
                )
            }
        }
    }

    LaunchedEffect(addBucketState){
        if (addBucketState == true){
            addBucketContent = TextFieldValue("")
            bucketViewModel.clearAddBucketState()
            Toasty.success(context, "버킷 만들기 성공", Toast.LENGTH_SHORT).show()
            setAddBucketDialog(false)
        } else if (addBucketState == false){
            addBucketContent = TextFieldValue("")
            bucketViewModel.clearAddBucketState()
            Toasty.error(context, "버킷 만들기 실패", Toast.LENGTH_SHORT).show()
            setAddBucketDialog(false)
        }
    }
}