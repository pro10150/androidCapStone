package com.example.littlelemon

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.littlelemon.ui.theme.FundamentalGray
import com.example.littlelemon.ui.theme.HighlightGray
import com.example.littlelemon.ui.theme.PrimaryGreen
import com.example.littlelemon.ui.theme.PrimaryYellow
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json

@Composable
fun Home(context: Context, navController: NavController) {
    val database by lazy {
        Room.databaseBuilder(context = context, AppDatabase::class.java, "database").build()
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        val databaseMenuItems by database.menuItemDao().getAll().observeAsState(emptyList())
        var menuItems: List<MenuItemRoom> = databaseMenuItems
        Header(navController = navController)
        Hero()
        Detail(menuItems = menuItems)
    }
}

@Composable
fun Header(navController: NavController) {
    Row(horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically, modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 10.dp)) {

        Spacer(modifier = Modifier.weight(1f))
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .padding(20.dp)
                .height(40.dp)
        )

        Spacer(modifier = Modifier.weight(1f))
        Image(
            painter = painterResource(id = R.drawable.profile),
            contentDescription = "profile",
            modifier = Modifier
                .size(50.dp)
                .fillMaxSize()
                .clickable {
                    navController.navigate(Profile.route)
                },
        )
    }
}

@Composable
fun Hero() {
    var searchPhrase by remember {
        mutableStateOf("")
    }
    Column(modifier = Modifier
        .background(PrimaryGreen)
        .padding(horizontal = 20.dp, vertical = 20.dp)) {
        Text(
            text = "Little Lemon",
            style = MaterialTheme.typography.headlineLarge,
            color = PrimaryYellow
        )
        Text(
            text = "Chicago",
            style = MaterialTheme.typography.headlineMedium,
            color = HighlightGray,
        )
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween) {
            Text(
                text = "We are a family-owned Mediterranean restaurant, focused on traditional recipes served with a modern twist",
                style = MaterialTheme.typography.bodySmall,
                color = HighlightGray,
                modifier = Modifier.fillMaxWidth(0.7f)
            )
            Image(
                painter = painterResource(id = R.drawable.hero_image),
                contentDescription = "Hero Image",
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
            )

        }
        OutlinedTextField(value = searchPhrase,
            onValueChange = {
                searchPhrase = it
            },
            placeholder = {
                Text(
                    text = "Enter Search Phrase",
                    style = MaterialTheme.typography.bodySmall
                )
            },
            textStyle = TextStyle(fontSize = 16.sp),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search Icon")
            },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                disabledContainerColor = Color.White
            ),
            modifier = Modifier.fillMaxWidth())
    }
}

@Composable
fun Detail(menuItems: List<MenuItemRoom>) {
    Column(modifier = Modifier.padding(horizontal = 10.dp, vertical = 20.dp)) {
        Text(
            text = "ORDER FOR DELIVERY!",
            style = MaterialTheme.typography.bodyLarge
        )
        MenuCategoryDetails(menuItems = menuItems)
        Divider(
            color = FundamentalGray,
            thickness = 1.dp
        )
        MenuItemDetails(menuItems = menuItems)
    }
}

@Composable
fun MenuCategoryDetails(menuItems: List<MenuItemRoom>) {
    val categories = menuItems.map {
        it.category.replaceFirstChar { character ->
            character.uppercase()
        }
    }.toSet()
    val categoryList = categories.toList()

    LazyRow() {
        items(
            items = categoryList,
            itemContent = { category ->
                MenuCategory(category = category)
            }
        )
    }
}

@Composable
fun MenuCategory(category: String) {
    Button(
        onClick = { /*TODO*/ },
        modifier = Modifier.padding(horizontal = 5.dp),
        colors = ButtonDefaults.buttonColors(Color.LightGray),
        shape = RoundedCornerShape(15.dp)
    ) {
        Text(
            text = category,
            color = PrimaryGreen,
            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.ExtraBold)
        )
    }
}

@Composable
fun MenuItemDetails(menuItems: List<MenuItemRoom>) {
    LazyColumn() {
        items(
            items = menuItems,
            itemContent = { menuItem ->
                MenuItems(menuItem)
            }
        )
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MenuItems(menuItem: MenuItemRoom) {
    Column(modifier = Modifier.padding(vertical = 10.dp)) {
        Text(
            text = menuItem.title,
            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween) {
            Column(modifier = Modifier
                .fillMaxWidth(0.7f)
                .padding(10.dp)) {
                Text(
                    text = menuItem.description,
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = String.format("$%.2f", menuItem.price),
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(vertical = 10.dp)
                )
            }
            GlideImage(
                model = menuItem.image,
                contentDescription = menuItem.title
            )

        }
        Divider(color = HighlightGray, thickness = 1.dp)
    }
}

@Preview
@Composable
fun HomePreview() {
    val navController = rememberNavController()
    val context = LocalContext.current
    Home(context = context, navController = navController)
}