package com.example.mvvm_test

import android.app.Application
import androidx.lifecycle.LiveData

class ContactRepository(application: Application) {

    private val contactDatabase = ContactDatabase.getInstance(application)!!
    private val contactDao: ContactDao = contactDatabase.contactDao()
    private val contacts: LiveData<List<Contact>> = contactDao.getAll()

    fun getAll(): LiveData<List<Contact>> {
        return contacts
    }

    fun insert(contact: Contact) {
        try {
            val thread = Thread {
                contactDao.insert(contact)
            }
            thread.start()
        } catch (e: Exception) { }
    }

    fun delete(contact: Contact) {
        try {
            val thread = Thread {
                contactDao.delete(contact)
            }
            thread.start()
        } catch (e: Exception) { }
    }

}