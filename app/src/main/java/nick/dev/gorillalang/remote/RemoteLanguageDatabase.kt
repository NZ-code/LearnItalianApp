package nick.dev.gorillalang.remote

import com.google.firebase.firestore.FirebaseFirestore
import nick.dev.gorillalang.models.ModuleRemote
import nick.dev.gorillalang.util.Constants.MODULE_COLLECTION

class RemoteLanguageDatabase {
    private val firestore = FirebaseFirestore.getInstance()
    private val moduleCollectionRef = firestore.collection(MODULE_COLLECTION)


    fun getModules() = moduleCollectionRef.get()

    fun getWordsByModule(moduleRemote: ModuleRemote) = moduleCollectionRef.document(moduleRemote.remoteId).collection("words").get()
}