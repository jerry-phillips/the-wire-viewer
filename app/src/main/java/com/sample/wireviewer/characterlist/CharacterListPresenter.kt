package com.sample.wireviewer.characterlist

import android.content.Context
import com.sample.wireviewer.poko.RelatedTopic
import com.sample.wireviewer.poko.RequestData
import io.realm.RealmList

class CharacterListPresenter(val context: Context, private val characterListView: CharacterListContract.WireListView):CharacterListContract.WireListPresenter {


    private val characterListModel = CharacterListModel(this)
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

    override fun processData(data: RequestData) {
        val results : RealmList<RelatedTopic> = RealmList()
        for (character in data.getRelatedTopics()!!) {
            character.setName(character.getText() as String)
            character.setDescription(character.getText() as String)
            results.add(character)
        }
        characterListModel.saveData(results)
    }
}