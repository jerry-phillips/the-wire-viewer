package com.sample.wireviewer.characterdetail

import android.content.Context
import com.sample.wireviewer.poko.RelatedTopic

class CharacterDetailPresenter(val context: Context, private val characterDetailView: CharacterDetailContract.WireDetailView): CharacterDetailContract.WireDetailPresenter {
    override fun failedRespone() {
       characterDetailView.failedResponse()
    }

    private val wireDetailModel = CharacterDetailModel(this)

    override fun updateView(wireFrame: RelatedTopic) {
        characterDetailView.updateView(wireFrame)
    }

    override fun getCharacter(tagInfo: String) {
        wireDetailModel.getWireCharacter(tagInfo)
    }

    override fun closeRealm() {
        wireDetailModel.closeRealm()
    }
}