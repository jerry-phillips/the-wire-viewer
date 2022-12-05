//package com.sample.wireviewer.characterlist
//
//import org.junit.Before
//import org.junit.Rule
//import org.junit.Test
//import org.mockito.kotlin.mock
//import org.mockito.kotlin.whenever
//
//
//internal class CharacterListViewModelTest {
//    @Rule
//    @JvmField
//    val instantExecutorRule = InstantTaskExecutorRule()
//
//    private lateinit var subject: CharacterListViewModel
//    private val repository: CharacterListRepository = mock()
//
//    @Before
//    fun setup(){
//        subject = CharacterListViewModel(repository)
//        //whenever(repository.getCharacters()).thenReturn(listOf(Character()))
//    }
//
//    @Test
//    fun getCharacters() {
//    }
//
//    @Test
//    fun queryCharacters() {
//    }
//}