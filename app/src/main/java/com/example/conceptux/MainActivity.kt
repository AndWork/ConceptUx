package com.example.conceptux

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.provider.Settings.Global.putString
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.content.edit
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.conceptux.ContactDetail.Companion.EXTRA_CONTACT
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlin.reflect.typeOf

class MainActivity : AppCompatActivity(), ClickItemContactListener {
    private val rvList: RecyclerView by lazy {
        findViewById<RecyclerView>(R.id.rv_list)
    }
    private val adapter = ContactAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.drawer_menu)

        initDrawer()
        fetchListContact()
        bindViews()
    }

    private fun fetchListContact(){
        val list = arrayListOf(
            Contact(
                "Teste",
                "(00) 0000-0000",
                "img.png"
            ),
            Contact(
                "Teste 2",
                "(00) 0000-0000",
                "img.png"
            )
        )
        getInstanceSharedPreferences().edit {
            val json = Gson().toJson(list)
            putString("contacts", json)
            commit()
        }
    }

    private fun getInstanceSharedPreferences(): SharedPreferences{
        return getSharedPreferences("com.exmaple.conceptux.PREFERENCES", Context.MODE_PRIVATE)
    }

    private fun initDrawer(){
        val drawerlayout = findViewById<View>(R.id.drawer_layout) as DrawerLayout

        val toolbar = findViewById<Toolbar>(R.id.toolBar)
        setSupportActionBar(toolbar)

        val toogle = ActionBarDrawerToggle(this, drawerlayout, toolbar, R.string.open_drawer , R.string.close_drawer)
        drawerlayout.addDrawerListener(toogle)
        toogle.syncState()
    }

    private fun bindViews() {
        rvList.adapter = adapter
        rvList.layoutManager = LinearLayoutManager(this)
        updateList()
    }

    private fun getListContacts(): List<Contact>{
        /*converter uma string para um objeto de classe*/
        val list = getInstanceSharedPreferences().getString("contacts", "[]")
        val turnsType = object : TypeToken<List<Contact>>() {}.type
        return Gson().fromJson(list, turnsType)
    }

    private fun updateList(){
        adapter.updatelist(getListContacts())
        /*adapter.updatelist(
            arrayListOf(
                Contact(
                    "Teste",
                    "(00) 0000-0000",
                    "img.png"
                ),
                Contact(
                    "Teste 2",
                    "(00) 0000-0000",
                    "img.png"
                )
            )
        )*/
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    private fun showToast(message: String){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.item_menu_1 -> {
                showToast("Exibindo item 1")
                true
            }
            R.id.item_menu_2 -> {
                showToast("Exibindo item 2")
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun clickItemContact(contact: Contact){
        val intent = Intent(this, ContactDetail::class.java)
        intent.putExtra(EXTRA_CONTACT, contact)
        startActivity(intent)
    }
}