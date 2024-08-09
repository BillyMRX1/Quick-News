package com.mrx.quicknews.ui.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.mrx.quicknews.core.domain.model.Article
import com.mrx.quicknews.core.domain.model.Source
import com.mrx.quicknews.core.util.getDateToString
import com.mrx.quicknews.navigation.Screen
import com.mrx.quicknews.util.Constant.NEWS_URL

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun NewsCard(article: Article, navController: NavHostController) {
    Column(modifier = Modifier.clickable {
        navController.currentBackStackEntry?.savedStateHandle?.set(
            NEWS_URL,
            article.url
        )
        navController.navigate(route = Screen.DetailNews.route)
    }) {
        Row(modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 8.dp)) {
            Column(
                modifier = Modifier
                    .weight(5f)
                    .padding(end = 8.dp)
            ) {
                Text(
                    text = article.title,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(bottom = 4.dp),
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    "${article.source.name} • ${getDateToString(article.publishedAt)}",
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
            GlideImage(
                model = article.urlToImage,
                contentDescription = article.title,
                modifier = Modifier.weight(4f),
            )
        }
        HorizontalDivider(thickness = 1.dp, modifier = Modifier.padding(top = 8.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun NewsCardPreview() {
    val article = Article(
        source = Source(
            id = "wired",
            name = "Wired"
        ),
        author = "Jessica Klein",
        title = "Bitcoin Bros Go Wild for Donald Trump",
        description = "At Bitcoin 2024 conference in Nashville, Trump is finally telling crypto enthusiasts what they want to hear.",
        url = "https://www.wired.com/story/bitcoin-bros-go-wild-for-donald-trump/",
        urlToImage = "https://media.wired.com/photos/66a56f21bf2909f08a634953/191:100/w_1280,c_limit/Crypto-Bros-Business-2162975355.jpg",
        publishedAt = "2024-07-28T12:43:07Z",
        content = "Trumps speech is an hour behind. A half hour into the wait, restless attendees start chanting Trump. The woman sitting in front of me murmurs her own chant:\r\nBitcoin, bitcointhats what they should be… [+3147 chars]"
    )
    NewsCard(article, rememberNavController())
}