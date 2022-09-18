package com.example.utvstories.util

import android.content.Context
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.view.get
import androidx.core.widget.addTextChangedListener
import com.example.utvstories.R
import com.example.utvstories.databinding.PinEditTextBinding
import com.example.utvstories.databinding.PinsLinearLayoutBinding

class PinCodeView
@JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val linearLayoutBinding = PinsLinearLayoutBinding.inflate(LayoutInflater.from(context))
    private val editTextBinding get() = PinEditTextBinding.inflate(LayoutInflater.from(context))

    init {
        addView(linearLayoutBinding.root)
        repeat(4) {
            val editText = editTextBinding
            editText.root.id = it
            setListener(editText)
            linearLayoutBinding.root.addView(editText.root)
            if (editText.root.id == 0) {
                (linearLayoutBinding.root[0]).findViewById<EditText>(R.id.edit_text).requestFocus()
            }
        }
    }

    private fun setListener(binding: PinEditTextBinding) {
        binding.editText.setOnKeyListener { view, _, keyEvent ->
            if (keyEvent.keyCode == KeyEvent.KEYCODE_DEL && keyEvent.action == KeyEvent.ACTION_DOWN) {
                val pinOrdinal = binding.root.id
                if ((view as EditText).text.isEmpty() || view.selectionStart == 0)
                    selectPrevious(pinOrdinal)
                else {
                    binding.backCircleIv.visibility = VISIBLE
                }
            }
            false
        }
        binding.editText.addTextChangedListener {
            if (!it.isNullOrEmpty())
                selectNext(binding, binding.root.id)
        }
    }

    private fun selectPrevious(currentId: Int) {
        if (currentId == 0) return
        with(linearLayoutBinding.root[currentId - 1]){
            findViewById<EditText>(R.id.edit_text).apply {
                text.clear()
                requestFocus()
                setSelection(0)
            }
            findViewById<ImageView>(R.id.back_circle_iv).visibility = VISIBLE
        }
    }

    private fun selectNext(binding: PinEditTextBinding, currentId: Int) {
        binding.backCircleIv.visibility = GONE
        if (currentId == linearLayoutBinding.root.childCount - 1) return
        linearLayoutBinding.root[currentId + 1].findViewById<EditText>(R.id.edit_text).apply {
            requestFocus()
            setSelection(0)
        }
    }

    private fun validate() {

    }
}