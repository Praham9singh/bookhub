package com.praham.bookhub.bookhub.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.FrameLayout
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.praham.bookhub.R
import com.praham.bookhub.bookhub.fragment.AboutFragment
import com.praham.bookhub.bookhub.fragment.DashboardFragment
import com.praham.bookhub.bookhub.fragment.FavouritesFragment
import com.praham.bookhub.bookhub.fragment.ProfileFragment

class MainActivity : AppCompatActivity() {
    lateinit var drawerLayout: DrawerLayout
    lateinit var coordinatorLayout: CoordinatorLayout
    lateinit var navigationView: NavigationView
    lateinit var frameLayout: FrameLayout
    lateinit var toolbar: androidx.appcompat.widget.Toolbar
    var previousmenuitem:MenuItem?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        drawerLayout=findViewById(R.id.drawerlayout_main)
        coordinatorLayout=findViewById(R.id.coordinator_main)
        toolbar=findViewById(R.id.toolbar_main)
        frameLayout=findViewById(R.id.framelayout_main)
        navigationView=findViewById(R.id.navigation_main)
        setuptoolbar()

        openDashboard()
        val actionBarDrawerToggle = ActionBarDrawerToggle(this@MainActivity,drawerLayout,
            R.string.open_drawer,
            R.string.close_drawer
        )
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()

        navigationView.setNavigationItemSelectedListener {
            if (previousmenuitem!=null)
            {
                previousmenuitem?.isChecked=false
            }
            it.isCheckable=true
            it.isChecked=true
            previousmenuitem=it

            when(it.itemId)
            {
                R.id.menu_dashboard ->{
                    openDashboard()
                    drawerLayout.closeDrawers()

                }
                R.id.menu_profile ->{
                    supportFragmentManager.beginTransaction()
                        .replace(
                            R.id.framelayout_main, ProfileFragment()
                        ).addToBackStack("Profile")
                        .commit()
                    supportActionBar?.title="Profile"
                    drawerLayout.closeDrawers()}
                R.id.menu_about ->{
                    supportFragmentManager.beginTransaction()
                        .replace(
                            R.id.framelayout_main, AboutFragment()
                        ).addToBackStack("About")
                        .commit()
                    supportActionBar?.title="About"
                    drawerLayout.closeDrawers()}
                R.id.menu_favourites ->{
                    supportFragmentManager.beginTransaction()
                        .replace(
                            R.id.framelayout_main, FavouritesFragment()
                        ).addToBackStack("Favourites")
                        .commit()
                    supportActionBar?.title="Favourites"
                    drawerLayout.closeDrawers()}
            }
            return@setNavigationItemSelectedListener true
        }
    }
    fun setuptoolbar()
    {
        setSupportActionBar(toolbar)
        supportActionBar?.title="Toolbar Title"
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id= item.itemId
        if (id==android.R.id.home)
        {
            drawerLayout.openDrawer(GravityCompat.START)
        }

        return super.onOptionsItemSelected(item)
    }
    fun openDashboard()
    {
        val fragment= DashboardFragment()
        val transaction=supportFragmentManager.beginTransaction()
        transaction.replace(R.id.framelayout_main,fragment)
        transaction.commit()
        supportActionBar?.title="Dashboard"
        navigationView.setCheckedItem(R.id.menu_dashboard)
    }

    override fun onBackPressed() {
        val frag=supportFragmentManager.findFragmentById(R.id.framelayout_main)
        when(frag)
        {
            !is DashboardFragment ->openDashboard()
            else ->super.onBackPressed()

        }

    }
}