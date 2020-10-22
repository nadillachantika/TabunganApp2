package com.nadillla.tabunganapp.ViewModel

import android.app.Application
import android.content.Context
import android.util.EventLog
import android.util.Patterns
import android.widget.Toast
import androidx.lifecycle.*
import com.nadillla.tabunganapp.Helper.SessionManager
import com.nadillla.tabunganapp.Local.User
import com.nadillla.tabunganapp.Repository.UserRepository
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.fragment_register1.*

class UserViewModel(application: Application) : AndroidViewModel(application) {


    val repoUser = UserRepository(application.applicationContext)
    var responseDataUser = MutableLiveData<List<User>>()
    var _responseDataUser: LiveData<List<User>> = responseDataUser

    var responseActionUser = MutableLiveData<User>()
    var _responseActionUser: LiveData<User> = responseActionUser

    var isErrorUser = MutableLiveData<Throwable>()
    var _isErrorUser: LiveData<Throwable> = isErrorUser

    var _password_empty = MutableLiveData<Boolean>()
    var password_empty: LiveData<Boolean> = _password_empty

    var _password_notmatch = MutableLiveData<Boolean>()
    var passwordnotmatch: LiveData<Boolean> = _password_notmatch

    var _password_less = MutableLiveData<Boolean>()
    var password_less: LiveData<Boolean> = _password_less

    var _empty_email = MutableLiveData<Boolean>()
    var empty_email: LiveData<Boolean> = _empty_email

    var _empty_name = MutableLiveData<Boolean>()
    var empty_name: LiveData<Boolean> = _empty_name

    var _wrong_email = MutableLiveData<Boolean>()
    var wrong_email: LiveData<Boolean> = _wrong_email


    fun gotEmail(email: String, name: String) {
        if  (name.isEmpty()) {
            _empty_name.value = true
        } else if (email.isEmpty()) {
            _empty_email.value = true
        } else if (validasiEmail(email) == false) {
            _wrong_email.value = true
        } else {
            repoUser.cekEmail(email, {
                responseActionUser.value = it

            }, {
                isErrorUser.value = it
            })
        }
    }


    fun loginUser(email: String, password: String) {
        if(email.isEmpty()){
           _empty_email.value=true
        }else if(password.isEmpty()){
            _password_empty.value=true
        }else {
            repoUser.loginUser(email, password, {
                responseActionUser.value = it

            }, {
                isErrorUser.value = it


            })
        }
    }

    fun registerUser(
        id: Int?,
        nama: String,
        email: String,
        password: String,
        passwordKonf: String
    ) {
        if (password.isEmpty()) {
            _password_empty.value = true
        } else if (password != passwordKonf) {
            _password_notmatch.value = true
        } else if (password.length < 6) {
            _password_less.value = true
        } else {
            repoUser.registerUser(id, nama, email, password, passwordKonf, {
                responseActionUser.value = it
            }, {
                isErrorUser.value = it

            })
        }
    }

    private fun validasiEmail(email: String): Boolean {
        if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return true
        } else {
            return false
        }
    }




}