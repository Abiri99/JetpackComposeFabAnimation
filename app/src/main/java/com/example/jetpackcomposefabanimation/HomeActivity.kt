package com.example.jetpackcomposefabanimation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.material.Surface
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpackcomposefabanimation.ui.theme.JetpackComposeFabAnimationTheme

class HomeActivity : ComponentActivity() {

    private val viewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackComposeFabAnimationTheme {
                HomeScreen(viewModel)
            }
        }
    }
}

@Composable
fun HomeScreen(viewModel: HomeViewModel) {

    val listItems = viewModel.items

    Scaffold(modifier = Modifier) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xff4DD1E1)),
        ) {
            LazyColumn(
                modifier = Modifier,
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                contentPadding = PaddingValues(16.dp),
            ) {
                items(listItems.count()) { index ->
                    var item = listItems.elementAt(index)

                    ListItem(model = item)
                }
            }
        }
    }
}

@Composable
fun ListItem(model: ListItemModel) {
    Surface(
        shape = RoundedCornerShape(size = 12.dp),
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .height(64.dp)
            .background(color = Color(0xffE1FBFD)),
    ) {
        Text(text = model.isExpanded.toString())
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {

    val items =
        listOf<ListItemModel>(ListItemModel(), ListItemModel(), ListItemModel(), ListItemModel())

    JetpackComposeFabAnimationTheme {
        Scaffold(modifier = Modifier) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xff4DD1E1)),
            ) {
                LazyColumn(
                    modifier = Modifier,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    contentPadding = PaddingValues(16.dp),
                ) {
                    items(items.count()) { index ->
                        var item = items.elementAt(index)

                        Text(text = index.toString())
                    }
                }
            }
        }
    }
}