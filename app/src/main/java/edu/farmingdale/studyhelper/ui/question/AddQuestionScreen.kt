package edu.farmingdale.studyhelper.ui.question

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import edu.farmingdale.studyhelper.data.Question

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddQuestionScreen(
   modifier: Modifier = Modifier,
   viewModel: AddQuestionViewModel = viewModel(
      factory = AddQuestionViewModel.Factory
   ),
   onUpClick: () -> Unit = {},
   onSaveClick: () -> Unit = {}
) {
   Scaffold(
      topBar = {
         TopAppBar(
            title = { Text("Add Question") },
            modifier = modifier,
            navigationIcon = {
               IconButton(onClick = onUpClick) {
                  Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back")
               }
            }
         )
      },
      floatingActionButton = {
         FloatingActionButton(
            onClick = {
               viewModel.addQuestion()
               onSaveClick()
            }
         ) {
            Icon(Icons.Filled.Done, "Save")
         }
      }
   ) { innerPadding ->
      QuestionEntry(
         question = viewModel.question,
         onQuestionChange = { viewModel.changeQuestion(it) },
         modifier = modifier
            .padding(innerPadding)
            .fillMaxSize()
      )
   }
}

@Composable
fun QuestionEntry(
   question: Question,
   onQuestionChange: (Question) -> Unit,
   modifier: Modifier = Modifier
) {
   val focusManager = LocalFocusManager.current

   Column(
      modifier = modifier,
      verticalArrangement = Arrangement.SpaceBetween,
   ) {
      Row(
         modifier = Modifier
            .weight(1f)
            .fillMaxWidth()
      ) {
         Text(
            text = "Q",
            color = MaterialTheme.colorScheme.primary,
            fontSize = 80.sp,
            modifier = Modifier.padding(8.dp)
         )
         TextField(
            value = question.text,
            onValueChange = { onQuestionChange(question.copy(text = it)) },
            singleLine = false,
            textStyle = TextStyle.Default.copy(fontSize = 30.sp),
            keyboardOptions = KeyboardOptions(
               imeAction = ImeAction.Next
            ),
            modifier = Modifier.padding(8.dp)
         )
      }
      Row(
         modifier = Modifier
            .weight(1f)
            .fillMaxWidth()
      ) {
         Text(
            text = "A",
            color = MaterialTheme.colorScheme.primary,
            fontSize = 80.sp,
            modifier = Modifier.padding(8.dp)
         )
         TextField(
            value = question.answer,
            onValueChange = { onQuestionChange(question.copy(answer = it)) },
            singleLine = false,
            textStyle = TextStyle.Default.copy(fontSize = 30.sp),
            keyboardActions = KeyboardActions(
               onDone = { focusManager.clearFocus() }
            ),
            keyboardOptions = KeyboardOptions(
               imeAction = ImeAction.Done
            ),
            modifier = Modifier.padding(8.dp)
         )
      }
   }
}
