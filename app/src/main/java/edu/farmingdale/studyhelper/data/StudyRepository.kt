package edu.farmingdale.studyhelper.data

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StudyRepository(context: Context) {

   private val databaseCallback = object : RoomDatabase.Callback() {
      override fun onCreate(db: SupportSQLiteDatabase) {
         super.onCreate(db)

         CoroutineScope(Dispatchers.IO).launch {
            addStarterData()
         }
      }
   }

   private val database: StudyDatabase = Room.databaseBuilder(
      context,
      StudyDatabase::class.java,
      "study.db"
   )
      .addCallback(databaseCallback)
      .build()

   private val subjectDao = database.subjectDao()
   private val questionDao = database.questionDao()

   fun getSubject(subjectId: Long) = subjectDao.getSubject(subjectId)

   fun getSubjects() = subjectDao.getSubjects()

   fun addSubject(subject: Subject) {
      if (subject.title.trim() != "") {
         CoroutineScope(Dispatchers.IO).launch {
            subject.id = subjectDao.addSubject(subject)
         }
      }
   }

   fun deleteSubject(subject: Subject) {
      CoroutineScope(Dispatchers.IO).launch {
         subjectDao.deleteSubject(subject)
      }
   }

   fun getQuestion(questionId: Long) = questionDao.getQuestion(questionId)

   fun getQuestions(subjectId: Long) = questionDao.getQuestions(subjectId)

   fun addQuestion(question: Question) {
      CoroutineScope(Dispatchers.IO).launch {
         question.id = questionDao.addQuestion(question)
      }
   }

   fun updateQuestion(question: Question) {
      CoroutineScope(Dispatchers.IO).launch {
         questionDao.updateQuestion(question)
      }
   }

   fun deleteQuestion(question: Question) {
      CoroutineScope(Dispatchers.IO).launch {
         questionDao.deleteQuestion(question)
      }
   }

   private fun addStarterData() {
      var subjectId = subjectDao.addSubject(Subject(title = "Math"))
      questionDao.addQuestion(
         Question(
            text = "What is the y-intercept of y = 4x + 1?",
            answer = "y-intercept is 1",
            subjectId = subjectId
         )
      )
      questionDao.addQuestion(
         Question(
            text = "What is pi?",
            answer = "The ratio of a circle's circumference to its diameter.",
            subjectId = subjectId
         )
      )
      questionDao.addQuestion(
         Question(
            text = "What formula calculates the area of a triangle?",
            answer = "area = base * height / 2",
            subjectId = subjectId
         )
      )

      subjectId = subjectDao.addSubject(Subject(title = "US History"))
      questionDao.addQuestion(
         Question(
            text = "On what date was the U.S. Declaration of Independence adopted?",
            answer = "July 4, 1776",
            subjectId = subjectId
         )
      )

      subjectDao.addSubject(Subject(title = "Computing"))
      subjectDao.addSubject(Subject(title = "Biology"))
      subjectDao.addSubject(Subject(title = "Greek"))
   }
}

