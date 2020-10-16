package com.nadillla.tabunganapp.Repository

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import com.nadillla.tabunganapp.Helper.SessionManager
import com.nadillla.tabunganapp.Local.TabunganDb
import com.nadillla.tabunganapp.Local.User
import com.nadillla.tabunganapp.View.MainActivity
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers

class UserRepository(val context: Context) {

    private var tabunganDb=TabunganDb.getInstance(context)
    val session = SessionManager(context)


    fun registerUser(id:Int?,name:String,email:String,password:String,passwordKonf:String,responseHandler:(User)->Unit,errorHandler:(Throwable)->Unit) {


            Observable.fromCallable { tabunganDb.userDao().register(User(id,name,email, password))}
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ item ->
                    responseHandler(User(id,name,email,password))

                }, {
                    errorHandler(it)
                })

    }


    fun loginUser(email:String, password:String, responseHandler: (User) -> Unit, errorHandler: (Throwable) -> Unit){

        Observable.fromCallable { tabunganDb.userDao().login(email,password)}
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({item->
                responseHandler(item)
                session.nama=item.user_name
                session.login = true
            },{
                errorHandler(it)
            })
    }

    fun cekEmail(email: String, responseHandler: (User) -> Unit, errorHandler: (Throwable) -> Unit){
        Observable.fromCallable { tabunganDb.userDao().getUser(email) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({item->
                responseHandler(item)

            },{
                errorHandler(it)

            })

    }


}