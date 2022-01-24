package com.example.jetpackcomposefabanimation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.core.view.WindowCompat
import com.example.jetpackcomposefabanimation.ui.theme.JetpackComposeFabAnimationTheme
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.statusBarsHeight
import com.google.accompanist.insets.statusBarsPadding
import com.google.accompanist.insets.systemBarsPadding
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import android.view.WindowManager

import android.os.Build
import android.view.View
import android.view.Window
import androidx.compose.animation.core.*


class HomeActivity : ComponentActivity() {

    private val viewModel: HomeViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val window: Window = window
        //show content behind status bar
        window?.decorView?.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//        WindowCompat.setDecorFitsSystemWindows(window, false)
//        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
//            WindowManager.LayoutParams.SOFT_INPUT_MASK_ADJUST)

        setContent {
            JetpackComposeFabAnimationTheme {
                ProvideWindowInsets {

                    val systemUiController = rememberSystemUiController()
                    val useDarkIcons = MaterialTheme.colors.isLight
                    SideEffect {
                        systemUiController.setStatusBarColor(
                            color = Color.Transparent,
                            darkIcons = useDarkIcons
                        )
                    }

                    HomeScreen(viewModel)
                }
            }
        }
    }
}

@Composable
fun HomeScreen(viewModel: HomeViewModel) {

    val listItems = viewModel.items

    var appBarAdditionalHeight = 48.dp

    Scaffold(modifier = Modifier) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xff4DD1E1)),
        ) {
            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                contentPadding = PaddingValues(
                    start = 8.dp,
                    end = 8.dp,
                    bottom = 64.dp,
                    top = 16.dp
                ),
                modifier = Modifier
                    .statusBarsPadding()
                    .padding(top = appBarAdditionalHeight)
            ) {
                items(listItems.count()) { index ->
                    var item = listItems.elementAt(index)

                    ListItem(model = item)
                }
            }

            CustomAppBar(appBarAdditionalHeight = appBarAdditionalHeight) {
                Line(
                    color = Color(0xff60D2DD),
                    modifier = Modifier
                        .padding(bottom = 16.dp)
                        .width(120.dp),
                    height = 12.dp,
                )
                DrawerButton(modifier = Modifier
                    .padding(bottom = 10.dp, start = 20.dp)
                    .align(alignment = Alignment.BottomStart))
            }

        }
    }
}

@Composable
fun Line(color: Color = Color(0xff60D2DD), height: Dp = 12.dp, modifier: Modifier = Modifier) {
    Surface(
        shape = RoundedCornerShape(size = 1000.dp),
        color = color,
        modifier = modifier
            .fillMaxWidth()
            .height(height),
    ) {
    }
}

@Composable
fun DrawerButton(color: Color = Color(0xff60D2DD), modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .width(30.dp)
            .height(28.dp),
    ) {
        repeat(3) {
            Line(color = color, height = 5.dp)
        }
    }
}

@Composable
fun CustomAppBar(appBarAdditionalHeight: Dp, child: @Composable() () -> Unit) {
    Card(
        backgroundColor = Color(0xff3DBCD5),
        elevation = 4.dp,
        modifier = Modifier
            .statusBarsHeight(additional = appBarAdditionalHeight)
            .fillMaxWidth()
            .zIndex(1f),
    ) {
        Box(
            contentAlignment = Alignment.BottomCenter,
            modifier = Modifier
        ) {
            child()
        }
    }
}

enum class ListItemState {
    Collapsed,
    Expanded
}

@Composable
fun ListItem(model: ListItemModel) {

    var currentState by remember {
        mutableStateOf(ListItemState.Collapsed)
    }
    
    val transition = updateTransition(targetState = currentState, label = "list_item_transition")

    val arrowRotationDegree by transition.animateFloat(label = "arrow_rotation_degree", transitionSpec = {
        spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = 70f
        )
    }) { state ->
        when (state) {
            ListItemState.Collapsed -> 0f
            ListItemState.Expanded -> 90f
        }
    }

    val cardHorizontalPadding by transition.animateDp(label = "card_horizontal_padding", transitionSpec = {
        spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = 70f
        )
    }) { state ->
        when (state) {
            ListItemState.Collapsed -> 20.dp
            ListItemState.Expanded -> 8.dp
        }
    }

    val cardHeight by transition.animateDp(label = "card_height", transitionSpec = {
        spring(
            dampingRatio = Spring.DampingRatioLowBouncy,
            stiffness = 70f
        )
    }) { state ->
        when (state) {
            ListItemState.Collapsed -> 88.dp
            ListItemState.Expanded -> 250.dp
        }
    }

    val cardElevation by transition.animateDp(label = "card_elevation") { state ->
        when (state) {
            ListItemState.Collapsed -> 0.dp
            ListItemState.Expanded -> 12.dp
        }
    }

    Card(
        elevation = cardElevation,
        backgroundColor = Color(0xffE2FAFF),
        shape = RoundedCornerShape(size = 10.dp),
        modifier = Modifier
            .height(cardHeight)
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = cardHorizontalPadding)
            .noRippleClickable {
                currentState = when (currentState) {
                    ListItemState.Expanded -> ListItemState.Collapsed
                    ListItemState.Collapsed -> ListItemState.Expanded
                }
            },
    ) {
        Row(modifier = Modifier, verticalAlignment = Alignment.Top) {
            Surface(modifier = Modifier.height(72.dp).padding(16.dp).wrapContentHeight(), color = Color.Transparent) {
                CustomArrowIcon(arrowRotationDegree)
            }
            Column(verticalArrangement = Arrangement.Top) {
                Row(verticalAlignment = Alignment.Bottom, modifier = Modifier.height(36.dp)) {
                    Line(color = Color(0xff84D6DE), height = 12.dp, modifier = Modifier.width(200.dp).padding(bottom = 6.dp))
                }
                Row(verticalAlignment = Alignment.Top, modifier = Modifier.height(36.dp)) {
                    Line(color = Color(0xffAEE7EF), height = 12.dp, modifier = Modifier
                        .padding(top = 6.dp, bottom = 8.dp, end = 16.dp)
                        .weight(1f))
                    Line(color = Color(0xffAEE7EF), height = 12.dp, modifier = Modifier
                        .padding(top = 6.dp, bottom = 8.dp, end = 48.dp)
                        .weight(2f))
                }
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
fun CustomArrowIcon(arrowRotationDegree: Float) {

    Canvas(modifier = Modifier.size(16.dp)) {
        rotate(arrowRotationDegree) {
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