package nick.dev.gorillalang.remote

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import nick.dev.gorillalang.models.Module
import nick.dev.gorillalang.util.Constants
import nick.dev.gorillalang.util.Constants.MODULE_COLLECTION

class RemoteLanguageDatabase {
    private val firestore = FirebaseFirestore.getInstance()
    private val moduleCollectionRef = firestore.collection(MODULE_COLLECTION)


    fun getModules() = moduleCollectionRef.get()

    fun getWordsByModule(module: Module) = moduleCollectionRef.document(module.moduleName).collection("words").get()
}