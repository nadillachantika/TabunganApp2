package com.nadillla.tabunganapp.ViewModel

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nadillla.tabunganapp.Local.Tabungan
import com.nadillla.tabunganapp.Repository.TabunganRepository
import kotlinx.android.synthetic.main.dialog_add_tabungan.view.*


class TabunganViewModel(application: Application) : AndroidViewModel(application) {

    val context: Context = application
    val repository = TabunganRepository(application.applicationContext)
    var responseData = MutableLiveData<List<Tabungan>>()
    var _responseData: LiveData<List<Tabungan>> = responseData

    var responseAction = MutableLiveData<Tabungan>()
    var _responseAction: LiveData<Tabungan> = responseAction

    var isError = MutableLiveData<Throwable>()
    var _isError: LiveData<Throwable> = isError

    var empty_ket = MutableLiveData<Boolean>()
    var _empty_ket: LiveData<Boolean> = empty_ket

    var empty_jml = MutableLiveData<Boolean>()
    var _empty_jml: LiveData<Boolean> = empty_jml


    fun getListTabungan() {
        repository.showTabungan({
            responseData.value = it

        }, {
            isError.value = it

        })
    }

    fun addTabungan(id: Int?, tgl: String, jumlah: Int, keterangan: String) {

//        if (jumlah.equals(null)) {
//            empty_jml.value = true
//        } else if (keterangan.isEmpty()) {
//            empty_ket.value = true
//        } else {
            repository.addTabungan(id, tgl, jumlah, keterangan, {
                responseAction.value = it
                Toast.makeText(context, "Data berhasil ditambahkan", Toast.LENGTH_SHORT).show()

            }, {
                isError.value = it
            })
//        }

    }

    fun updateTabungan(id: Int?, tgl: String, jumlah: Int, keterangan: String) {
//        if (jumlah.equals(null)) {
//            empty_jml.value = true
//        } else if (keterangan.isEmpty()) {
//            empty_ket.value = true
//        } else {
            repository.updateTabungan(id, tgl, jumlah, keterangan, {
                responseAction.value = it
                Toast.makeText(context, "Data berhasil diubah", Toast.LENGTH_SHORT).show()
            }, {
                isError.value = it
            })
//        }
    }

    fun deleteTabungan(item: Tabungan) {
        repository.deleteTabungan(item, {
            responseAction.value = it
            Toast.makeText(context, "Data berhasil dihapus", Toast.LENGTH_SHORT).show()
        }, {
            isError.value = it
        })
    }

}





