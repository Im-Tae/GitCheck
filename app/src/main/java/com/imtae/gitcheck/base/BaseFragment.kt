package com.imtae.gitcheck.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.imtae.gitcheck.R

abstract class BaseFragment<B: ViewDataBinding>(
    @LayoutRes private val layoutResId: Int,
    private val BR: Int
) : Fragment() {

    lateinit var binding: B

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater, layoutResId, container, false)
        binding.setVariable(BR, this)
        binding.lifecycleOwner = this
        binding.invalidateAll()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}