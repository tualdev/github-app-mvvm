package com.mvvm.githubapp.ui.main

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView.OnEditorActionListener
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.VisibleForTesting
import com.mvvm.githubapp.BuildConfig
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
  private lateinit var filterImageButton: ImageButton

  private var delay: Long = 1000
  private var lastTextEdit: Long = 0
  private var handler: Handler = Handler(Looper.getMainLooper())

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
    filterImageButton = binding.mainToolbar.findViewById(R.id.filterImageButton)

    initView()
  }

  private fun initView() {

    val sortChoicesArray = arrayOf<CharSequence>("Stars", "Forks", "Updated")

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

    searchEditText.addTextChangedListener(object : TextWatcher{

      override fun afterTextChanged(s: Editable?) {

        if (s!!.isNotEmpty()){
          lastTextEdit = System.currentTimeMillis()
          handler.postDelayed(inputFinishChecker, delay)
        }
      }

      override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
      }

      override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        handler.removeCallbacks(inputFinishChecker)
      }

    })

    filterImageButton.setOnClickListener {

      if (BuildConfig.FLAVOR == "free"){
        Toast.makeText(this, "it is free :(", Toast.LENGTH_SHORT).show()
      }else{
        showSortDialog(sortChoicesArray)
      }
    }
  }

  private val inputFinishChecker = Runnable {
    if (System.currentTimeMillis() > lastTextEdit + delay - 500) {
      val query: String = searchEditText.text.toString().trim { it <= ' ' }
      if (query != "") {
        viewModel.fetchRepoList(query, "best%20match", 0)
        searchEditText.clearFocus()
      }
    }
  }

  private fun showSortDialog(sortChoicesArray: Array<CharSequence>) {
    AlertDialog.Builder(this)
            .setSingleChoiceItems(sortChoicesArray, 0, null)
            .setPositiveButton(R.string.okay, DialogInterface.OnClickListener { dialog, _ ->
              dialog.dismiss()
              val selectedPosition: Int = (dialog as AlertDialog).listView.checkedItemPosition

              val query: String = searchEditText.text.toString().trim { it <= ' ' }
              if (query != "") {

                when (selectedPosition) {
                  0 -> {
                    viewModel.fetchRepoList(query, "stars", 0)
                    searchEditText.clearFocus()
                  }
                  1 -> {
                    viewModel.fetchRepoList(query, "forks", 0)
                    searchEditText.clearFocus()
                  }
                  2 -> {
                    viewModel.fetchRepoList(query, "updated", 0)
                    searchEditText.clearFocus()
                  }
                }

              } else {
                dialog.dismiss()
                Toast.makeText(this, getString(R.string.please_enter_input), Toast.LENGTH_SHORT).show()
              }

            }).show()
  }

  private fun searchAction() {
    val query: String = searchEditText.text.toString().trim { it <= ' ' }
    if (query != "") {
      viewModel.fetchRepoList(query, "best%20match", 0)
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
