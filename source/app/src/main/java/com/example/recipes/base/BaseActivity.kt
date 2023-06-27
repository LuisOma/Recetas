package com.example.recipes.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

typealias InflateActivity<T> = (LayoutInflater, ViewGroup?, Boolean) -> T

abstract class BaseActivity<VB : ViewBinding>(private val inflate: InflateActivity<VB>) :
    AppCompatActivity() {

    private var _binding: VB? = null
    val binding get() = _binding!!

    private var baseViewModel = BaseViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = inflate.invoke(layoutInflater, null, false)
        _binding?.let {
            setContentView(it.root)
            observe()
            initComponents()
        }
    }

    abstract fun initComponents()
    open fun observe() {
        baseViewModel.isSessionActive.observe(this) {
            if (!it) {
                //TODO show dialog for logout
            }
        }
    }

}