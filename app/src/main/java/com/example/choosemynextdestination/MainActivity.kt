package com.example.choosemynextdestination

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.rememberSplineBasedDecay
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.AndroidUriHandler
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.choosemynextdestination.data.Destination
import com.example.choosemynextdestination.data.destinations
import com.example.choosemynextdestination.ui.theme.ChooseMyNextDestinationTheme

/*
苦労したこと
・1つのCardをタップしたら、すでに開いているカードが閉じるという仕組みを完成させるのに手間取った。
最初、cardをタップしたら別のcardが一斉に開いたので、keyの認識が出来ていないのではと考えた。次に、
スコープに間違いがあるのではと思い、ScopeProviderを設置してみたが改善せず。結果はshowDetailsが
true/falseの判定で、showDetailsがtrueになると一斉にオープンになる状態が原因で、destinationの
dayIntで判定すると、欲しい形になった。
・アニメーションでweightを使っていると、そのサイズ調整が最後に行われて、ピコっと動いてしまっていた。
そこで、テキストにはアニメーションをつけず、テキストをラップしているRowにアニメーションをつけることで
改善した。
・画質が原因でLazy Columnのスクロールががたついた
 */

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalSharedTransitionApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ChooseMyNextDestinationTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    DestinationApp()
                }
            }
        }
    }
}

@ExperimentalSharedTransitionApi
@Composable
private fun DestinationApp() {
    val context = LocalContext.current
    SharedTransitionLayout {
        Column(modifier = Modifier.fillMaxSize()) {
            NextTopAppBar()
            LazyColumn(
                modifier = Modifier
                    .padding(horizontal = 32.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(32.dp),
                contentPadding = PaddingValues(bottom = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding())
            ) {
                items(
                    destinations,
                    key = {destination -> destination.dayInt}
                ) { destination ->
                    Card(destination, context = context)
                }
            }
        }
    }
}

@Composable
fun flingBehavior(): FlingBehavior {
    val flingSpec = rememberSplineBasedDecay<Float>()
    return remember(flingSpec) { DefaultFlingBehavior(flingSpec) }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun Card(
    destination: Destination,
    modifier: Modifier = Modifier,
    context: Context
) {
    var showDetails by rememberSaveable { mutableStateOf(false) }
    val destinationId = destination.dayInt.toString()

    SharedTransitionLayout() {
        AnimatedContent(
            modifier = Modifier.fillMaxSize(),
            targetState = showDetails,
            label = destinationId
        ) { targetState ->
            if (!targetState) {
                println(showDetails)
                ClosedDestinationCard(
                    destination = destination,
                    onShowDetails = {
                        showDetails = !showDetails
                    },
                    sharedTransitionScope = this@SharedTransitionLayout,
                    animatedVisibilityScope = this@AnimatedContent,
                    modifier = modifier
                )
            } else {
                OpenedDestinationCard(
                    destination = destination,
                    onShowDetails = {
                        showDetails = !showDetails
                    },
                    context = context,
                    sharedTransitionScope = this@SharedTransitionLayout,
                    animatedVisibilityScope = this@AnimatedContent,
                    modifier = modifier
                )
            }
        }
    }
}

@Stable
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NextTopAppBar(
    modifier: Modifier = Modifier
) {
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface,
            titleContentColor = MaterialTheme.colorScheme.primary
        ),
        title = {
            Text(
                text = stringResource(R.string.app_name),
                style = MaterialTheme.typography.displayLarge,
            )
        },
        modifier = modifier
            .padding(bottom = 32.dp, top = 32.dp)
    )
}


