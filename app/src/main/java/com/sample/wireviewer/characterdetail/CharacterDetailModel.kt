package com.sample.wireviewer.characterdetail

import com.sample.wireviewer.poko.RelatedTopic
import io.realm.Realm

class CharacterDetailModel(private val characterDetailPresenter: CharacterDetailPresenter):CharacterDetailContract.WireDetailModel {

    val context = characterDetailPresenter.context
    var realm : Realm? = null

    private fun initRealm() {
        Realm.init(context)
        realm = Realm.getDefaultInstance()
    }
    override fun getWireCharacter(tagInfo: String) {
        initRealm()
        try {
            val wireCharacters = realm?.where(RelatedTopic::class.java)?.equalTo(WIREFRAMETAG, tagInfo)?.findFirst()
            characterDetailPresenter.updateView(wireCharacters as RelatedTopic)
        } finally {
            closeRealm()
        }
    }

    override fun closeRealm() {
        realm?.close()
    }

    companion object{
        const val WIREFRAMETAG = "firstURL"
    }
}