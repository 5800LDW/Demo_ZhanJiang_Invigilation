package com.tecsun.jc.base.event


class TopTabEvent(topTabNames: Array<String>?, tabFragments: Array<String>?) {

    var topTabNames: Array<String>? = null

    var tabFragments: Array<String>? = null

    init {
        this.topTabNames = topTabNames
        this.tabFragments = tabFragments
    }

}

/*
data class TopTabEvent(var topTabName: Array<String>, var tabFragments: Array<String>?) {
    override fun equals(other: Any?): Boolean {
        return super.equals(other)
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }
}*/
