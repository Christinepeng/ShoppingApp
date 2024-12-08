//package com.example.shoppingapp.viewmodel
//
//import androidx.arch.core.executor.testing.InstantTaskExecutorRule
//import com.example.shoppingapp.model.Product
//import com.example.shoppingapp.repository.ProductRepository
//import kotlinx.coroutines.ExperimentalCoroutinesApi
//import kotlinx.coroutines.flow.first
//import kotlinx.coroutines.test.*
//import org.junit.Assert.*
//import org.junit.Rule
//import org.junit.Test
//import org.mockito.kotlin.*
//
//@ExperimentalCoroutinesApi
//class ShopViewModelTest {
//
//    @get:Rule
//    val instantTaskExecutorRule = InstantTaskExecutorRule()
//
//    // 使用 TestCoroutineDispatcher
//    private val testDispatcher = StandardTestDispatcher()
//
//    // Mock 的 ProductRepository
//    private val productRepository: ProductRepository = mock()
//
//    private lateinit var viewModel: ShopViewModel
//
//    @Test
//    fun when_query_changes_search_suggestions_are_updated() = runTest(testDispatcher) {
//        // 准备测试数据
//        val query = "shoes"
//        val products = listOf(
//            Product(id = "1", name = "Running Shoes", title = "Running Shoes", price = 50.0, thumbnail = ""),
//            Product(id = "2", name = "Basketball Shoes", title = "Basketball Shoes", price = 80.0, thumbnail = "")
//        )
//        whenever(productRepository.searchProducts(query)).thenReturn(products)
//
//        // 初始化 ViewModel
//        viewModel = ShopViewModel(productRepository)
//
//        // 设置查询
//        viewModel.onQueryChanged(query)
//
//        // 由于 debounce，需要 advance 时间
//        testDispatcher.scheduler.advanceTimeBy(500)
//
//        // 获取搜索建议
//        val suggestions = viewModel.searchSuggestions.first()
//
//        // 验证建议列表是否正确
//        assertEquals(listOf("Running Shoes", "Basketball Shoes"), suggestions)
//    }
//
//    @Test
//    fun onSearchClicked_updates_searchResults() = runTest(testDispatcher) {
//        // 准备测试数据
//        val query = "shoes"
//        val products = listOf(
//            Product(id = "1", name = "Running Shoes", title = "Running Shoes", price = 50.0, thumbnail = "")
//        )
//        whenever(productRepository.searchProducts(query)).thenReturn(products)
//
//        // 初始化 ViewModel
//        viewModel = ShopViewModel(productRepository)
//
//        // 设置查询并执行搜索
//        viewModel.onQueryChanged(query)
//        viewModel.onSearchClicked()
//
//        // 等待协程完成
//        testDispatcher.scheduler.advanceUntilIdle()
//
//        // 获取搜索结果
//        val results = viewModel.searchResults.first()
//
//        // 验证结果是否正确
//        assertEquals(products, results)
//    }
//
//    @Test
//    fun resetState_clears_all_states() = runTest(testDispatcher) {
//        // 初始化 ViewModel
//        viewModel = ShopViewModel(productRepository)
//
//        // 设置一些状态
//        viewModel.onQueryChanged("shoes")
//        viewModel.onSearchClicked()
//
//        // 重置状态
//        viewModel.resetState()
//
//        // 验证状态是否重置
//        assertEquals("", viewModel.query.value)
//        assertTrue(viewModel.searchSuggestions.value.isEmpty())
//        assertTrue(viewModel.searchResults.value.isEmpty())
//    }
//}
