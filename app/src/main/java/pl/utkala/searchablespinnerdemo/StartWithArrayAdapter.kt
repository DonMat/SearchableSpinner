package pl.utkala.searchablespinnerdemo

import android.content.Context
import android.widget.ArrayAdapter
import android.widget.Filter

class StartWithArrayAdapter(context: Context, resourceId: Int, private val items: List<String>) : ArrayAdapter<String>(context, resourceId, items) {
    private var mData = items

    override fun getCount(): Int {
        return mData.size
    }

    override fun getItem(position: Int): String {
        return mData[position]
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val result = FilterResults()
                if (!constraint.isNullOrBlank()) {
                    synchronized(this) {
                        val filteredItems = ArrayList<String>()
                        for (i in (items.indices)) {
                            if (items[i].startsWith(constraint, ignoreCase = true))
                                filteredItems.add(items[i])
                        }
                        result.count = filteredItems.size
                        result.values = filteredItems
                    }
                } else {
                    synchronized(this) {
                        result.values = items
                        result.count = items.size
                    }
                }
                return result
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                if (results?.values != null) {
                    mData = results.values as List<String>
                    notifyDataSetChanged()
                }
            }
        }
    }
}