package com.example.appcenter_todolist.di

import com.example.appcenter_todolist.network.RetrofitAPI
import com.example.appcenter_todolist.repository.CommentRepository
import com.example.appcenter_todolist.repository.CommentRepositoryImpl
import com.example.appcenter_todolist.repository.MemberRepository
import com.example.appcenter_todolist.repository.MemberRepositoryImpl
import com.example.appcenter_todolist.repository.TodoRepository
import com.example.appcenter_todolist.repository.TodoRepositoryImpl
import com.example.appcenter_todolist.viewmodel.CommentViewModel
import com.example.appcenter_todolist.viewmodel.MemberViewModel
import com.example.appcenter_todolist.viewmodel.TodoViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    single { RetrofitAPI.apiService }

    single<MemberRepository> { MemberRepositoryImpl(get()) }
    single<TodoRepository> { TodoRepositoryImpl(get()) }
    single<CommentRepository> { CommentRepositoryImpl(get()) }

    viewModel{ MemberViewModel(get())}
    viewModel{ TodoViewModel(get())}
    viewModel{ CommentViewModel(get())}

}