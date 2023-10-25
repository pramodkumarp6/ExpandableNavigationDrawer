package com.project.expandablenavigationdrawer

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView
import com.project.expandablenavigationdrawer.navigation.BaseItem
import com.project.expandablenavigationdrawer.navigation.CustomDataProvider
import pl.openrnd.multilevellistview.ItemInfo
import pl.openrnd.multilevellistview.MultiLevelListAdapter
import pl.openrnd.multilevellistview.MultiLevelListView
import pl.openrnd.multilevellistview.OnItemClickListener

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private var multiLevelListView: MultiLevelListView? = null
    private val mOnItemClickListener: OnItemClickListener = object : OnItemClickListener {
        private fun showItemDescription(`object`: Any, itemInfo: ItemInfo) {
            if ((`object` as BaseItem).name.contains("Home")) {
                displaySelectedScreen("HOME")
            }
            if (`object`.name.contains("Category1")) {
                displaySelectedScreen("CATEGORY1")
            }
            if (`object`.name.contains("Category2")) {
                displaySelectedScreen("CATEGORY2")
            }
            if (`object`.name.contains("Category3")) {
                displaySelectedScreen("CATEGORY3")
            }
            if (`object`.name.contains("Assignment1")) {
                displaySelectedScreen("ASSIGNMENT1")
            }
            if (`object`.name.contains("Assignment2")) {
                displaySelectedScreen("ASSIGNMENT2")
            }
            if (`object`.name.contains("Assignment3")) {
                displaySelectedScreen("ASSIGNMENT3")
            }
            if (`object`.name.contains("AboutUs")) {
                displaySelectedScreen("ABOUTUS")
            }
            if (`object`.name.contains("Logout")) {
                displaySelectedScreen("Logout")
            }
        }

        override fun onItemClicked(
            parent: MultiLevelListView,
            view: View,
            item: Any,
            itemInfo: ItemInfo
        ) {
            showItemDescription(item, itemInfo)
        }

        override fun onGroupItemClicked(
            parent: MultiLevelListView,
            view: View,
            item: Any,
            itemInfo: ItemInfo
        ) {
            showItemDescription(item, itemInfo)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        confMenu()
        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        val toggle = ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer.setDrawerListener(toggle)
        toggle.syncState()
        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)
        displaySelectedScreen("HOME")
    }

    private fun confMenu() {
        multiLevelListView = findViewById(R.id.multi_nav)
        // custom ListAdapter
        val listAdapter: ListAdapter = ListAdapter()
        multiLevelListView?.setAdapter(listAdapter)
        multiLevelListView?.setOnItemClickListener(mOnItemClickListener)
        listAdapter.setDataItems(CustomDataProvider.initialItems)
    }

    override fun onBackPressed() {
        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    private fun displaySelectedScreen(itemName: String) {
        //creating fragment object
        var fragment: Fragment? = null
        when (itemName) {
            "HOME" -> fragment = HomeFragment()
            "CATEGORY1" -> fragment = Category1Fragment()
            "CATEGORY2" -> fragment = Category2Fragment()
            "CATEGORY3" -> Toast.makeText(
                applicationContext,
                "Category3 Clicked",
                Toast.LENGTH_LONG
            ).show()

            "ASSIGNMENT1" -> fragment = Assignment1Fragment()
            "ASSIGNMENT2" -> fragment = Assignment2Fragment()
            "ASSIGNMENT3" -> Toast.makeText(
                applicationContext,
                "Assignment3 Clicked",
                Toast.LENGTH_LONG
            ).show()

            "ABOUTUS" -> startActivity(Intent(applicationContext, AboutUsActivity::class.java))
            "Logout" -> dialogExit()
        }

        //replacing the fragment
        if (fragment != null) {
            val ft = supportFragmentManager.beginTransaction()
            ft.replace(R.id.fragment_container, fragment)
            ft.commit()
            val drawer = findViewById<View>(R.id.drawer_layout) as DrawerLayout
            drawer.closeDrawer(GravityCompat.START)
        }
    }

    private fun dialogExit() {
        AlertDialog.Builder(this)
            .setMessage("Are you sure want to exit?")
            .setPositiveButton("Yes") { dialogInterface, i -> finishAffinity() }
            .setNegativeButton("No") { dialogInterface, i -> dialogInterface.dismiss() }
            .show()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        //calling the method displayselectedscreen and passing the id of selected menu
        displaySelectedScreen(item.itemId.toString())
        //make this method blank
        return true
    }

    private inner class ListAdapter : MultiLevelListAdapter() {
        public override fun getSubObjects(`object`: Any): List<BaseItem?>? {
            // DIEKSEKUSI SAAT KLIK PADA GROUP-ITEM
            return CustomDataProvider.getSubItems(`object` as BaseItem)
        }

        public override fun isExpandable(`object`: Any): Boolean {
            return CustomDataProvider.isExpandable(`object` as BaseItem)
        }

        public override fun getViewForObject(
            `object`: Any,
            convertView: View,
            itemInfo: ItemInfo
        ): View {
            var convertView = convertView
            val viewHolder: ViewHolder
            if (convertView == null) {
                viewHolder = ViewHolder()
                convertView =
                    LayoutInflater.from(this@MainActivity).inflate(R.layout.data_item, null)
                //viewHolder.infoView = (TextView) convertView.findViewById(R.id.dataItemInfo);
                viewHolder.nameView = convertView.findViewById(R.id.dataItemName)
                viewHolder.arrowView = convertView.findViewById(R.id.dataItemArrow)
                viewHolder.icon = convertView.findViewById(R.id.di_image)
                convertView.tag = viewHolder
            } else {
                viewHolder = convertView.tag as ViewHolder
            }
            viewHolder.nameView!!.text = (`object` as BaseItem).name
            //viewHolder.infoView.setText(getItemInfoDsc(itemInfo));
            if (itemInfo.isExpandable) {
                viewHolder.arrowView!!.visibility = View.VISIBLE
                viewHolder.arrowView!!.setImageResource(if (itemInfo.isExpanded) R.drawable.bottomarrow else R.drawable.rightarrow)
            } else {
                viewHolder.arrowView!!.visibility = View.GONE
            }
            viewHolder.icon!!.setImageResource(`object`.icon)
            return convertView
        }

        private inner class ViewHolder {
            var nameView: TextView? = null
            var arrowView: ImageView? = null
            var icon: ImageView? = null
        }
    }
}