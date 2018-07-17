package com.xrojan.rxbusdemo

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.xrojan.rxbus.RxBus
import com.xrojan.rxbusdemo.events.UpdateFragmentBottomLabel
import com.xrojan.rxbusdemo.events.UpdateFragmentTopLabel
import com.xrojan.rxbusdemo.events.UpdateMainActivityLabel
import com.xrojan.rxbusdemo.fragments.FragmentBottom
import com.xrojan.rxbusdemo.fragments.FragmentTop
import kotlinx.android.synthetic.main.main_activity.*

/**
 * Created by Joshua de Guzman on 16/07/2018.
 */

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        // Initialize subscriptions
        RxBus.subscribe<UpdateMainActivityLabel>(this) {
            tv_main.text = it.string
        }

        // Load fragments
        val fragmentTop = FragmentTop()
        val fragmentBottom = FragmentBottom()
        loadFragment(R.id.fl_fragment_top, fragmentTop, fragmentTop::class.java.simpleName)
        loadFragment(R.id.fl_fragment_bottom, fragmentBottom, fragmentBottom::class.java.simpleName)

        // Init button listeners
        bt_post_top.setOnClickListener {
            RxBus.post(UpdateFragmentTopLabel("From MainActivity: Hello!"))
        }

        bt_post_bottom.setOnClickListener {
            RxBus.post(UpdateFragmentBottomLabel("From MainActivity: Hello!"))
        }
    }

    /**
     * Dispose subscriptions here
     */
    override fun onDestroy() {
        super.onDestroy()
        RxBus.unsubscribe(this)
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