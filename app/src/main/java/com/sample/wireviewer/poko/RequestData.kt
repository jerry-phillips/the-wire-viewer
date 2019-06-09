package com.sample.wireviewer.poko

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class RequestData {

    @SerializedName("RelatedTopics")
    @Expose
    private var relatedTopics: List<RelatedTopic>? = null


    fun getRelatedTopics(): List<RelatedTopic>? {
        return relatedTopics
    }

    fun setRelatedTopics(relatedTopics: List<RelatedTopic>) {
        this.relatedTopics = relatedTopics
    }
}