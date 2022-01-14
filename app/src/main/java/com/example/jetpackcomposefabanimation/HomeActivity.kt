package com.example.jetpackcomposefabanimation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import com.example.jetpackcomposefabanimation.ui.theme.JetpackComposeFabAnimationTheme
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.statusBarsHeight
import com.google.accompanist.insets.systemBarsPadding
import com.google.accompanist.systemuicontroller.rememberSystemUiController

class HomeActivity : ComponentActivity() {

    private val viewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            JetpackComposeFabAnimationTheme {
                ProvideWindowInsets {
                    HomeScreen(viewModel)
                }
            }
        }
    }
}

@Composable
fun HomeScreen(viewModel: HomeViewModel) {

    // Remember a SystemUiController
    val systemUiController = rememberSystemUiController()
    val useDarkIcons = MaterialTheme.colors.isLight

    SideEffect {
        systemUiController.setStatusBarColor(color = Color.Transparent, darkIcons = useDarkIcons)
//        systemUiController.setNavigationBarColor(color = Color.White, darkIcons = useDarkIcons)
    }

    val listItems = viewModel.items

    Scaffold(modifier = Modifier) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xff4DD1E1)),
        ) {
            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                contentPadding = PaddingValues(vertical = 24.dp),
            ) {
                items(listItems.count()) { index ->
                    var item = listItems.elementAt(index)

                    ListItem(model = item)
                }
            }

            CustomAppBar()
        }
    }
}

@Composable
fun CustomAppBar() {
    Card(
        backgroundColor = Color(0xff3DBCD5),
        elevation = 4.dp,
        modifier = Modifier
            .statusBarsHeight(additional = 48.dp)
            .fillMaxWidth()
    ) {
        Box(contentAlignment = Alignment.Center) {

        }
    }
}

@Composable
fun ListItem(model: ListItemModel) {

    var isExpanded by remember {
        mutableStateOf(false)
    }

    Card(
        elevation = 8.dp,
        backgroundColor = Color(0xffE2FAFF),
        shape = RoundedCornerShape(size = 6.dp),
        modifier = Modifier
            .height(84.dp)
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 8.dp)
            .noRippleClickable {
                isExpanded = !isExpanded
            },
    ) {
        Row(modifier = Modifier, verticalAlignment = Alignment.CenterVertically) {
            Surface(modifier = Modifier.padding(16.dp), color = Color.Transparent) {
                CustomArrowIcon(isExpanded = isExpanded)
            }
        }
    }
}

inline fun Modifier.noRippleClickable(crossinline onClick: () -> Unit): Modifier = composed {
    clickable(indication = null,
        interactionSource = remember { MutableInteractionSource() }) {
        onClick()
    }
}

@Composable
fun CustomArrowIcon(isExpanded: Boolean) {

    val animatedDegree by animateFloatAsState(
        targetValue = if (isExpanded) 90f else 0f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioHighBouncy,
            stiffness = 70f
        )
    )

    Canvas(modifier = Modifier.size(16.dp)) {
        rotate(animatedDegree) {
            val arrowPath = Path().let {
                it.moveTo(3 * this.size.width / 10, 1 * this.size.height / 8)
                it.lineTo(7 * this.size.width / 10, this.size.height / 2)
                it.lineTo(3 * this.size.width / 10, 7 * this.size.height / 8)
//                it.close()
                it
            }

            drawPath(
                path = arrowPath,
                style = Stroke(width = 10f, join = StrokeJoin.Round, cap = StrokeCap.Round),
                color = Color(0xff7DD6DE)
            )
        }
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