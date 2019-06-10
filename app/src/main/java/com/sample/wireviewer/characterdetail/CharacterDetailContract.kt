package com.sample.wireviewer.characterdetail

import com.sample.wireviewer.poko.RelatedTopic

interface CharacterDetailContract {

    interface WireDetailView {
        fun updateView(wireFrame: RelatedTopic)
        fun failedResponse()
    }

    interface WireDetailPresenter {
        fun updateView(wireFrame: RelatedTopic)
        fun getCharacter(tagInfo: String)
        fun failedRespone()
        fun closeRealm()
    }

    interface WireDetailModel {
        fun getWireCharacter(tagInfo: String)
        fun closeRealm()
    }

}