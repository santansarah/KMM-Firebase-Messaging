package com.santansarah.kmmfirebasemessaging.android.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.santansarah.kmmfirebasemessaging.SharedRes
import dev.icerock.moko.resources.ColorResource
import androidx.compose.ui.res.colorResource

val APP_COLORS = SharedRes.colors

@Composable
fun colorResourceFromId(resource: ColorResource) = colorResource(resource.resourceId)

@Composable
fun ColorResource.toColor() = colorResource(this.resourceId)