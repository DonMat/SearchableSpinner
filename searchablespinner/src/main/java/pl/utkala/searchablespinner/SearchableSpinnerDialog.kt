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

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.dialog_layout.view.*

class SearchableSpinnerDialog : DialogFragment(), SearchView.OnQueryTextListener, SearchView.OnCloseListener {
    private var items: MutableList<Any?> = arrayListOf("")
    private var mListView: ListView? = null
    private var mSearchView: SearchView? = null
    private var mDismissText: String? = null
    private var mDialogTitle: String? = null
    private var mDismissListener: DialogInterface.OnClickListener? = null
    lateinit var onSearchableItemClick: OnSearchableItemClick<Any?>

    companion object {
        @JvmStatic
        val CLICK_LISTENER = "click_listener"

        fun getInstance(items: MutableList<Any?>): SearchableSpinnerDialog {
            val dialog = SearchableSpinnerDialog()
            dialog.items = items
            return dialog
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        if (savedInstanceState != null) {
            onSearchableItemClick = savedInstanceState.getSerializable(CLICK_LISTENER) as OnSearchableItemClick<Any?>
        }

        val layoutInflater = LayoutInflater.from(activity)
        val rootView = layoutInflater.inflate(R.layout.dialog_layout, null)

        setView(rootView)

        val alertBuilder = AlertDialog.Builder(activity)
        alertBuilder.setView(rootView)
        val title = if (mDialogTitle.isNullOrBlank()) getString(R.string.search_dialog_title) else mDialogTitle
        alertBuilder.setTitle(title)

        val dismiss = if(mDismissText.isNullOrBlank()) getString(R.string.search_dialog_close) else mDismissText
        alertBuilder.setPositiveButton(dismiss, mDismissListener)

        return alertBuilder.create()
    }

    private var listAdapter: ArrayAdapter<Any?>? = null

    private fun setView(rootView: View?) {
        if (rootView == null) return

        listAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, items)
        mListView = rootView.listView
        mListView?.adapter = listAdapter
        mListView?.isTextFilterEnabled = true
        mListView?.setOnItemClickListener { _, _, position, _ ->
            onSearchableItemClick.onSearchableItemClicked(mListView?.adapter?.getItem(position), position)
            dialog?.dismiss()
        }

        mSearchView = rootView.searchView
        mSearchView?.setOnQueryTextListener(this)
        mSearchView?.setOnCloseListener(this)
        mSearchView?.clearFocus()
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        mSearchView?.clearFocus()
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        if (query.isNullOrBlank()) {
            (mListView?.adapter as ArrayAdapter<*>).filter.filter(null)
        } else {
            (mListView?.adapter as ArrayAdapter<*>).filter.filter(query)
        }
        return true
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putSerializable(CLICK_LISTENER, onSearchableItemClick)
        super.onSaveInstanceState(outState)
    }

    override fun onClose(): Boolean {
        return false
    }

    override fun onPause() {
        super.onPause()
        dismiss()
    }


    fun setDismissText(closeText: String?) {
        mDismissText = closeText
    }


    fun setDismissText(closeText: String?, listener: DialogInterface.OnClickListener) {
        mDismissText = closeText
        mDismissListener = listener
    }


    fun setTitle(dialogTitle: String?) {
        mDialogTitle = dialogTitle
    }
}