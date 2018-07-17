package com.xrojan.rxbusdemo.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.xrojan.rxbus.RxBus
import com.xrojan.rxbusdemo.R
import com.xrojan.rxbusdemo.events.UpdateFragmentBottomLabel
import com.xrojan.rxbusdemo.events.UpdateFragmentTopLabel
import com.xrojan.rxbusdemo.events.UpdateMainActivityLabel
import kotlinx.android.synthetic.main.top_fragment.*

/**
 * Created by Joshua de Guzman on 16/07/2018.
 */

class FragmentTop : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.top_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Init subscription
        RxBus.subscribe<UpdateFragmentTopLabel>(this) {
            tv_main.text = it.string
        }

        // Init listeners
        bt_post_main.setOnClickListener {
            RxBus.post(UpdateMainActivityLabel("From FragmentTop: Hello!"))
        }

        bt_post_bottom.setOnClickListener {
            RxBus.post(UpdateFragmentBottomLabel("From FragmentTop: Hello!"))
        }
    }

    /**
     * Dispose subscriptions here
     */
    override fun onDestroy() {
        super.onDestroy()
        RxBus.unsubscribe(this)
    }
}