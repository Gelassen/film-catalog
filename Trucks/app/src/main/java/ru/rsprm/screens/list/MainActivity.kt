package ru.rsprm.screens.list

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.view_item_truck.*
import ru.rsprm.AppApplication
import ru.rsprm.R
import ru.rsprm.model.Truck
import ru.rsprm.screens.BaseActivity
import ru.rsprm.screens.add.AddTruckActivity
import javax.inject.Inject

class MainActivity : BaseActivity(), IListContract.View, DataAdapter.Listener {

    private val START_MODIFY_OR_ADD_SCREEN: Int = 10

    @Inject
    lateinit var presenter : MainPresenter

    var adapter : DataAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        (application as AppApplication).getComponent().inject(this)

        adapter = DataAdapter(this)

        val view = findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.list)
        view.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        view.adapter = adapter

        fab.setOnClickListener { view ->
            AddTruckActivity.startForResult(this, null, START_MODIFY_OR_ADD_SCREEN)
        }

        presenter.onStart(this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == START_MODIFY_OR_ADD_SCREEN && resultCode == Activity.RESULT_OK) {
            presenter.onUpdate()
        }
    }

    override fun onTrucks(data: List<Truck>) {
        adapter?.updateModel(data!!)
    }

    override fun onError(e: Throwable) {
        Log.d("TAG", "Something went wrong", e)
    }

    override fun onError(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }

    override fun onRemove(id: Int) {
        presenter!!.onRemove(id)
    }

    override fun onRemoveTruck(id: Int) {
        adapter!!.onRemoveItem(id)

        if (adapter!!.itemCount == 0) {
            showEmptyList()
        }
    }

    override fun onItemClick(truck: Truck) {
        AddTruckActivity.startForResult(this, truck, START_MODIFY_OR_ADD_SCREEN)
    }

    override fun showEmptyList() {
        empty.visibility = View.VISIBLE
    }

    override fun hideEmptyList() {
        empty.visibility = View.GONE
    }

    override fun showProgressIndicator() {
        progress_indicator.visibility = View.VISIBLE
    }

    override fun hideProgressIndicator() {
        progress_indicator.visibility = View.GONE
    }
}
