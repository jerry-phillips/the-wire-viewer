package com.sample.wireviewer.characterlist

import android.content.Context
import com.sample.wireviewer.poko.RelatedTopic
import com.sample.wireviewer.poko.RequestData
import io.realm.RealmList

class CharacterListContract {
    interface WireListView {
        fun showProgress(isShowingProgress: Boolean)
        fun updateDataSource(wireFrames: RealmList<RelatedTopic>)
        fun failedResponse()
        fun noResults()
        fun getViewContext():Context
    }

    interface WireListPresenter {
        fun getData()
        fun updateDataSource(wireFrames: RealmList<RelatedTopic>)
        fun failedRespone()
        fun getContext():Context
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