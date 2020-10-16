package com.nadillla.tabunganapp.ViewModel

import android.app.Application
import android.content.Context
import android.util.EventLog
import android.widget.Toast
import androidx.lifecycle.*
import com.nadillla.tabunganapp.Helper.SessionManager
import com.nadillla.tabunganapp.Local.User
import com.nadillla.tabunganapp.Repository.UserRepository

class UserViewModel(application: Application):AndroidViewModel(application) {

    val context :Context = application


    val repoUser = UserRepository(application.applicationContext)
    var responseDataUser = MutableLiveData<List<User>>()
    var _responseDataUser : LiveData<List<User>> =responseDataUser

    var responseActionUser= MutableLiveData<User>()
    var _responseActionUser : LiveData<User> = responseActionUser

    var isErrorUser = MutableLiveData<Throwable>()
    var _isErrorUser : LiveData<Throwable> = isErrorUser

//    private val messsage = MutableLiveData<String>()
//    val _message : LiveData<String> = messsage


    fun gotEmail(email: String){
        repoUser.cekEmail(email,{
            responseActionUser.value=it

        },{
            isErrorUser.value=it
        })
    }
    fun loginUser(email:String,password:String){

        repoUser.loginUser(email,password,{
            responseActionUser.value=it


        },{
            isErrorUser.value=it


        })
    }

    fun registerUser(id:Int?,nama:String,email:String,password: String,passwordKonf:String) {
        if (password.isEmpty()) {
            Toast.makeText(context, "Password tidak boleh kosong", Toast.LENGTH_SHORT).show()
        } else if (password != passwordKonf) {
            Toast.makeText(context, "Password Tidak Sama", Toast.LENGTH_SHORT).show()
        } else if (password.length < 6) {
            Toast.makeText(context, "Password harus lebih dari 5 karakter", Toast.LENGTH_SHORT)
                .show()
        } else {

            repoUser.registerUser(id, nama, email, password, passwordKonf, {
                responseActionUser.value = it
            }, {
                isErrorUser.value = it

            })
        }
    }
}