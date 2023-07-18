package com.json.dondeligia

import android.content.ClipData
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.FileProvider
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.BuildConfig
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import com.json.dondeligia.ui.theme.DondeLigiaTheme
import java.io.File
import java.util.Objects

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //storageSetting()
        getCatalogs()
        setContent {
            DondeLigiaTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                }
            }
        }
    }

    fun getCatalogs(){
        val database = Firebase.database
        val reference = database.getReference("brands")
        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val data = snapshot.value
                Toast.makeText(applicationContext, data.toString(), Toast.LENGTH_SHORT).show()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(applicationContext, error.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun storageSetting(){
        val storage = Firebase.storage
        val reference = storage.getReferenceFromUrl("gs://donde-ligia.appspot.com/2023-06CR.pdf")
        val tempFile = File.createTempFile("catalog-", ".pdf")
        reference.getFile(tempFile).addOnSuccessListener {
            val fileUri = FileProvider.getUriForFile(Objects.requireNonNull(applicationContext), "com.json.dondeligia.provider", tempFile)
            Log.d("MAIN-ACTIVITY", "File path: ${fileUri.path}")
            Toast.makeText(applicationContext, "PDF Downloaded", Toast.LENGTH_SHORT).show()
            val pdfView = Intent(Intent.ACTION_VIEW)
            pdfView.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            pdfView.clipData = ClipData.newRawUri("", fileUri)
            pdfView.setDataAndType(fileUri, "application/pdf")
            pdfView.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
            startActivity(pdfView)
        }.addOnFailureListener{
            Toast.makeText(applicationContext, "PDF not Downloaded", Toast.LENGTH_SHORT).show()
        }
    }

}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    DondeLigiaTheme {
        Greeting("Android")
    }
}