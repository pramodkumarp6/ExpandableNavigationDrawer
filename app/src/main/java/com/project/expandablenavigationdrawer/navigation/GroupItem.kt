package com.project.expandablenavigationdrawer.navigation

class GroupItem : BaseItem {
    var level: Int

    constructor(mName: String?, icon: Int) : super(mName!!, icon) {
        level = 0
    }

    constructor(name: String?) : super(name!!) {
        level = 0
    }
}