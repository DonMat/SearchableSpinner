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
import android.widget.Spinner


class SearchableSpinner : Spinner, View.OnTouchListener, OnSearchableItemClick<Any?> {
    override fun onSearchableItemClicked(item: Any?, position: Int) {
        setSelection(mItems.indexOf(item))
    }

    private lateinit var searchDialog: SearchableSpinnerDialog
    private val mContext: Context
    private var mDialogTitle: String? = null
    private var mCloseText: String? = null
    private var mItems: MutableList<Any?> = mutableListOf(null)

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
        searchDialog.setDismissText(mCloseText)
        searchDialog.onSearchableItemClick = this

        setOnTouchListener(this)
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        if (searchDialog.isAdded) return true

        if (event?.action == MotionEvent.ACTION_UP) {
            if (adapter != null) {
                mItems.clear()
                for (i in 0 until (adapter.count )) {
                    mItems.add(adapter.getItem(i))
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
        mCloseText = dismiss
        searchDialog.setDismissText(dismiss)
    }

    fun setDismissText(dismiss: String?, onDismissListener: DialogInterface.OnClickListener) {
        mCloseText = dismiss
        searchDialog.setDismissText(dismiss, onDismissListener)
    }

    private fun setAttributes(context: Context, attrs: AttributeSet) {
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.SearchableSpinner)

        for (i in 0 until attributes.indexCount) {
            val attr = attributes.getIndex(i)
            when (attr) {
                R.styleable.SearchableSpinner_closeText -> mCloseText = attributes.getString(attr)
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
