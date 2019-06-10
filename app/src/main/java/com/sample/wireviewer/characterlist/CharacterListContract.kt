package com.sample.wireviewer.characterlist

import com.sample.wireviewer.poko.RelatedTopic
import com.sample.wireviewer.poko.RequestData
import io.realm.RealmList

class CharacterListContract {
    interface WireListView {
        fun showProgress(isShowingProgress: Boolean)
        fun updateDataSource(wireFrames: RealmList<RelatedTopic>)
        fun failedResponse()
        fun noResults()
    }

    interface WireListPresenter {
        fun getData()
        fun updateDataSource(wireFrames: RealmList<RelatedTopic>)
        fun failedRespone()
        fun processData(data: RequestData)
        fun queryCharacters(query: String)
        fun closeRealm()
        fun noResults()
    }

    interface WireListModel {
        fun getData()
        fun queryWireFrame(query: String)
        fun closeRealm()
        fun saveData(results:RealmList<RelatedTopic>)
    }
}