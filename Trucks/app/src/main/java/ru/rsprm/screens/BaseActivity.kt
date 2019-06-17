package ru.rsprm.screens

import androidx.appcompat.app.AppCompatActivity
import leakcanary.LeakSentry

abstract
class BaseActivity : AppCompatActivity() {

    override fun onDestroy() {
        super.onDestroy()
        LeakSentry.refWatcher.watch(this)
    }
}
