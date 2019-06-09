package com.sample.wireviewer.characterdetail

import com.sample.wireviewer.poko.RelatedTopic

interface CharacterDetailContract {

    interface WireDetailView {
        fun updateView(wireFrame: RelatedTopic)
    }

    interface WireDetailPresenter {
        fun updateView(wireFrame: RelatedTopic)
        fun getCharacter(tagInfo: String)
        fun closeRealm()
    }

    interface WireDetailModel {
        fun getWireCharacter(tagInfo: String)
        fun closeRealm()
    }

}