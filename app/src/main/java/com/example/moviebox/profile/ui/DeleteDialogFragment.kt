package com.example.moviebox.profile.ui

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.moviebox.R

const val ARG_OK = "ARG_OK"
const val KEY_DELETE_DIALOG = "KEY_DELETE_DIALOG"

class DeleteDialogFragment(private val tabNumber: Int) : DialogFragment() {

    private val suffix: String
        get() {
            return when(tabNumber) {
                0 -> getString(R.string.history)
                1 -> getString(R.string.favorite)
                2 -> getString(R.string.wish_list)
                else -> ""
            }
        }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val dialogListener = DialogInterface.OnClickListener { _, _ ->
            val result = Bundle()
            result.putBoolean(ARG_OK, true)
            parentFragmentManager.setFragmentResult(KEY_DELETE_DIALOG, result)
        }

        return AlertDialog.Builder(requireContext())
            .setTitle(R.string.warning)
            .setMessage(getString(R.string.delete_message) + "\"$suffix\"?")
            .setIcon(R.drawable.ic_baseline_warning_24)
            .setPositiveButton(R.string.yes, dialogListener)
            .setNegativeButton(R.string.no, null)
            .create()
    }
}