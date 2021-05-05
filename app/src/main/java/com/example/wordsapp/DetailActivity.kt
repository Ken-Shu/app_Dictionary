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

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wordsapp.databinding.ActivityDetailBinding


class DetailActivity : AppCompatActivity() {
    companion object {
        //不希望我們的 class 是一個 Singleton, 但是我們又需要有一個靜態區塊，這時我們就需要 companion object
        //companion object 和一般的 object 不同，他是隸屬於某個 class 底下的單一實體。
        // 在 Kotlin 中，基本上取代了 java class 對應的 static 區塊

        //注意，除了使用object關鍵字之外，這與定義類非常相似。還有一個關鍵字companion，
        //表示它與DetailActivity類相關聯，我們不需要為其指定單獨的類型名稱。
        //在花括號內，為字母常量添加一個屬性。
        const val LETTER = "letter"
        //const 關鍵字代表著常數，
        // 基本上取代了 public static final 的 java 寫法。
        // 可惜，const 只能使用在基本型態上。
        // 所以當你需要有一個靜態的物件，或是一個靜態的 function
        val SEARCH_PREFIX ="https://www.google.com/search?q="
        //對於此應用程序，您將在Google上搜索該單詞。
        // 第一個搜索結果將是單詞的字典定義。
        // 由於每次搜索都使用相同的基本URL，因此最好將其定義為自己的常量。
        // 在中DetailActivity，修改伴隨對像以添加新的常量SEARCH_PREFIX。這是Google搜索的基本URL
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //使用一個伴隨對象來重構“額外”的代碼


        // Retrieve a binding object that allows you to refer to views by id name
        // Names are converted from snake case to camel case.
        // For example, a View with the id word_one is referenced as binding.wordOne
        val binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Retrieve the LETTER from the Intent extras
        // intent.extras.getString returns String? (String or null)
        // so toString() guarantees that the value will be a String
        //獲取letterId傳入的代碼intent
        val letterId = intent?.extras?.getString(LETTER).toString()
        //這兩個屬性均帶有問號。為什麼是這樣？原因是intent和extras屬性是可為空的，
        // 這意味著它們可能有也可能沒有值。有時您可能想要一個變量null。
        // 該intent屬性可能實際上不是a Intent（如果不是從意圖啟動該活動），
        // 而extras屬性可能實際上不是a Bundle，而是一個稱為的值null。
        // 在Kotlin中，null表示沒有值。該對象可能存在，也可能存在null。
        // 如果您的應用嘗試訪問屬性或調用對像上的null函數，則該應用將崩潰。
        // 要安全訪問此值，請?在名稱後加上a 。如果intent為null，則您的應用甚至不會嘗試訪問extras屬性，
        // 如果extras為null，您的代碼甚至不會嘗試調用getString()。

        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = WordAdapter(letterId, this)

        // Adds a [DividerItemDecoration] between items
        recyclerView.addItemDecoration(
            DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        )

        title = getString(R.string.detail_prefix) + " " + letterId
    }
}