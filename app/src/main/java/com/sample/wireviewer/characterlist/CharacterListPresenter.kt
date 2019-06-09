package com.sample.wireviewer.characterlist

import android.content.Context
import com.sample.wireviewer.poko.RelatedTopic
import io.realm.RealmList

class CharacterListPresenter(val context: Context, val characterListView: CharacterListContract.WireListView):CharacterListContract.WireListPresenter {

    val characterListModel = CharacterListModel(this)
    override fun getData() {
        characterListView.showProgress(true)
        characterListModel.getData()
    }

    override fun updateDataSource(wireFrames: RealmList<RelatedTopic>) {
        characterListView.showProgress(false)
        characterListView.updateDataSource(wireFrames)
    }

    override fun failedRespone() {
        characterListView.failedResponse()
    }

    override fun queryCharacters(query: String) { characterListModel.queryWireFrame(query)
    }

    override fun closeRealm() {
        characterListModel.closeRealm()
    }

    override fun noResults() {
        characterListView.noResults()
    }
}