@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun ClosedDestinationCard(
    modifier: Modifier = Modifier,
    destination: Destination,
    onShowDetails: () -> Unit,
    sharedTransitionScope: SharedTransitionScope ,
    animatedVisibilityScope: AnimatedVisibilityScope
) {
    with(sharedTransitionScope) {
        Card(
            modifier = modifier
                .clip(RoundedCornerShape(16.dp))
                .fillMaxWidth()
                .height(88.dp)
                .sharedElement(
                    rememberSharedContentState(key = "card${destination.dayInt}"),
                    animatedVisibilityScope = animatedVisibilityScope
                )
                .clickable{ onShowDetails() }

        ) {
            Row(
                modifier = modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                    .height(72.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Day ${destination.dayInt}",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = modifier
                        .sharedElement(
                            rememberSharedContentState(key = "day${destination.dayInt}"),
                            animatedVisibilityScope = animatedVisibilityScope
                        )
                        .wrapContentSize()
                )
                Text(
                    text = stringResource(destination.nameRes),
                    style = MaterialTheme.typography.titleLarge,
                    maxLines = 1,
                    modifier = modifier
                        .sharedElement(
                            rememberSharedContentState(key = "name${destination.dayInt}"),
                            animatedVisibilityScope = animatedVisibilityScope
                        )
                        .weight(1F)
                        .horizontalScroll(rememberScrollState())
                        .padding(end = 8.dp)
                        .wrapContentSize()
                )
                Image(
                    painter = painterResource(destination.imageRes),
                    contentDescription = null,
                    modifier = modifier
                        .sharedElement(
                            rememberSharedContentState(key = "image${destination.dayInt}"),
                            animatedVisibilityScope = animatedVisibilityScope
                        )
                        .size(72.dp)
                        .clip(RoundedCornerShape(16.dp)),
                    contentScale = ContentScale.Crop
                )

            }
        }
    }
}


@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun OpenedDestinationCard(
    modifier: Modifier = Modifier,
    destination: Destination,
    onShowDetails: () -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    context: Context
) {
    with(sharedTransitionScope) {
        Card(
            modifier = modifier
                .clip(RoundedCornerShape(16.dp))
                .fillMaxWidth()
                .wrapContentSize()
                .sharedElement(
                    rememberSharedContentState(key = "card${destination.dayInt}"),
                    animatedVisibilityScope = animatedVisibilityScope
                )
                .clickable { onShowDetails() }
        ) {
            val url = if(destination.urlRes != null) stringResource(destination.urlRes) else null
            val noMap = stringResource(R.string.noMap)

            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Day ${destination.dayInt}",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = modifier
                        .sharedElement(
                            rememberSharedContentState(key = "day${destination.dayInt}"),
                            animatedVisibilityScope = animatedVisibilityScope
                        )
                        .wrapContentSize()
                )
                Spacer(modifier = Modifier.height(32.dp))
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .sizeIn(maxHeight = 32.dp)
                ) {
                    Text(
                        text = stringResource(destination.nameRes),
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = modifier
                            .sharedElement(
                                rememberSharedContentState(key = "name${destination.dayInt}"),
                                animatedVisibilityScope = animatedVisibilityScope
                            )
                            .weight(1F)
                            .wrapContentSize()
                            .padding(end = 8.dp)
                            .horizontalScroll(rememberScrollState())
                    )
                    AssistChip(
                        onClick = {
                            if(url == "null") { Toast.makeText(context,noMap, Toast.LENGTH_LONG).show() }
                            else if(url != null) {AndroidUriHandler(context).openUri(url) }
                        },
                        label = {
                            Text(
                                text = stringResource(destination.areaRes),
                                style = MaterialTheme.typography.labelLarge
                            )
                        },
                        leadingIcon = {
                            Icon(
                                Icons.Rounded.LocationOn,
                                contentDescription = null
                            )
                        },
                        modifier = Modifier.wrapContentSize()
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Image(
                    painter = painterResource(destination.imageRes),
                    contentDescription = null,
                    modifier = modifier
                        .sharedElement(
                            rememberSharedContentState(key = "image${destination.dayInt}"),
                            animatedVisibilityScope = animatedVisibilityScope
                        )
                        .fillMaxWidth()
                        .wrapContentSize()
                        .sizeIn(maxHeight = 240.dp)
                        .clip(RoundedCornerShape(16.dp)),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = stringResource(destination.explanationRes),
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.wrapContentSize()
                )
            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Preview
@Composable
fun DestinationAppPreview() {
    ChooseMyNextDestinationTheme {
        DestinationApp()
    }
}
