package ru.rsprm.screens.add

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import ru.rsprm.AppApplication
import ru.rsprm.R
import ru.rsprm.model.Truck
import javax.inject.Inject

class AddTruckActivity : AppCompatActivity(), IAddTruckContract.View {

    @Inject
    lateinit var presenter : AddTruckPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_truck)

        (application as AppApplication).getComponent().inject(this)

        val truck = intent.getSerializableExtra(EXTRA_PAYLOAD) as Truck?
        presenter.onStart(this, truck)

        findViewById<View>(R.id.addTruck).setOnClickListener {
            if (truck == null) {
                presenter!!.onSubmit(
                    findViewById<EditText>(R.id.truckName).getText().toString(),
                    findViewById<EditText>(R.id.truckPrice).getText().toString(),
                    findViewById<EditText>(R.id.comment).getText().toString())
            } else {
                presenter!!.onSubmit(
                    truck!!.id,
                    findViewById<EditText>(R.id.truckName).getText().toString(),
                    findViewById<EditText>(R.id.truckPrice).getText().toString(),
                    findViewById<EditText>(R.id.comment).getText().toString())
            }
        }
    }

    override fun onError(error: String) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
    }

    override fun onCreate(data: Truck) {
        Toast.makeText(this, "Машина добавлена", Toast.LENGTH_SHORT).show()
        val returnIntent = intent
        setResult(Activity.RESULT_OK, returnIntent)
        finish()
    }

    override fun onError(e: Throwable) {
        Log.e("TAG", "Something went wrong", e)
    }

    override fun onNotValidName() {
        Toast.makeText(this, "Complete me", Toast.LENGTH_SHORT).show()
        findViewById<EditText>(R.id.truckName).setError("Имя не должно быть пустым и более 360 символов")
    }

    override fun onNotValidPrice() {
        Toast.makeText(this, "Complete me", Toast.LENGTH_SHORT).show()
        findViewById<EditText>(R.id.truckPrice).setError("Цена должна состоять только из цифр и не более 7 символов")
    }

    override fun onNotValidComment() {
        Toast.makeText(this, "Complete me", Toast.LENGTH_SHORT).show()
        findViewById<EditText>(R.id.comment).setError("Комментарий не должен быть более 360 символов")
    }

    override fun onAddTrackMode() {
        (findViewById<View>(R.id.addTruck) as Button).setText("Добавить машину")
    }

    override fun onModifyTrackMode(truck: Truck) {
        (findViewById<View>(R.id.addTruck) as Button).setText("Изменить машину")
        findViewById<EditText>(R.id.truckName).setText(truck.name)
        findViewById<EditText>(R.id.truckPrice).setText(truck.price)
        findViewById<EditText>(R.id.comment).setText(truck.comment)
    }

    companion object {

        private val EXTRA_PAYLOAD = "EXTRA_PAYLOAD"

        fun start(context: Context, payload: Truck?) {
            val intent = Intent(context, AddTruckActivity::class.java)
            intent.putExtra(EXTRA_PAYLOAD, payload)
            context.startActivity(intent)
        }

        fun startForResult(context: AppCompatActivity, payload: Truck?, requestCode: Int) {
            val intent = Intent(context, AddTruckActivity::class.java)
            intent.putExtra(EXTRA_PAYLOAD, payload)
            context.startActivityForResult(intent, requestCode)
        }
    }
}
