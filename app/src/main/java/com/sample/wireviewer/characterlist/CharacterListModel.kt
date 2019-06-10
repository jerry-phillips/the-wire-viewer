package com.sample.wireviewer.characterlist

import com.sample.wireviewer.interfaces.DuckDuckGoService
import com.sample.wireviewer.poko.RelatedTopic
import com.sample.wireviewer.poko.RequestData
import io.realm.Case
import io.realm.Realm
import io.realm.RealmList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CharacterListModel(private val presenter: CharacterListPresenter):CharacterListContract.WireListModel {

    private val context = presenter.context

    private var realm: Realm? = null

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.duckduckgo.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val service = retrofit.create<DuckDuckGoService>(DuckDuckGoService::class.java)

    private fun initRealm() {
        if(realm == null) {
            Realm.init(context)
            realm = Realm.getDefaultInstance()
        }
    }

    override fun getData() {
        initRealm()
        val searchResults = realm?.where(RelatedTopic::class.java)?.findAll()
        val results = RealmList<RelatedTopic>()
        if (searchResults?.size == 0) {
            service.getWireCharacters(WIREFRAMEQUERY, DATAFORMAT).enqueue(object : Callback<RequestData> {
                override fun onResponse(call: Call<RequestData>, response: Response<RequestData>) {
                    val requestData = response.body()
                    presenter.processData(requestData as RequestData)
                }

                override fun onFailure(call: Call<RequestData>, t: Throwable) {
                    presenter.failedRespone()
                }
            })
        } else {
            searchResults?.let { results.addAll(it) }
            presenter.updateDataSource(results)
        }


    }

    override fun queryWireFrame(query: String) {
        initRealm()
        try {
            realm?.executeTransaction(Realm.Transaction { realm ->
                val searchResults = realm.where(RelatedTopic::class.java)
                    .contains(TEXT, query, Case.INSENSITIVE)
                    .findAll()
                if (searchResults.size > 0) {
                    val characters = RealmList<RelatedTopic>()
                    characters.addAll(searchResults)
                    presenter.updateDataSource(characters)
                } else {
                    presenter.noResults()
                }
            })
        } catch (e:Exception) {
            presenter.failedRespone()
        }

    }

    override fun closeRealm() {
        realm?.close()
    }

    companion object {
        //This demo's requirements can allow for these parameters to be static
        const val WIREFRAMEQUERY = "the wire characters"
        const val DATAFORMAT = "json"
        const val TEXT = "text"
    }

    override fun saveData(results:RealmList<RelatedTopic>) {
        try {
            realm?.executeTransaction{ realm -> realm.insertOrUpdate(results) }
        } catch (e: java.lang.Exception) {
            presenter.failedRespone()
        }
        presenter.updateDataSource(results)

    }
}