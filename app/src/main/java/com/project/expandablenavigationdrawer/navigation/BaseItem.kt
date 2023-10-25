package com.project.expandablenavigationdrawer.navigation

open class BaseItem {
    var name: String
        private set
    var icon = 0
        private set

    constructor(mName: String, icon: Int) {
        name = mName
        this.icon = icon
    }

    constructor(name: String) {
        this.name = name
    }
}