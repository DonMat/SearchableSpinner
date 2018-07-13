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

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.Spinner


class SearchableSpinner : Spinner, View.OnTouchListener {
    private val mContext: Context
    private var dialogTitle: String? = null
    var hintText: String? = null
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
        setOnTouchListener(this)
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        if (event?.action == MotionEvent.ACTION_UP) {

        }

        return true
    }

    fun setDialogTitle(title: String?){
        dialogTitle = title
    }

    private fun setAttributes(context: Context, attrs: AttributeSet) {
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.SearchableSpinner)

        for (i in 0 until attributes.indexCount) {
            val attr = attributes.getIndex(i)
            when (attr) {
                R.styleable.SearchableSpinner_searchHint -> hintText = attributes.getString(attr)
                R.styleable.SearchableSpinner_closeText -> closeText = attributes.getString(attr)
                R.styleable.SearchableSpinner_dialogTitle -> dialogTitle = attributes.getString(attr)
            }
        }
        attributes.recycle()
    }
}
