package com.likith.presentation.books

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.likith.domain.model.BookSummary
import com.likith.domain.util.DataError
import com.likith.domain.util.DeviceConfiguration
import com.likith.presentation.design_system.CustomImageView
import com.likith.presentation.R
import com.likith.presentation.util.BookAppConst
import com.likith.presentation.util.toMessageRes

@Composable
fun BookRootView(modifier: Modifier) {

    val bookViewModel: BookViewModel = hiltViewModel()

    val uiState = bookViewModel.bookUiState.collectAsStateWithLifecycle()

    BookViewContent(modifier, uiState.value) {
        bookViewModel.loadBooksUsingRx()
    }
}

@Composable
fun BookViewContent(modifier: Modifier, value: BookUiState, retryApiRequest: () -> Unit) {

    var selectedBook by remember { mutableStateOf<BookSummary?>(null) }

    when (value) {
        BookUiState.Ideal -> { // Initial State. So do nothing
        }

        BookUiState.Loading -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(36.dp),
                )
            }

        }

        is BookUiState.FailureBookData -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                ErrorBookViewContent(value.error, retryApiRequest)
            }
        }

        is BookUiState.SuccessBookData -> {
            Box(modifier = modifier.fillMaxSize()) {
                BookViewData(value.bookSummary, onCardClicked = { bookKey ->
                    val bookData = value.bookSummary.find { it.key == bookKey }
                    selectedBook = bookData
                })
            }
        }
    }

    selectedBook?.let {

        val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
        val deviceConfiguration = DeviceConfiguration.fromWindowSizeClass(windowSizeClass)

        when (deviceConfiguration) {
            DeviceConfiguration.MOBILE_PORTRAIT, DeviceConfiguration.TABLET_PORTRAIT -> {
                BottomSheetPortraitContent(selectedBook) {
                    selectedBook = null
                }
            }

            DeviceConfiguration.MOBILE_LANDSCAPE, DeviceConfiguration.TABLET_LANDSCAPE, DeviceConfiguration.DESKTOP -> {
                BottomSheetLandScapeContent(selectedBook) {
                    selectedBook = null
                }
            }
        }
    }
}

@Composable
fun AuthorContent(bookData: BookSummary) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(R.string.authors),
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = bookData.authors.joinToString(", "),
            style = MaterialTheme.typography.bodySmall,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun BookImageContent(bookData: BookSummary, cornerRadius: Int) {
    CustomImageView(
        imageUrl = BookAppConst.COVER_IMAGE_API.replace(
            "{cover_id}",
            bookData.coverImageId.toString()
        ),
        contentDescription = bookData.title,
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(16f / 9f),
        contentScale = ContentScale.Crop,
        cornerRadius = cornerRadius.dp
    )
}

@Composable
fun BookViewData(
    bookSummary: List<BookSummary>,
    modifier: Modifier = Modifier,
    onCardClicked: (String) -> Unit
) {
    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    val deviceConfiguration = DeviceConfiguration.fromWindowSizeClass(windowSizeClass)

    when (deviceConfiguration) {
        DeviceConfiguration.MOBILE_PORTRAIT, DeviceConfiguration.TABLET_PORTRAIT -> {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(bookSummary) { book ->
                    BookPortraitCardView(book, onCardClicked)
                }
            }
        }

        DeviceConfiguration.MOBILE_LANDSCAPE, DeviceConfiguration.TABLET_LANDSCAPE, DeviceConfiguration.DESKTOP -> {
            LazyColumn(
                modifier = modifier
                    .fillMaxSize()
                    .windowInsetsPadding(WindowInsets.displayCutout)
            ) {
                items(bookSummary) { book ->
                    BookLandScapeCardView(book, onCardClicked)
                }
            }
        }
    }

}

@Composable
fun BookPortraitCardView(book: BookSummary, onCardClicked: (String) -> Unit) {
    ElevatedCard(
        onClick = { onCardClicked(book.key) },
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 6.dp, bottom = 6.dp, start = 8.dp, end = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            BookImageContent(book, 16)

            Column(
                modifier = Modifier.padding(vertical = 12.dp, horizontal = 12.dp)
            ) {
                Text(
                    text = book.title,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(6.dp))

                AuthorContent(book)
            }
        }
    }
}

@Composable
fun BookLandScapeCardView(book: BookSummary, onCardClicked: (String) -> Unit) {
    ElevatedCard(
        onClick = { onCardClicked(book.key) },
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 6.dp, bottom = 6.dp, start = 8.dp, end = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {

            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                BookImageContent(book, 16)
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 14.dp, horizontal = 16.dp)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    text = book.title,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                AuthorContent(book)
            }
        }
    }
}

@Composable
fun ErrorBookViewContent(error: DataError, onRetry: () -> Unit) {
    var showError by rememberSaveable { mutableStateOf(true) }

    if (showError) {
        AlertDialog(
            onDismissRequest = {},
            confirmButton = {
                TextButton(onClick = onRetry) {
                    Text(stringResource(R.string.retry))
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showError = false
                }) {
                    Text(stringResource(R.string.cancel))
                }
            },
            title = { Text(stringResource(R.string.error)) },
            text = { Text(stringResource(error.toMessageRes())) },
            properties = DialogProperties(dismissOnClickOutside = false)
        )
    }
}

@Preview
@Composable
private fun PreviewBookLoadingContent() {
    BookViewContent(modifier = Modifier, BookUiState.Loading, {})
}

@Preview
@Composable
private fun PreviewBookErrorViewContent() {
    BookViewContent(
        modifier = Modifier,
        BookUiState.FailureBookData(DataError.Remote.SERVER_ERROR),
        {})
}

@Preview
@Composable
private fun PreviewBookSuccessViewContent() {
    BookViewContent(
        modifier = Modifier,
        BookUiState.SuccessBookData(
            listOf(
                BookSummary(
                    key = "OL2624319W",
                    coverImageId = 630082,
                    title = "Killing the black body",
                    authors = listOf("Paulo Freire", "Culadasa John Yates"),
                    firstPublishedYear = 1986,
                    loggedEdition = "OL21365864M",
                    loggedDate = "2025/10/20, 05:26:59"
                )
            )
        ),
        {}
    )
}