package com.example.appcenter_todolist.di

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.appcenter_todolist.DataStoreManager
import com.example.appcenter_todolist.network.RetrofitAPI
import com.example.appcenter_todolist.repository.BucketRepository
import com.example.appcenter_todolist.repository.BucketRepositoryImpl
import com.example.appcenter_todolist.repository.CommentRepository
import com.example.appcenter_todolist.repository.CommentRepositoryImpl
import com.example.appcenter_todolist.repository.MemberRepository
import com.example.appcenter_todolist.repository.MemberRepositoryImpl
import com.example.appcenter_todolist.repository.TodoRepository
import com.example.appcenter_todolist.repository.TodoRepositoryImpl
import com.example.appcenter_todolist.repository.TokenRepository
import com.example.appcenter_todolist.repository.TokenRepositoryImpl
import com.example.appcenter_todolist.viewmodel.AuthViewModel
import com.example.appcenter_todolist.viewmodel.BucketViewModel
import com.example.appcenter_todolist.viewmodel.CommentViewModel
import com.example.appcenter_todolist.viewmodel.MemberViewModel
import com.example.appcenter_todolist.viewmodel.TodoViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

@RequiresApi(Build.VERSION_CODES.O)
val viewModelModule = module {
    single { DataStoreManager(androidContext()) }
    single { RetrofitAPI.apiService }
    single<TokenRepository> { TokenRepositoryImpl(get()) }

    single<MemberRepository> { MemberRepositoryImpl(get()) }
    single<TodoRepository> { TodoRepositoryImpl(get()) }
    single<CommentRepository> { CommentRepositoryImpl(get()) }
    single<BucketRepository> { BucketRepositoryImpl(get()) }

    viewModel{ MemberViewModel(get(), get()) }
    viewModel{ TodoViewModel(get(), get()) }
    viewModel{ CommentViewModel(get()) }
    viewModel{ BucketViewModel(get()) }
    viewModel{ AuthViewModel(get(), get()) }

}