/*
 * Copyright (c) 2021 Razeware LLC
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * Notwithstanding the foregoing, you may not use, copy, modify, merge, publish,
 * distribute, sublicense, create a derivative work, and/or sell copies of the
 * Software in any work that is designed, intended, or marketed for pedagogical or
 * instructional purposes related to programming, coding, application development,
 * or information technology.  Permission for such use, copying, modification,
 * merger, publication, distribution, sublicensing, creation of derivative works,
 * or sale is expressly withheld.
 * 
 * This project and source code may use libraries or frameworks that are
 * released under various Open-Source licenses. Use of those libraries and
 * frameworks are governed by their own individual licenses.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.raywenderlich.android.tipcalculator

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.raywenderlich.android.tipcalculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

  // TODO 3: Add ViewBinding
  private lateinit var binding: ActivityMainBinding
  // TODO 9: get instance of ViewModel
  private val calculatorViewModel: CalculatorViewModel by viewModels()

  private val tag = "MAIN_ACTIVITY"

  override fun onCreate(savedInstanceState: Bundle?) {
    setTheme(R.style.AppTheme)

    super.onCreate(savedInstanceState)
    Log.d(tag, "CREATED")
    binding = ActivityMainBinding.inflate(layoutInflater)
    val view = binding.root
    setContentView(view)
  }

  // TODO 1: Add LifeCycle callbacks
  override fun onStart() {
    super.onStart()
    Log.d(tag, "STARTED")
  }

  override fun onResume() {
    super.onResume()
    Log.d(tag, "RESUMED")
    setInitialValues()
    listenToBillChanges()
    listenToTipChanges()
    observeTotalAmount()
    observerTipAmount()
  }

  override fun onPause() {
    super.onPause()
    Log.d(tag, "PAUSED")
  }

  override fun onStop() {
    super.onStop()
    Log.d(tag, "STOPPED")
  }

  override fun onDestroy() {
    super.onDestroy()
    Log.d(tag, "DESTROYED")
  }

  //TODO 4: Set initial values
  private fun setInitialValues() {
    binding.billInputField.setText("${calculatorViewModel.bill}")
    binding.tipInputField.setText("${calculatorViewModel.tipPercentage}")
  }

  // TODO 5: Add text changed listeners
  private fun listenToBillChanges() {
    binding.billInputField.addTextChangedListener(object : TextWatcher {
      override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

      override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

      override fun afterTextChanged(input: Editable?) {
        val newBill = input.toString()
        // Log before creating a view model
        // TODO 10: Call ViewModel methods to update bill and Tip
        if (newBill.isNotEmpty()) { // 1
          calculatorViewModel.bill = newBill.toInt() // 2
          calculatorViewModel.calculateTip() // 3
          calculatorViewModel.calculateTotalBill()// 4
        }
      }
    })
  }

  private fun listenToTipChanges() {
    binding.tipInputField.addTextChangedListener(object : TextWatcher {
      override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

      override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

      override fun afterTextChanged(input: Editable?) {
        val newTip = input.toString()
        if (newTip.isNotEmpty()) {
          calculatorViewModel.tipPercentage = newTip.toInt()
          calculatorViewModel.calculateTip()
          calculatorViewModel.calculateTotalBill()
        }
      }
    })
  }

  // TODO 11: Observe changes on tip and total amount
  private fun observerTipAmount() {
    calculatorViewModel.tipAmount.observe(this) { tipAmount ->
      binding.tipAmountTextview.text = "$${tipAmount}"
    }
  }

  private fun observeTotalAmount() {
    calculatorViewModel.totalAmount.observe(this) { totalAmount ->
      binding.totalAmountTextview.text = "$${totalAmount}"
    }
  }

}