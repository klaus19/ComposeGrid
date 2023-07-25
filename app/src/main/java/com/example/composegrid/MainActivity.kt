package com.example.composegrid

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.composegrid.ui.theme.ComposeGridTheme

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeGridTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    PlayScreen()
                }
            }
        }
    }
}



@Composable
fun PlayScreen() {
    // Generate the list of images and names as key-value pairs
    val imageNamesMap = generateImageNamesMap()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        // Center the Row in the screen
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // First column for images (keys)
            CardColumn(imageNamesMap.keys.toList())

            // Second column for names (values)
            CardColumn(imageNamesMap.values.toList(), true)
        }
    }
}



@Composable
fun CardColumn(items: List<Any>, isNamesColumn: Boolean = false) {
    LazyColumn(
        modifier = Modifier.padding(start = if (isNamesColumn) 16.dp else 0.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(items) { item ->
            Card(modifier = Modifier.size(100.dp).padding(start = 30.dp, top = 20.dp)) {
                if (isNamesColumn) {
                    NameItem(name = item as String)
                } else {
                    ImageItem(imageResId = item as Int)
                }
            }
        }
    }
}


fun generateImageNamesMap(): HashMap<Int, String> {
    // Add image resources to a list
    val images = listOf(
        R.drawable.he,
        R.drawable.how,
        R.drawable.what,
        R.drawable.we,
        // Add more image resources as needed
    )

    // Add image names to a list in random order
    val imageNames = listOf(
        "Name1",
        "Name2",
        "Name3",
        "Name4",
        "Name5"
        // Add more image names corresponding to the resources
    )

    // Shuffle the image names list randomly
    val shuffledNames = imageNames.shuffled()

    // Use the shuffled names to assign each image resource ID a corresponding name in the imageNamesMap
    val imageNamesMap = HashMap<Int, String>()
    for (i in images.indices) {
        imageNamesMap[images[i]] = shuffledNames[i]
    }

    return imageNamesMap
}

@Composable
fun ImageItem(imageResId: Int) {
    val painter: Painter = painterResource(id = imageResId)
    Image(
        painter = painter,
        contentDescription = null, // Provide proper content description if needed
        modifier = Modifier
            .size(120.dp)
            .padding(8.dp)
    )
}

@Composable
fun NameItem(name: String) {
    Text(
        text = name,
        style = TextStyle(
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.typography.bodyMedium.color,
            textAlign = TextAlign.Center
        ),
        modifier = Modifier
            .padding(8.dp)
    )
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ComposeGridTheme {

        PlayScreen()

    }
}