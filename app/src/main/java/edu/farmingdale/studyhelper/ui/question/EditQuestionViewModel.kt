package edu.farmingdale.studyhelper.ui.question

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.toRoute
import edu.farmingdale.studyhelper.StudyHelperApplication
import edu.farmingdale.studyhelper.data.Question
import edu.farmingdale.studyhelper.data.StudyRepository
import edu.farmingdale.studyhelper.ui.Routes
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class EditQuestionViewModel(
   savedStateHandle: SavedStateHandle,
   private val studyRepo: StudyRepository
) : ViewModel() {

   companion object {
      val Factory: ViewModelProvider.Factory = viewModelFactory {
         initializer {
            val application = (this[APPLICATION_KEY] as StudyHelperApplication)
            EditQuestionViewModel(this.createSavedStateHandle(), application.studyRepository)
         }
      }
   }

   // Get from composable's route argument
   private val questionId: Long = savedStateHandle.toRoute<Routes.EditQuestion>().questionId

   var question by mutableStateOf(Question(0))
      private set

   fun changeQuestion(ques: Question) {
      question = ques
   }

   fun updateQuestion() {
      studyRepo.updateQuestion(question)
   }

   init {
      viewModelScope.launch {
         question = studyRepo.getQuestion(questionId).filterNotNull().first()
      }
   }
}
