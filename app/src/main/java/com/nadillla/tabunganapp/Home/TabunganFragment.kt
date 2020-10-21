package com.nadillla.tabunganapp.Home

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isInvisible
import androidx.lifecycle.ViewModelProvider
import com.nadillla.tabunganapp.Home.Adapter.TabunganAdapter
import com.nadillla.tabunganapp.Local.Tabungan
import com.nadillla.tabunganapp.Local.TabunganDb
import com.nadillla.tabunganapp.R
import com.nadillla.tabunganapp.ViewModel.TabunganViewModel
import kotlinx.android.synthetic.main.activity_home2.*
import kotlinx.android.synthetic.main.dialog_add_tabungan.*
import kotlinx.android.synthetic.main.dialog_add_tabungan.view.*
import kotlinx.android.synthetic.main.dialog_add_tabungan.view.editJumlah
import kotlinx.android.synthetic.main.fragment_tabungan.*
import kotlinx.android.synthetic.main.item_tabungan.view.*
import java.text.SimpleDateFormat
import java.util.*


class TabunganFragment : Fragment() {
    private var dialogView: Dialog? = null
    private lateinit var tbngnViewModel: TabunganViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tabungan, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tbngnViewModel = ViewModelProvider(this).get(TabunganViewModel::class.java)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tbngnViewModel.getListTabungan()

        attachObserve()

        fabAdd.setOnClickListener {
            showAddDialog()

        }

    }

    private fun attachObserve() {
        //getData
        tbngnViewModel._responseData.observe(
            viewLifecycleOwner,
            androidx.lifecycle.Observer { showTabungan(it) })
        tbngnViewModel._isError.observe(
            viewLifecycleOwner,
            androidx.lifecycle.Observer { showError(it) })

        //input
        tbngnViewModel._responseAction.observe(
            viewLifecycleOwner,
            androidx.lifecycle.Observer { inputTabungan(it) })
        tbngnViewModel._isError.observe(
            viewLifecycleOwner,
            androidx.lifecycle.Observer { inputError(it) })
        tbngnViewModel._empty_jml.observe(viewLifecycleOwner, androidx.lifecycle.Observer { emptyJml() })

        tbngnViewModel._empty_ket.observe(viewLifecycleOwner, androidx.lifecycle.Observer { emptyKet() })

    }

    private fun emptyKet() {
        val view = layoutInflater.inflate(R.layout.dialog_add_tabungan, null)
        view?.editTextKeterangan?.error="Keterangan tidak boleh kosong"
    }

    private fun emptyJml() {
        val view = layoutInflater.inflate(R.layout.dialog_add_tabungan, null)
        view?.editJumlah?.error="Masukkan jumlah"
    }


    private fun inputError(it: Throwable?) {
        Log.d("TAG", "inputTabungan: gagal")


    }

    private fun inputTabungan(it: Tabungan) {
        Log.d("TAG", "inputTabungan: OK")
        dialogView?.dismiss()
        tbngnViewModel.getListTabungan()

    }

    private fun showError(it: Throwable?) {
        Toast.makeText(context, it?.message, Toast.LENGTH_SHORT).show()
    }

    private fun showTabungan(item: List<Tabungan>) {
        listTabungan.adapter =
            TabunganAdapter(item, object : TabunganAdapter.OnClickListener {
                override fun update(item: Tabungan?) {
                    showUpdateTabungan(item)
                }

                override fun delete(item: Tabungan?) {
                    AlertDialog.Builder(context).apply {
                        setTitle("Hapus")
                        setMessage("Yakin hapus data ?")
                        setCancelable(false)

                        setPositiveButton("Yakin") { dialogInterface, i ->
                            if (item != null) {
                                tbngnViewModel.deleteTabungan(item)
                            }
                        }
                        setNegativeButton("Batal") { dialogInterface, i ->
                            dialogInterface.dismiss()
                        }
                    }.show()
                }
            })
    }

    private fun showAddDialog() {
        val dialog = AlertDialog.Builder(context)
        val view = layoutInflater.inflate(R.layout.dialog_add_tabungan, null)
        dialog.setView(view)

        view.editTgl.setText(getDate())
        view.btnSimpan.setOnClickListener {
            if (view.editJumlah.text.toString().isEmpty()) {
                view.editJumlah.error = "tidak boleh kosong"
            } else if (view.editTextKeterangan.text.isEmpty()) {
                view.editTextKeterangan.error = "tidak boleh kosong"
            } else {
                tbngnViewModel.addTabungan(
                    null, getDate(),
                    view.editJumlah.text.toString().toInt(),
                    view.editTextKeterangan.text.toString()
                )

            }
        }
        view.btnClose.setOnClickListener {
            dialogView?.dismiss()
        }
        dialogView = dialog.create()
        dialogView?.show()
    }


    private fun showUpdateTabungan(item: Tabungan?) {
        val dialog = AlertDialog.Builder(context)
        val view = layoutInflater.inflate(R.layout.dialog_add_tabungan, null)
        dialog.setView(view)

        view.btnSimpan.text = "Update"

        view.editTgl.setText((item?.tgl))
        view.editTextKeterangan.setText((item?.keterangan))
        view.editJumlah.setText(item?.jumlah.toString())

        view.btnSimpan.setOnClickListener {
            if (view.editTextKeterangan.text.isEmpty()) {
                view.editTextKeterangan.error = "tidak boleh kosong"
            } else if (view.editJumlah.text.isEmpty()) {
                view.editJumlah.error = "tidak boleh kosong"
            } else {
                tbngnViewModel.updateTabungan(
                    item?.id,
                    getDate(),
                    view.editJumlah.text.toString().toInt(),
                    view.editTextKeterangan.text.toString()
                )
            }
        }
        view.btnClose.setOnClickListener {
            dialogView?.dismiss()
        }
        dialogView = dialog.create()
        dialogView?.show()

    }

    private fun getDate(): String {
        val date = Calendar.getInstance().time
        val format = SimpleDateFormat.getDateInstance()
        val formatDate = format.format(date)
        return formatDate
    }
}
