package com.likith.presentation.books

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import androidx.activity.ComponentActivity
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.ProgressBarRangeInfo
import androidx.compose.ui.test.*
import com.likith.domain.model.BookSummary
import com.likith.domain.util.DataError
import com.likith.presentation.R

@RunWith(AndroidJUnit4::class)
class BookViewContentTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<ComponentActivity>()

    private fun setContentWithTheme(content: @Composable () -> Unit) {
        composeRule.setContent {
            MaterialTheme {
                content()
            }
        }
    }

    private fun sampleBook(
        key: String = "OL2624319W",
        title: String = "Killing the black body",
        authors: List<String> = listOf("Paulo Freire", "Culadasa John Yates"),
        coverId: Int = 630082,
        firstPublishedYear: Int = 1987,
        loggedEdition: String = "OL21365864M",
        loggedDate: String = "2025/10/20, 05:26:59"
    ) = BookSummary(
        key = key,
        title = title,
        authors = authors,
        coverImageId = coverId,
        firstPublishedYear = firstPublishedYear,
        loggedEdition = loggedEdition,
        loggedDate = loggedDate
    )

    @Test
    fun loading_showsCircularProgressIndicator() {
        setContentWithTheme {
            BookViewContent(
                modifier = Modifier,
                value = BookUiState.Loading,
                retryApiRequest = {}
            )
        }

        composeRule
            .onNode(hasProgressBarRangeInfo(ProgressBarRangeInfo.Indeterminate))
            .assertIsDisplayed()
    }

    @Test
    fun failure_showsDialog_and_retry_invokesCallback() {
        var retried = false
        val retryText = composeRule.activity.getString(R.string.retry)
        val errorTitle = composeRule.activity.getString(R.string.error)

        setContentWithTheme {
            BookViewContent(
                modifier = Modifier,
                value = BookUiState.FailureBookData(DataError.Remote.NOT_FOUND),
                retryApiRequest = { retried = true }
            )
        }

        composeRule.onNodeWithText(errorTitle).assertIsDisplayed()

        composeRule.onNodeWithText(retryText).performClick()

        assert(retried)
    }

    @Test
    fun success_rendersItems_and_clickingItem_isHandled() {
        val books = listOf(
            sampleBook(key = "OL2624319W", title = "Killing the black body"),
            sampleBook(key = "OL2624312W", title = "Advanced Quilter")
        )

        setContentWithTheme {
            BookViewContent(
                modifier = Modifier,
                value = BookUiState.SuccessBookData(books),
                retryApiRequest = {}
            )
        }

        composeRule.onNodeWithText("Killing the black body").assertIsDisplayed()
        composeRule.onNodeWithText("Advanced Quilter").assertIsDisplayed()

        composeRule.onNodeWithText("Killing the black body").performClick()
    }
}