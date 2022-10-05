package nick.dev.gorillalang.remote

import com.google.firebase.firestore.FirebaseFirestore
import nick.dev.gorillalang.models.Module
import nick.dev.gorillalang.util.Constants.COURSES_COLLECTION
import nick.dev.gorillalang.util.Constants.LANG_DOCUMENT
import nick.dev.gorillalang.util.Constants.MODULE_COLLECTION

class RemoteLanguageDatabase {
    private val firestore = FirebaseFirestore.getInstance()
    private val moduleCollectionRef = firestore.collection(COURSES_COLLECTION).document(LANG_DOCUMENT).collection(MODULE_COLLECTION)
    fun getModules() = moduleCollectionRef.get()
    fun getWordsByModule(module: Module) = moduleCollectionRef.document(module.remoteId).collection("words").get()
}