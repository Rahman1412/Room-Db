package com.example.roomdb1.screens

import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.roomdb1.R
import com.example.roomdb1.models.Employee
import com.example.roomdb1.models.User
import com.example.roomdb1.screens.components.UserDialog
import com.example.roomdb1.viewModels.UserVM
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(){
    val context = LocalContext.current.applicationContext
    val lifecycleOwner : LifecycleOwner = LocalLifecycleOwner.current

    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
    }

    LaunchedEffect(true) {
        delay(2000)
        requestPermissionLauncher.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)
    }

    val vm : UserVM = viewModel()
    var isDialog by rememberSaveable {
        mutableStateOf(false)
    }

    val alertAction : (Boolean) -> Unit = { value:Boolean ->
        Log.d("Value",value.toString())
        isDialog = value
    }

    val allUser by vm.allUser.collectAsState(initial = emptyList())
    val allEmployee by vm.allEmployee.collectAsState(initial = emptyList())

    DisposableEffect(lifecycleOwner) {

        val observer = LifecycleEventObserver{ _,event ->
            if (event == Lifecycle.Event.ON_START) {
                Log.d("Started","Started")
            } else if (event == Lifecycle.Event.ON_STOP) {
                Log.d("Stopped","Stopped")
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text("Users")
                },
                actions = {
                    IconButton(onClick = {
                        alertAction(true)
                    }) {
                        Icon(Icons.Filled.AddCircle, contentDescription = "Add")
                    }
                }
            )
        },
    ){paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            if(isDialog){
                UserDialog(alertAction)
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                allEmployee.forEach { item ->
                    Employee(item)
                }
                allUser.forEach { item ->
                    User(item)
                }
            }
        }
    }
}

@Composable
fun User(user: User){
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
    ){
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(bitmap = user.image?.asImageBitmap()!!, contentDescription = "", modifier = Modifier.size(100.dp))
            Column(
                modifier = Modifier.padding(10.dp)
            ) {
                Text(text = user.id.toString())
                Text(text = "USER "+user.username)
                Text(text = user.email)
            }
        }
    }
}

@Composable
fun Employee(user: Employee){
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
    ){
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(bitmap = user.image?.asImageBitmap()!!, contentDescription = "", modifier = Modifier.size(100.dp))
            Column(
                modifier = Modifier.padding(10.dp)
            ) {
                Text(text = user.id.toString())
                Text(text = "EMP "+user.username)
                Text(text = user.email)
            }
        }
    }
}