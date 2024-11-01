package edu.farmingdale.studyhelper.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import edu.farmingdale.studyhelper.ui.question.AddQuestionScreen
import edu.farmingdale.studyhelper.ui.question.EditQuestionScreen
import edu.farmingdale.studyhelper.ui.question.QuestionScreen
import edu.farmingdale.studyhelper.ui.subject.SubjectScreen
import edu.farmingdale.studyhelper.ui.theme.StudyHelperTheme
import kotlinx.serialization.Serializable

sealed class Routes {
   @Serializable
   data object Subject

   @Serializable
   data class Question(
      val subjectId: Long,
      val showLastQuestion: Boolean = false
   )

   @Serializable
   data class AddQuestion(
      val subjectId: Long
   )

   @Serializable
   data class EditQuestion(
      val questionId: Long
   )
}

@Composable
fun StudyHelperApp() {
   val navController = rememberNavController()

   NavHost(
      navController = navController,
      startDestination = Routes.Subject
   ) {
      composable<Routes.Subject> {
         SubjectScreen(
            onSubjectClick = { subject ->
               navController.navigate(
                  Routes.Question(subjectId = subject.id)
               )
            }
         )
      }
      composable<Routes.Question> { backStackEntry ->
         val routeArgs = backStackEntry.toRoute<Routes.Question>()

         QuestionScreen(
            onUpClick = { navController.navigateUp() },
            onAddClick = {
               navController.navigate(
                  Routes.AddQuestion(subjectId = routeArgs.subjectId)
               )
            },
            onEditClick = { questionId ->
               navController.navigate(
                  Routes.EditQuestion(questionId = questionId)
               )
            }
         )
      }
      composable<Routes.AddQuestion> { backStackEntry ->
         val routeArgs = backStackEntry.toRoute<Routes.AddQuestion>()

         AddQuestionScreen(
            onUpClick = { navController.navigateUp() },
            onSaveClick = {
               // Pop the Add Question screen before going up
               navController.popBackStack()
               navController.navigateUp()

               // Navigate to new question
               navController.navigate(
                  Routes.Question(
                     subjectId = routeArgs.subjectId,
                     showLastQuestion = true
                  )
               )
            }
         )
      }
      composable<Routes.EditQuestion> {
         EditQuestionScreen(
            onUpClick = { navController.navigateUp() },
            onSaveClick = { navController.navigateUp() }
         )
      }
   }
}

@Preview(showBackground = true)
@Composable
fun StudyHelperAppPreview() {
   StudyHelperTheme {
      StudyHelperApp()
   }
}

