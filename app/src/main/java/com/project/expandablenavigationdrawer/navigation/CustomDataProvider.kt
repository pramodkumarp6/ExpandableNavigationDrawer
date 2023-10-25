package com.project.expandablenavigationdrawer.navigation

import android.content.Context
import com.project.expandablenavigationdrawer.R
import java.util.Locale

class CustomDataProvider {
    var context: Context? = null

    companion object {
        private const val MAX_LEVELS = 3
        private const val LEVEL_1 = 1
        private const val LEVEL_2 = 2
        private const val LEVEL_3 = 3
        private val mMenu: List<BaseItem> = ArrayList()
        val initialItems: List<BaseItem>
            get() {
                //return getSubItems(new GroupItem("root"));
                val rootMenu: MutableList<BaseItem> = ArrayList()
                /**
                 * ITEM = WITHOUT CHILD
                 * GROUP_ITEM = WITH CHILD
                 */
                rootMenu.add(Item("Home", R.drawable.ic_home))
                rootMenu.add(GroupItem("Category", R.drawable.ic_category))
                rootMenu.add(GroupItem("Assignments", R.drawable.ic_assignment))
                rootMenu.add(Item("AboutUs", R.drawable.ic_info))
                rootMenu.add(Item("Exit", R.drawable.ic_logout))
                return rootMenu
            }

        fun getSubItems(baseItem: BaseItem): List<BaseItem?>? {
            var result: List<BaseItem?> = ArrayList()
            val level = (baseItem as GroupItem).level + 1
            val menuItem = baseItem.name
            require(baseItem is GroupItem) { "GroupItem required" }
            if (baseItem.level >= MAX_LEVELS) {
                return null
            }
            when (level) {
                LEVEL_1 -> when (menuItem.uppercase(Locale.getDefault())) {
                    "CATEGORY" -> result = listCategory
                    "ASSIGNMENTS" -> result = listAssignments
                }
            }
            return result
        }

        fun isExpandable(baseItem: BaseItem?): Boolean {
            return baseItem is GroupItem
        }

        private val listCategory: List<BaseItem?>
            private get() {
                val list: MutableList<BaseItem?> = ArrayList()
                list.add(Item("Category1"))
                list.add(Item("Category2"))
                list.add(Item("Category3"))
                return list
            }
        private val listAssignments: List<BaseItem?>
            private get() {
                val list: MutableList<BaseItem?> = ArrayList()
                list.add(Item("Assignment1"))
                list.add(Item("Assignment2"))
                list.add(Item("Assignment3"))
                return list
            }
    }
}