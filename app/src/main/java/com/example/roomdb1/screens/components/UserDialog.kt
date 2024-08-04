package com.example.roomdb1.screens.components

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.roomdb1.R
import com.example.roomdb1.viewModels.UserVM

@Composable
fun UserDialog(alertAction : (Boolean) -> Unit){
    val vm : UserVM = viewModel()
    val user = vm.user.collectAsState()
    val username = user.value?.username
    val email = user.value?.email

    val context = LocalContext.current

    var bitmap by remember {
        mutableStateOf<Bitmap?>(null)
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let { it ->
            bitmap = if(Build.VERSION.SDK_INT < 28){
                MediaStore.Images.Media.getBitmap(context.contentResolver,it)
            }else{
                val source = ImageDecoder.createSource(context.contentResolver,it)
                ImageDecoder.decodeBitmap(source)
            }
            vm.setImage(bitmap)
        }
    }


    
    Dialog(onDismissRequest = { }) {
        Card {
            Column(
                modifier = Modifier.padding(10.dp)
            ) {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ){
                    when(bitmap){
                        null -> {
                            Image(
                                painter = painterResource(R.drawable.profile),
                                contentDescription = "Profile",
                                modifier = Modifier
                                    .size(100.dp)
                                    .clip(CircleShape)
                                    .background(Color.Transparent)
                                    .border(BorderStroke(2.dp, Color.Gray), CircleShape),
                                contentScale = ContentScale.FillWidth,
                                alignment = Alignment.Center
                            )
                        }
                        else -> {
                            Image(
                                bitmap = bitmap?.asImageBitmap()!!,
                                contentDescription = "Profile",
                                modifier = Modifier
                                    .size(100.dp)
                                    .clip(CircleShape)
                                    .background(Color.Transparent)
                                    .border(BorderStroke(2.dp, Color.Gray), CircleShape),
                                contentScale = ContentScale.FillWidth,
                                alignment = Alignment.Center
                            )
                        }
                    }

                    IconButton(
                        onClick = {
                            when(bitmap){
                                null -> {
                                    launcher.launch("image/*")
                                }
                                else -> {
                                    vm.setImage(null)
                                    bitmap = null
                                }
                            }

                        },
                        modifier = Modifier.align(Alignment.Center).padding(start = 60.dp, top = 25.dp)
                    ) {
                        Icon(
                            painter = painterResource(
                                when(bitmap){
                                    null -> {
                                        R.drawable.camera
                                    }
                                    else -> {
                                        R.drawable.trash
                                    }
                                }

                            ),
                            contentDescription = "Camera",
                            modifier = Modifier.size(30.dp)
                        )
                    }
                }
                OutlinedTextField(
                    value = username.toString(),
                    onValueChange = {
                        vm.setValue("username",it)
                    },
                    label = {
                        Text(text = "Username")
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                SpacerHeight(space = 10.dp)
                OutlinedTextField(
                    value = email.toString(),
                    onValueChange = {
                        vm.setValue("email",it)
                    },
                    label = {
                            Text(text = "Email")
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                SpacerHeight(space = 10.dp)
                Button(
                    onClick = {
                              vm.addUser()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(0.dp))
                    ) {
                    Text(text = "Add")
                }

                Button(
                    onClick = {
                        alertAction(false)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(0.dp))
                ) {
                    Text(text = "Close")
                }

            }
        }
    }
}