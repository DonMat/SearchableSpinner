/*
 * Copyright 2018 Mateusz Utkala (DonMat)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package pl.utkala.searchablespinner

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.DialogInterface
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.SpinnerAdapter


class SearchableSpinner : Spinner, View.OnTouchListener, OnSearchableItemClick<Any?> {
    override fun onSearchableItemClicked(item: Any?, position: Int) {
        setSelection(mItems.indexOf(item))
        mIsSelectedItem = true
        adapter = currentAdapter
        setSelection(mItems.indexOf(item))
    }

    private lateinit var searchDialog: SearchableSpinnerDialog
    private val mContext: Context
    private var mDialogTitle: String? = null
    private var mItems: MutableList<Any?> = mutableListOf("")
    private var mIsSelectedItem: Boolean = false
    private var currentAdapter: ArrayAdapter<Any?>? = null

    var closeText: String? = null

    constructor(context: Context) : super(context) {
        this.mContext = context
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        this.mContext = context
        setAttributes(context, attrs)
        init()
    }

    constructor (context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        this.mContext = context
        setAttributes(context, attrs)
        init()
    }

    private fun init() {
        searchDialog = SearchableSpinnerDialog.getInstance(mItems)
        searchDialog.setTitle(mDialogTitle)
        searchDialog.setDismissText(closeText)
        searchDialog.onSearchableItemClick = this

        setOnTouchListener(this)
        currentAdapter = adapter as ArrayAdapter<Any?>?
    }

    override fun setAdapter(adap: SpinnerAdapter?) {
        currentAdapter = adap as ArrayAdapter<Any?>?

        super.setAdapter(adap)
    }
    override fun getSelectedItemPosition(): Int {
        return if (!mIsSelectedItem) {
            -1
        } else {
            super.getSelectedItemPosition()
        }
    }

    override fun getSelectedItem(): Any? {
        return if (!mIsSelectedItem) {
            null
        } else {
            super.getSelectedItem()
        }
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        if (searchDialog.isAdded) return true

        if(currentAdapter != null) {
            if (event?.action == MotionEvent.ACTION_UP) {
                mItems.clear()
                for (i in 0 until (adapter?.count ?: 0)) {
                    mItems.add(adapter?.getItem(i))
                }
                val fm = scanForActivity(mContext)?.fragmentManager
                if (!searchDialog.isAdded)
                    searchDialog.show(fm, "search")
            }
        }
        return true
    }

    fun setDialogTitle(title: String?) {
        mDialogTitle = title
        searchDialog.setTitle(title)
    }

    fun setDismissText(dismiss: String?) {
        closeText = dismiss
        searchDialog.setDismissText(dismiss)
    }

    fun setDismissText(dismiss: String?, onDismissListener: DialogInterface.OnClickListener) {
        closeText = dismiss
        searchDialog.setDismissText(dismiss, onDismissListener)
    }

    fun setDialogCancelable(cancelable: Boolean){
        searchDialog.isCancelable = cancelable
    }

    private fun setAttributes(context: Context, attrs: AttributeSet) {
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.SearchableSpinner)

        for (i in 0 until attributes.indexCount) {
            val attr = attributes.getIndex(i)
            when (attr) {
                R.styleable.SearchableSpinner_closeText -> closeText = attributes.getString(attr)
                R.styleable.SearchableSpinner_dialogTitle -> mDialogTitle = attributes.getString(attr)
            }
        }
        attributes.recycle()
    }

    private fun scanForActivity(context: Context?): Activity? {
        return when (context) {
            is Activity -> context
            is ContextWrapper -> scanForActivity(context.baseContext)
            else -> null
        }

    }
}
