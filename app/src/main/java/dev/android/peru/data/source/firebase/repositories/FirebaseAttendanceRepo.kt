package dev.android.peru.data.source.firebase.repositories

import dev.android.peru.data.repositories.AttendanceRepo
import dev.android.peru.data.source.firebase.FirebaseParser
import dev.android.peru.data.source.firebase.FirebaseRepo
import peru.android.dev.datamodel.Attendance
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class FirebaseAttendanceRepo: AttendanceRepo, FirebaseRepo {

    override suspend fun getAttendance(meetupId: String): List<Attendance> {
        return suspendCoroutine { cont ->
            readCollection( path = "meetups/$meetupId/attendance", onSuccess = { result ->
                val attendance = result?.documents
                        ?.mapNotNull { it?.data }
                        ?.let { FirebaseParser.toAttendance(it) }
                        ?: emptyList()
                cont.resume(attendance)
            })
        }
    }
}
