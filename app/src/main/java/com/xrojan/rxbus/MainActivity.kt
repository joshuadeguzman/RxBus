package com.xrojan.rxbus

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.xrojan.library.rxbus
import com.xrojan.rxbus.events.UpdateFragmentBottomLabel
import com.xrojan.rxbus.events.UpdateFragmentTopLabel
import com.xrojan.rxbus.events.UpdateMainActivityLabel
import com.xrojan.rxbus.fragments.FragmentBottom
import com.xrojan.rxbus.fragments.FragmentTop
import kotlinx.android.synthetic.main.main_activity.*

/**
 * Created by Joshua de Guzman on 16/07/2018.
 */

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        // Initialize subscriptions
        rxbus.subscribe<UpdateMainActivityLabel>(this) {
            tv_main.text = it.string
        }

        // Load fragments
        val fragmentTop = FragmentTop()
        val fragmentBottom = FragmentBottom()
        loadFragment(R.id.fl_fragment_top, fragmentTop, fragmentTop::class.java.simpleName)
        loadFragment(R.id.fl_fragment_bottom, fragmentBottom, fragmentBottom::class.java.simpleName)

        // Init button listeners
        bt_post_top.setOnClickListener {
            rxbus.post(UpdateFragmentTopLabel("From MainActivity: Hello!"))
        }

        bt_post_bottom.setOnClickListener {
            rxbus.post(UpdateFragmentBottomLabel("From MainActivity: Hello!"))
        }
    }

    /**
     * Dispose subscriptions here
     */
    override fun onDestroy() {
        super.onDestroy()
        rxbus.unsubscribe(this)
    }

    /**
     * @param layoutId - Int / resource view for the demo fragment
     * @param fragment - v4 Fragment
     * @param tag - String
     */
    private fun loadFragment(layoutId: Int, fragment: Fragment, tag: String) {
        supportFragmentManager.beginTransaction()
                .replace(layoutId, fragment, tag)
                .commitNow()
    }
}