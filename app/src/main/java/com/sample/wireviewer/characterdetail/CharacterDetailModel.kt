package com.sample.wireviewer.characterdetail

import com.sample.wireviewer.poko.RelatedTopic
import io.realm.Realm

class CharacterDetailModel(private val characterDetailPresenter: CharacterDetailPresenter):CharacterDetailContract.WireDetailModel {

    val context = characterDetailPresenter.getContext()
    var realm : Realm? = null

    private fun initRealm() {
        if (realm == null) {
            Realm.init(context)
            realm = Realm.getDefaultInstance()
        }
    }
    override fun getWireCharacter(tagInfo: String) {
        initRealm()
        try {
            val wireCharacters = realm?.where(RelatedTopic::class.java)?.equalTo(WIREFRAMETAG, tagInfo)?.findFirst()
            characterDetailPresenter.updateView(wireCharacters as RelatedTopic)
        } catch (e:Exception) {
            characterDetailPresenter.failedRespone()
        }
    }

    override fun closeRealm() {
        if (realm != null) {
            realm?.close()
        }
    }

    companion object{
        const val WIREFRAMETAG = "firstURL"
    }
}