package com.sample.wireviewer.characterdetail

import android.content.Context
import com.sample.wireviewer.poko.RelatedTopic

interface CharacterDetailContract {

    interface WireDetailView {
        fun updateView(wireFrame: RelatedTopic)
        fun failedResponse()
        fun getViewContext():Context
    }

    interface WireDetailPresenter {
        fun updateView(wireFrame: RelatedTopic)
        fun getCharacter(tagInfo: String)
        fun getContext():Context
        fun failedRespone()
        fun closeRealm()
    }

    interface WireDetailModel {
        fun getWireCharacter(tagInfo: String)
        fun closeRealm()
    }

}