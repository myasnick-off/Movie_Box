package com.example.moviebox.contacts.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.database.Cursor
import android.os.Bundle
import android.provider.ContactsContract
import android.telephony.SmsManager
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SimpleAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.example.moviebox.R
import com.example.moviebox.databinding.FragmentContactsBinding
import com.example.moviebox._core.ui.model.Contact
import com.example.moviebox._core.data.remote.model.GenreDTO
import com.example.moviebox._core.data.remote.model.MovieDetailsDTO
import java.lang.StringBuilder

class ContactsFragment : Fragment() {

    private var _binding: FragmentContactsBinding? = null
    private val binding get() = _binding!!
    private var movieData: MovieDetailsDTO? = null
    private var smsGranted = false
    private var contactList: ArrayList<Contact> = arrayListOf()

    private val readContactsPermissionResult =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { result ->
            if (result) {
                getContacts()
            } else {
                Toast.makeText(
                    context,
                    getString(R.string.need_permissions_to_read_contacts),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    private val sendSmsPermissionResult =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { result ->
            if (result) {
                smsGranted = true
            } else {
                Toast.makeText(
                    context,
                    getString(R.string.need_permissions_to_send_sms),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentContactsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        movieData = arguments?.getParcelable(KEY_DETAILS)
        checkReadContactsPermission()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    // ?????????? ???????????????? ?????????????? ???????????????????? ???? ???????????? ?????????????????? ????????????????
    private fun checkReadContactsPermission() {
        context?.let { it ->
            when (PackageManager.PERMISSION_GRANTED) {
                ContextCompat.checkSelfPermission(
                    it, Manifest.permission.READ_CONTACTS
                ) -> {
                    getContacts()
                }
                else -> {
                    readContactsPermissionResult.launch(Manifest.permission.READ_CONTACTS)
                }
            }
        }
    }

    // ?????????? ???????????????? ?????????????? ???????????????????? ???? ???????????????? ??????
    private fun checkSendSmsPermission() {
        context?.let { it ->
            when (PackageManager.PERMISSION_GRANTED) {
                ContextCompat.checkSelfPermission(it, Manifest.permission.SEND_SMS) -> {
                    smsGranted = true
                }
                else -> {
                    sendSmsPermissionResult.launch(Manifest.permission.SEND_SMS)
                }
            }
        }
    }

    // ?????????? ?????????????? ???????????? ?????????????????? ?? ???????????????? ??????????????????
    @SuppressLint("Range")
    private fun getContacts() {
        // ?????????????????????? ???????????? ?????????????????? ???? ???????????????????? ??????????????-????????????????????
        context?.let { it ->
            val cursorWithContacts: Cursor? = it.contentResolver.query(
                ContactsContract.Contacts.CONTENT_URI,
                null,
                null,
                null,
                ContactsContract.Contacts.DISPLAY_NAME + " ASC"
            )

            // ???????? ???????????? ???? null ???????????????????? ???? ???????? ?????????????????? ??????????????
            cursorWithContacts?.let { cursor ->
                for (i in 0..cursor.count) {
                    if (cursor.moveToPosition(i)) {
                        // ?????????????? ?????? ????????????????
                        val name =
                            cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                        // ?????????????? id ????????????????
                        val contactId =
                            cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID))
                        // ?????????????? ???????? ?????????????? ???????????? ???????????????? ?? ?????????? ????????????????
                        val hasPhone =
                            cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))

                        // ???????? ?????????? ???????????????? ????????
                        if (hasPhone.toInt() > 0) {
                            // ?????????????????????? ?????????? ?????????????? ?????????????????? ?????? ?????????????? ???????????????? (???? ?????? id)
                            val phoneCursor: Cursor? = it.contentResolver.query(
                                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                null,
                                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + contactId,
                                null,
                                null
                            )
                            // ?????????????????????? ?????????? ?????????????? ?????????????????? ???? null, ?????????? ???????????? ???? ????????????
                            if (phoneCursor != null && phoneCursor.moveToPosition(0)) {
                                val phone = phoneCursor.getString(
                                    phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                                )
                                //?????????????????? ?????????? ???????????? ?? ???????????? ??????????????????, ???????????????? ???????????????? ???????????? ???????????? ??????????
                                if (phone.length >= 11) {
                                    contactList.add(Contact(i, name, phoneFormatter(phone)))
                                }
                                //?????????????????? ?????????? View ?? Layout ??????????????????
//                                addView(name, phone)
                            }
                            phoneCursor?.close()
                        }
                    }
                }
            }
            cursorWithContacts?.close()
        }
        fillList()
    }

    // ?????????? ???????????????????? ListView ?????????????? ?????????????????? ?? ???????????????? ?????? ?????? ?????????????? ???? ???????? ???? ??????
    private fun fillList() = with(binding) {
            // ?????????????????????? ???????????? ?????????????????? ?? ???????????????? ?????? ListView-???????????????? ??????????????????
        val data: ArrayList<Map<String, String>> = arrayListOf()
        for (i in 0 until contactList.size) {
            val map: HashMap<String, String> = hashMapOf()
            map[ATTRIBUTE_NAME] = contactList[i].name
            map[ATTRIBUTE_PHONE] = contactList[i].phone
            data.add(map)
        }
        // ?????????????? ???????????? ???????? ??????????????????, ???? ?????????????? ?????????? ???????????????? ????????????
        val from = arrayOf(ATTRIBUTE_NAME, ATTRIBUTE_PHONE)
        // ?????????????? ???????????? ID View-??????????????????????, ?? ?????????????? ?????????? ?????????????????? ????????????
        val to = intArrayOf(R.id.item_contact_name, R.id.item_contact_phone)
        // ?????????????? ?????????????? ?????? ListView
        val adapter = SimpleAdapter(requireContext(), data, R.layout.item_contacts, from, to)
        contactsListView.adapter = adapter

        // ???????????????????? ?????????????? ???? ?????????????? ???????????? ?? ListView
        contactsListView.setOnItemClickListener { _, _, position, _ ->
            checkSendSmsPermission()
            if (smsGranted) {
                showDialog(contactList[position].name, contactList[position].phone)
            }
        }
    }

    // ?????????? ?????????????? ?????????????? ???? ???????????????? ?????? ??????????????????
    private fun showDialog(name: String, phone: String) {
        AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.send_sms_to_contact) + " $name ?" )
            .setMessage(buildMessage(movieData))
            .setPositiveButton(getString(R.string.yes)) { _, _ ->
                sendSMS(phone)
            }
            .setNegativeButton(getString(R.string.no)) { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }

    // ?????????? ???????????????? SMS ?????????????????? ?? ?????????????????????? ?? ???????????? ???? ???????????????????? ????????????
    private fun sendSMS(phone: String?) {
        if (phone != null) {
            try {
                val smsManager = SmsManager.getDefault()
                val message = smsManager.divideMessage(buildMessage(movieData))
                smsManager.sendMultipartTextMessage(phone, null, message, null, null)
                Toast.makeText(requireContext(), getString(R.string.sms_sent_success), Toast.LENGTH_SHORT)
                    .show()
            } catch (e: Exception) {
                Toast.makeText(requireContext(), getString(R.string.fail_sending_sms), Toast.LENGTH_SHORT)
                    .show()
            }
        } else {
            Toast.makeText(requireContext(), getString(R.string.fail_sending_sms), Toast.LENGTH_SHORT)
                .show()
        }
    }

    // ?????????? ???????????????????????????? ?????????????????????? ???????????? ?? ???????????????? ????????????
    private fun phoneFormatter(phone: String): String {
        if (phone.startsWith("+7")) {
            phone.replace("+7", "8")
        }
        Regex("[^0123456789]").replace(phone, "")
        return phone
    }

    // ?????????? ?????????????????????? ???????????? ?????? ??????????????????
    private fun buildMessage(movieData: MovieDetailsDTO?): String {
        val messageBuilder = StringBuilder()
            .append(getString(R.string.my_recommends))
            .append(getString(R.string.title))
            .append(movieData?.title).append(". ")
            .append(getString(R.string.release_date))
            .append(movieData?.releaseDate?.substring(0, 4)).append(". ")
            .append(getString(R.string.genres))
            .append(genreListToString(movieData?.genres)).append(". ")
            .append(getString(R.string.rating))
            .append(movieData?.voteAverage)
        return messageBuilder.toString()
    }

    // ?????????? ???????????????????????????? ?????????????? ?? ?????????????? ?? ?????????????????? ????????????
    private fun genreListToString(genres: List<GenreDTO>?): String {
        val result = StringBuilder()
        genres?.let {
            for (genre in it) {
                result.append(genre.name)
                if (genre != it.last()) {
                    result.append(", ")
                }
            }
        }
        return result.toString()
    }

    companion object {

        private const val ATTRIBUTE_NAME = "name"
        private const val ATTRIBUTE_PHONE = "phone"
        private const val KEY_DETAILS = "movie_details"

        fun newInstance(bundle: Bundle): ContactsFragment {
            return ContactsFragment().apply { arguments = bundle }
        }
    }
}