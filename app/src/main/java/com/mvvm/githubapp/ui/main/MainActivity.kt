package com.mvvm.githubapp.ui.main

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView.OnEditorActionListener
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.VisibleForTesting
import com.mvvm.githubapp.R
import com.mvvm.githubapp.base.DataBindingActivity
import com.mvvm.githubapp.databinding.ActivityMainBinding
import com.mvvm.githubapp.ui.adapter.RepositoryAdapter
import com.skydoves.transformationlayout.onTransformationStartContainer
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : DataBindingActivity() {

  @VisibleForTesting val viewModel: MainViewModel by viewModels()
  private val binding: ActivityMainBinding by binding(R.layout.activity_main)

  private lateinit var searchEditText: EditText
  private lateinit var clearButton: ImageButton

  override fun onCreate(savedInstanceState: Bundle?) {
    onTransformationStartContainer()
    super.onCreate(savedInstanceState)
    binding.apply {
      lifecycleOwner = this@MainActivity
      adapter = RepositoryAdapter()
      vm = viewModel
    }

    searchEditText = binding.etSearch
    clearButton = binding.btClear

    initView()
  }

  private fun initView() {

    clearButton.setOnClickListener{
      searchEditText.setText("")
      searchEditText.clearFocus()
    }

    searchEditText.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
      if (actionId == EditorInfo.IME_ACTION_SEARCH) {
        hideKeyboard()
        searchAction()
        return@OnEditorActionListener true
      }
      false
    })
  }

  private fun searchAction() {
    val query: String = searchEditText.text.toString().trim { it <= ' ' }
    if (query != "") {
      viewModel.fetchRepoList(query)
      searchEditText.clearFocus()
    } else {
      Toast.makeText(this, getString(R.string.please_enter_input), Toast.LENGTH_SHORT).show()
    }
  }

  private fun hideKeyboard() {
    val view = this.currentFocus
    if (view != null) {
      val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
      imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
  }
}
