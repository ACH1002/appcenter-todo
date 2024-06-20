package com.example.appcenter_todolist.ui.components.item

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.appcenter_todolist.R
import com.example.appcenter_todolist.model.bucket.BucketResponse
import com.example.appcenter_todolist.navigation.AppNavigationActions
import com.example.appcenter_todolist.ui.components.button.ButtonAfterLogin
import com.example.appcenter_todolist.ui.theme.BlackTextColor
import com.example.appcenter_todolist.ui.theme.BucketItemContainer
import com.example.appcenter_todolist.ui.theme.BucketItemDeleteIcon
import com.example.appcenter_todolist.ui.theme.BucketItemIcon
import com.example.appcenter_todolist.ui.theme.CustomTypography
import com.example.appcenter_todolist.ui.theme.Dimensions
import com.example.appcenter_todolist.viewmodel.BucketViewModel

@Composable
fun BucketItem(
    bucketResponse: BucketResponse,
    bucketViewModel: BucketViewModel,
    appNavigationActions: AppNavigationActions,
    isMine: Boolean = true,
    onClickDetailButton : () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(10.dp),
        color = BucketItemContainer,
        modifier = Modifier
            .padding(start = Dimensions.SidePadding, end = Dimensions.SidePadding, bottom = 23.dp)
            .fillMaxWidth()
            .height(90.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(vertical = 15.dp)
                .fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .weight(1f),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier
                        .padding(start = 15.dp, bottom = 5.dp)
                        .weight(1f)
                        .fillMaxHeight(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    IconButton(
                        onClick = {
                            if (isMine){
                                bucketViewModel.completeBucket(bucketId = bucketResponse.bucketId)
                            }
                        }
                    ) {
                        Icon(
                            painter = painterResource(id = if (bucketResponse.completed) R.drawable.filled_heart else R.drawable.unfilled_heart),
                            contentDescription = null,
                            tint = BucketItemIcon
                        )
                    }
                    Text(
                        text = bucketResponse.content,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                        style = CustomTypography.bodyInter20.copy(color = BlackTextColor.copy(alpha = 0.81f))
                    )
                }
                if (isMine){
                    IconButton(
                        onClick = {
                            bucketViewModel.deleteBucket(bucketId = bucketResponse.bucketId)
                        },
                        modifier = Modifier
                            .padding(bottom = 15.dp)
                            .fillMaxHeight(),
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = null,
                            tint = BucketItemDeleteIcon,
                        )
                    }
                }
            }
            ButtonAfterLogin(
                content = "상세 투두리스트 보기",
                onClick = {
                    onClickDetailButton()
                  },
                modifier = Modifier
                    .padding(horizontal = 25.dp)
                    .height(24.dp),
                roundedCornerDp = 6.dp
            )
        }
    }
}