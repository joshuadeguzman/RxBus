package com.xrojan.rxbus.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.xrojan.library.RxBus
import com.xrojan.rxbus.R
import com.xrojan.rxbus.events.UpdateFragmentBottomLabel
import com.xrojan.rxbus.events.UpdateFragmentTopLabel
import com.xrojan.rxbus.events.UpdateMainActivityLabel
import kotlinx.android.synthetic.main.bottom_fragment.*

/**
 * Created by Joshua de Guzman on 16/07/2018.
 */

class FragmentBottom : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.bottom_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Init subscription
        RxBus.subscribe<UpdateFragmentBottomLabel>(this) {
            tv_main.text = it.string
        }

        // Init listeners
        bt_post_main.setOnClickListener {
            RxBus.post(UpdateMainActivityLabel("From FragmentBottom: Hello!"))
        }

        bt_post_top.setOnClickListener {
            RxBus.post(UpdateFragmentTopLabel("From FragmentBottom: Hello!"))
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