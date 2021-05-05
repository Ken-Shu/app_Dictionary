/*
 * Copyright (C) 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.wordsapp

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wordsapp.databinding.ActivityMainBinding

/**
 * Main Activity and entry point for the app. Displays a RecyclerView of letters.
 */
class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView

    //首先，創建一個屬性來跟踪應用程序處於哪個佈局狀態是一個好主意。
    // 這將使切換佈局按鈕更加容易。將默認值設置為true，因為默認情況下將使用線性佈局管理器。
    private var isLinearLayoutManager = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerView = binding.recyclerView
        //在運行應用程序之前，還有一件事。
        // 由於現在已經在中設置了佈局管理器和適配器chooseLayout()，
        // 因此您應該替換該代碼onCreate()以調用新方法。onCreate()更改後應如下所示
        chooseLayout()
        // Sets the LinearLayoutManager of the recyclerview
//        recyclerView.layoutManager = LinearLayoutManager(this)
//        recyclerView.adapter = LetterAdapter()
    }
    //當用戶切換按鈕時，您希望項目列表變成項目網格。
    // 如果您從對回收者視圖的了解中回想起，有很多不同的佈局管理器，
    // 其中之一GridLayoutManager允許在一行中包含多個項目
    private fun chooseLayout() {
        if (isLinearLayoutManager) {
            recyclerView.layoutManager = LinearLayoutManager(this)
        } else {
            recyclerView.layoutManager = GridLayoutManager(this, 4)
        }
        recyclerView.adapter = LetterAdapter()
        //在這裡，您可以使用一條if語句來分配佈局管理器。
        // 除了設置之外layoutManager，此方法還分配適配器。
        // LetterAdapter用於列表和網格佈局
    }

    //最初在xml中設置菜單時，您給了它一個靜態圖標。
    // 但是，切換佈局後，應更新圖標以反映其新功能-切換回列表佈局。
    // 在這裡，只需設置線性和網格佈局圖標，即可在下次點擊該按鈕時根據該佈局切換回該佈局
    private fun setIcon(menuItem: MenuItem?){
        if(menuItem == null)
            return
        menuItem.icon =
            if(isLinearLayoutManager)
                ContextCompat.getDrawable(this ,R.drawable.ic_grid_layout)
        else ContextCompat.getDrawable(this,R.drawable.ic_linear_layout)
    }

    //onCreateOptionsMenu：您可以在其中擴展選項菜單並執行任何其他設置
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.layout_meun,menu)

        val layoutButton = menu?.findItem(R.id.action_switch_layout)
        setIcon(layoutButton)
        return true
        //放大佈局後，您將根據佈局調用setIcon()以確保圖標正確。
        // 該方法返回一個Boolean-您返回true此處，因為您希望創建選項菜單
    }

    //onOptionsItemSelected：chooseLayout()選擇該按鈕時實際呼叫的地方
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.action_switch_layout ->{
                isLinearLayoutManager = !isLinearLayoutManager
                chooseLayout()
                setIcon(item)
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
        //每當點擊菜單項時都會調用它，因此您需要確保檢查點擊了哪個菜單項。
        // 您在when上面使用了一條語句。如果id與action_switch_layout菜單項匹配，
        // 則將的值取反isLinearLayoutManager。然後，調用chooseLayout()和相應setIcon()地更新UI。
    }
}
