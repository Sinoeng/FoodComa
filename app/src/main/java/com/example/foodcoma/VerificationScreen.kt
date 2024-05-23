package com.example.foodcoma

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType


@Composable
fun VerificationScreen(
    verify: () -> Unit
) {
    Button(
        onClick = verify,
//        colors = ButtonColors(
//            containerColor = Color.Blue,
//            contentColor = Color.White,
//            disabledContainerColor = Color.Gray,
//            disabledContentColor = Color.White,
//        ),
        modifier = Modifier
            .fillMaxSize(0.5f)
            .aspectRatio(16f / 9f)
    ) {
        Text(
            text = stringResource(R.string.verify),
            fontSize = TextUnit(value = 46f, type = TextUnitType.Sp),

        )
    }
}