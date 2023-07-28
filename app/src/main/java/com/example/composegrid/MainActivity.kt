package com.example.composegrid

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardDefaults.cardColors
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
    var selectedImageId by remember { mutableStateOf(-1) }
    var selectedNameValue by remember { mutableStateOf("") }
    // Generate the list of images and names as key-value pairs
    val imageNamesMap = generateImageNamesMap()
    val randomKeys by remember { mutableStateOf(imageNamesMap.values.toList().shuffled()) }
    var selectedIndex by remember { mutableStateOf(-1) }

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
            CardColumn(
                imageNamesMap.keys.toList(),
                isNamesColumn = false,
                selectedId = selectedImageId,
                onCardClick = { clickedImageId ->
                    // Check if the clicked image card is different from the currently selected image card
                    if (clickedImageId != selectedImageId) {
                        selectedImageId = clickedImageId as Int
                        selectedNameValue = "" // Reset the selected name value
                    }
                }
            )

            // Second column for names (values)
            CardColumn(
                randomKeys,
                isNamesColumn = true,
                selectedId = selectedNameValue,
                selectedIndex = selectedIndex,
                onCardClick = {
                    if (imageNamesMap.getOrDefault(selectedImageId, "") == it) {
                        selectedNameValue = it // Unique ID for name
                        selectedIndex = -1
                    } else {
                        selectedNameValue = "" // Reset if it's incorrect
                        selectedIndex = randomKeys.indexOf(it)
                    }
                }
            )
        }
    }
}


@Composable
fun CardColumn(
    dataImage: List<Any>,
    isNamesColumn: Boolean = false,
    selectedId: Any,
    selectedIndex: Int = -1,
    onCardClick: (Any) -> Unit
) {
    LazyColumn(
        modifier = Modifier.padding(start = if (isNamesColumn) 16.dp else 0.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        itemsIndexed(dataImage) {index, item ->
            val isSelected = if (isNamesColumn) {
                item.toString() == selectedId as String
            } else {
                item as Int == selectedId as Int
            }

            Card(
                modifier = Modifier
                    .size(100.dp)
                    .padding(start = 30.dp, top =    20.dp)
                    .clickable {
                        onCardClick(item)
                    },
                colors = cardColors( containerColor = when {
                    isSelected -> Color.Green
                    else -> if (selectedIndex != -1 && selectedIndex == index) Color.Red else Color.LightGray
                })
            ) {
                if (isNamesColumn) {
                    NameItem(name = item as String, isSelected = isSelected)
                } else {
                    ImageItem(imageResId = item as Int, isSelected = isSelected)
                }
            }
        }
    }
}

fun generateImageNamesMap(): Map<Int, String> {
    // Add image resources to a list
    val images = listOf(
        R.drawable.we,
        R.drawable.what,
        R.drawable.how,
        R.drawable.he,
        // Add more image resources as needed
    )

    // Add image names to a list in random order
    val imageNames = listOf(
        "we",
        "what",
        "how",
        "he",
        // Add more image names corresponding to the resources
    )

    // Use the 'zip' function to pair the images with the shuffled names and then convert it to a map
    return images.zip(imageNames).toMap()
}

@Composable
fun ImageItem(imageResId: Int, isSelected: Boolean) {
    val painter: Painter = painterResource(id = imageResId)
    Image(
        painter = painter,
        contentDescription = null,
        modifier = Modifier
            .size(120.dp)
            .padding(8.dp),
        contentScale = ContentScale.Crop,
        alignment = if (isSelected) Alignment.TopCenter else Alignment.Center
    )
}

@Composable
fun NameItem(name: String, isSelected: Boolean) {
    Text(
        text = name,
        style = TextStyle(
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        ),
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ComposeGridTheme {

        PlayScreen()

    }
}