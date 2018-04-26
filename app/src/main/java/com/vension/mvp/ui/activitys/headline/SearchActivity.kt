//package com.vension.mvp.ui.activitys.headline
//
//import android.app.SearchManager
//import android.content.ComponentName
//import android.content.Context
//import android.graphics.Color
//import android.os.Bundle
//import android.support.design.widget.TabLayout
//import android.support.v4.app.Fragment
//import android.support.v4.view.MenuItemCompat
//import android.support.v4.view.ViewPager
//import android.support.v7.app.AlertDialog
//import android.support.v7.widget.SearchView
//import android.support.v7.widget.Toolbar
//import android.text.TextUtils
//import android.util.Log
//import android.view.LayoutInflater
//import android.view.Menu
//import android.view.View
//import android.widget.LinearLayout
//import android.widget.ListView
//import android.widget.TextView
//import com.google.android.flexbox.FlexDirection
//import com.google.android.flexbox.FlexWrap
//import com.google.android.flexbox.FlexboxLayout
//import com.jakewharton.rxbinding2.view.RxView
//import com.jakewharton.rxbinding2.widget.RxSearchView
//import com.vension.mvp.base.BaseActivity
//import io.reactivex.Observable
//import io.reactivex.ObservableOnSubscribe
//import io.reactivex.android.schedulers.AndroidSchedulers
//import io.reactivex.schedulers.Schedulers
//import java.util.ArrayList
//import java.util.concurrent.TimeUnit
//import com.vension.mvp.R
//import com.vension.mvp.beans.headline.SearchHistoryBean
//import com.vension.mvp.beans.headline.SearchRecommentBean
//import com.vension.mvp.db.sqlite.dao.SearchHistoryDao
//import com.vension.mvp.http.RetrofitFactory
//import com.vension.mvp.showToast
//import com.vension.mvp.ui.adapters.headline.SearchHistoryAdapter
//import com.vension.mvp.ui.adapters.headline.SearchSuggestionAdapter
//import com.vension.mvp.utils.ErrorAction
//import com.vension.mvp.utils.ViewUtil
//import kotlinx.android.synthetic.main.activity_news_search.*
//import kotlinx.android.synthetic.main.fragment_gank_image_preview.*
//
///**
// * ========================================================
// * 作  者：Vension
// * 日  期：2018/4/24 14:32
// * 描  述：头条·搜索
// * ========================================================
// */
//
//class SearchActivity : BaseActivity() {
//
//    private val TAG = "SearchActivity"
////    private var tabLayout: TabLayout? = null
////    private var viewPager: ViewPager? = null
////    private val titles = arrayOf("综合", "视频", "图集", "用户(beta)", "问答")
//    private var searchView: SearchView? = null
//    private var historyAdapter: SearchHistoryAdapter? = null
//    private var suggestionAdapter: SearchSuggestionAdapter? = null
//    private val dao = SearchHistoryDao()
//
//
//    override fun attachLayoutRes(): Int {
//        return R.layout.activity_news_search
//    }
//
//    /**
//     * 初始化 Toolbar
//     */
//    private fun initToolBar(toolbar: Toolbar, homeAsUpEnabled: Boolean, title: String) {
//        toolbar.title = title
//        setSupportActionBar(toolbar)
//        supportActionBar!!.setDisplayHomeAsUpEnabled(homeAsUpEnabled)
//    }
//    override fun initViewAndData(savedInstanceState: Bundle?) {
//        super.initViewAndData(savedInstanceState)
//        initToolBar(toolbar_search, true, "")
//        // 热门搜索
//        flexbox_layout.flexDirection = FlexDirection.ROW
//        flexbox_layout.flexWrap = FlexWrap.WRAP
//        tv_clear.setOnClickListener(this)
//        ViewUtil.singleClick(tv_refresh, { o ->
//            // 防抖
//            flexbox_layout.removeAllViews()
//            getSearchHotWord()
//        })
//        // 搜索结果
////        resultLayout = findViewById(R.id.result_layout)
////        tabLayout = findViewById(R.id.tab_layout)
////        viewPager = findViewById(R.id.view_pager)
////        tabLayout.setBackgroundColor(SettingUtil.getInstance().getColor())
////        tabLayout.setupWithViewPager(viewPager)
////        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE)
//        // 搜索建议
//        suggestionAdapter = SearchSuggestionAdapter(this, -1)
//        history_list.adapter = suggestionAdapter
//        history_list.setOnItemClickListener { parent, view, position, id ->
//            val keyWord = suggestionAdapter.getItem(position).keyword
//            searchView?.clearFocus()
//            searchView?.setQuery(keyWord, true)
//        }
//        // 搜索历史
//        historyAdapter = SearchHistoryAdapter(this, -1)
//        history_list.adapter = historyAdapter
//        history_list.setOnItemClickListener { parent, view, position, id ->
//            val keyWord = historyAdapter?.getItem(position)?.keyWord
//            searchView?.clearFocus()
//            searchView?.setQuery(keyWord, true)
//        }
//    }
//
//    private fun initSearchLayout(query: String) {
//        hotword_layout.visibility = View.GONE
//        suggestion_list.visibility = View.GONE
////        resultLayout.setVisibility(View.VISIBLE)
//        val fragmentList = ArrayList<Fragment>()
////        for (i in 1 until titles.size + 1) {
////            fragmentList.add(SearchResultFragment.newInstance(query, i.toString() + ""))
////        }
////        val pagerAdapter = BasePagerAdapter(supportFragmentManager, fragmentList, titles)
////        viewPager.setAdapter(pagerAdapter)
////        viewPager.setOffscreenPageLimit(fragmentList.size)
////        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
////            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
////
////            }
////
////            override fun onPageSelected(position: Int) {
////                if (position == 0) {
////                    if (slidrInterface != null) {
////                        slidrInterface.unlock()
////                    }
////                } else {
////                    if (slidrInterface != null) {
////                        slidrInterface.lock()
////                    }
////                }
////            }
////
////            override fun onPageScrollStateChanged(state: Int) {
////
////            }
////        })
//    }
//
//    override fun requestLoadData() {
//        getSearchHotWord()
//        getSearchHistory()
//    }
//
//
//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        menuInflater.inflate(R.menu.menu_search, menu)
//        val item = menu.findItem(R.id.action_search)
//        searchView = MenuItemCompat.getActionView(item) as SearchView
//        // 关联检索配置与 SearchActivity
//        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
//        val searchableInfo = searchManager.getSearchableInfo(
//                ComponentName(applicationContext, SearchActivity::class.java))
//        searchView?.setSearchableInfo(searchableInfo)
//        searchView?.onActionViewExpanded()
//        //        // 设置搜索文字样式
//        //        EditText searchEditText = (EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
//        //        searchEditText.setTextColor(getResources().getColor(R.color.textColorPrimary));
//        //        searchEditText.setHintTextColor(getResources().getColor(R.color.textColorPrimary));
//        //        searchEditText.setBackgroundColor(Color.WHITE);
//        setOnQuenyTextChangeListener()
//
//        return super.onCreateOptionsMenu(menu)
//    }
//    val TAG_COLORS = intArrayOf(
//            Color.parseColor("#90C5F0"), Color.parseColor("#91CED5"),
//            Color.parseColor("#F88F55"), Color.parseColor("#C0AFD0"),
//            Color.parseColor("#E78F8F"), Color.parseColor("#67CCB7"),
//            Color.parseColor("#F6BC7E")
//    )
//    private fun getSearchHotWord() {
//        RetrofitFactory.toutiaoService.getSearchRecomment()
//                .subscribeOn(Schedulers.io())
//                .observeOn(Schedulers.io())
//                .map({ searchRecommentBean ->
//                    val suggest_word_list
//                            = searchRecommentBean.data.suggest_word_list
//                    val hotList = ArrayList<String>()
//                    for (i in suggest_word_list.indices) {
//                        if (suggest_word_list[i].type == "recom") {
//                            hotList.add(suggest_word_list[i].word)
//                        }
//                    }
//                    hotList
//                })
//                .observeOn(AndroidSchedulers.mainThread())
//                .`as`(this.bindAutoDispose())
//                .subscribe({ list ->
//                    for (i in 0 until list.size()) {
//                        val tv = LayoutInflater.from(this@SearchActivity).inflate(R.layout.item_news_search_sug_text, flexboxLayout, false) as TextView
//                        val keyWord = list.get(i)
//                        val color = TAG_COLORS[i % TAG_COLORS.size]
//                        tv.setText(keyWord)
//                        tv.setBackgroundColor(color)
//                        tv.setTextColor(Color.WHITE)
//                        tv.setOnClickListener { view ->
//                            searchView.clearFocus()
//                            searchView.setQuery(keyWord, true)
//                        }
//                        flexbox_layout.addView(tv)
//                        if (i == 7) {
//                            return@subscribe
//                        }
//                    }
//                }, ErrorAction.error())
//    }
//
//    private fun getSearchHistory() {
//        Observable
//                .create<List<SearchHistoryBean>>({ e ->
//                    val list = dao.queryAll()
//                    e.onNext(list)
//                } as ObservableOnSubscribe<List<SearchHistoryBean>>)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .`as`(this.bindAutoDispose())
//                .subscribe({ list -> historyAdapter.updateDataSource(list) }, ErrorAction.error())
//    }
//
//    private fun getSearchSuggest(keyWord: String) {
//        RetrofitFactory.toutiaoService
//                .getSearchSuggestion(keyWord.trim { it <= ' ' })
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .`as`(this.bindAutoDispose())
//                .subscribe({ bean -> suggestionAdapter.updateDataSource(bean.getData()) }, ErrorAction.error())
//    }
//
//    private fun setOnQuenyTextChangeListener() {
//        RxSearchView.queryTextChangeEvents(searchView)
//                .throttleLast(100, TimeUnit.MILLISECONDS)
//                .debounce(300, TimeUnit.MILLISECONDS)
//                .observeOn(AndroidSchedulers.mainThread())
//                .`as`(this.bindAutoDispose())
//                .subscribe({ searchViewQueryTextEvent ->
//                    val keyWord = searchViewQueryTextEvent.queryText() + ""
//                    Log.d(TAG, "accept: $keyWord")
//                    if (searchViewQueryTextEvent.isSubmitted()) {
//                        searchView.clearFocus()
//                        initSearchLayout(keyWord)
//                        Thread {
//                            if (dao.queryisExist(keyWord)) {
//                                dao.update(keyWord)
//                            } else {
//                                dao.add(keyWord)
//                            }
//                        }.start()
//                        return@subscribe
//                    }
//                    if (!TextUtils.isEmpty(keyWord)) {
//                        getSearchSuggest(keyWord)
//                        hotword_layout.visibility = View.GONE
////                        resultLayout.setVisibility(View.GONE)
//                        suggestion_list.visibility = View.VISIBLE
//                    } else {
//                        getSearchHistory()
//                        if (hotword_layout.visibility != View.VISIBLE) {
//                            hotword_layout.visibility = View.VISIBLE
//                        }
////                        if (resultLayout.getVisibility() != View.GONE) {
////                            resultLayout.setVisibility(View.GONE)
////                        }
//                        if (suggestion_list.visibility != View.GONE) {
//                            suggestion_list.visibility = View.GONE
//                        }
//                    }
//                }, ErrorAction.error())
//    }
//
//    override fun onPause() {
//        super.onPause()
//        searchView?.clearFocus()
//    }
//
//    override fun onClick(view: View?) {
//        super.onClick(view)
//        when(view?.id){
//            R.id.tv_clear-> {
//                AlertDialog.Builder(this)
//                    .setMessage(R.string.delete_all_search_history)
//                    .setPositiveButton(R.string.button_enter, { dialog, which ->
//                        Thread {
//                            dao.deleteAll()
//                            getSearchHistory()
//                        }.start()
//                        dialog.dismiss()
//                    })
//                    .setNegativeButton(R.string.button_cancel, { dialog, which -> dialog.dismiss() })
//                    .show()
//            }
//        }
//    }
//
//    override fun onBackPressed() {
//        if (suggestion_list.visibility != View.GONE) {
//            // 关闭搜索建议
//            suggestion_list.visibility = View.GONE
//            hotword_layout.visibility = View.VISIBLE
////        } else if (resultLayout.getVisibility() != View.GONE) {
////            // 关闭搜索结果
////            searchView.setQuery("", false)
////            searchView.clearFocus()
////            resultLayout.setVisibility(View.GONE)
////            hotword_layout.setVisibility(View.VISIBLE)
//        } else {
//            finish()
//        }
//    }
//
//}