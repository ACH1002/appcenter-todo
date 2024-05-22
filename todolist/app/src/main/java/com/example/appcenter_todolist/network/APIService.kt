package com.example.appcenter_todolist.network

import com.example.appcenter_todolist.model.AddCommentReq
import com.example.appcenter_todolist.model.AddTodoReq
import com.example.appcenter_todolist.model.CommentResponse
import com.example.appcenter_todolist.model.CommonResponse
import com.example.appcenter_todolist.model.SignupMemberReq
import com.example.appcenter_todolist.model.TodoResponse
import com.example.appcenter_todolist.model.UpdateCommentReq
import com.example.appcenter_todolist.model.UpdateTodoReq
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface APIService {
    //회원 가입
    @POST("/members")
    suspend fun signUp(
        @Body signupMemberReq: SignupMemberReq
    ): Response<CommonResponse<Long>>

    //전체 투두 조회
    @GET("/todos")
    suspend fun getTodos(
    ): Response<CommonResponse<List<TodoResponse>>>

    //투두 추가
    @POST("/todos")
    suspend fun addTodo(
        @Body addTodoReq: AddTodoReq
    ): Response<CommonResponse<Long>>

    //단일 투두 조회
    @GET("/todos/{memberId}")
    suspend fun getTodoById(
        @Path("memberId") memberId: Long,
        @Query("todoId") todoId: Long
    ): Response<CommonResponse<TodoResponse>>

    //단일 투두 삭제
    @DELETE("/todos/{memberId}")
    suspend fun deleteTodoById(
        @Path("memberId") memberId: Long,
        @Query("todoId") todoId: Long
    ): Response<CommonResponse<Long>>

    //단일 투두 수정
    @PATCH("/todos/{memberId}")
    suspend fun updateTodoById(
        @Path("memberId") memberId: Long,
        @Query("todoId") todoId: Long,
        @Body updateTodoReq: UpdateTodoReq
    ): Response<CommonResponse<Long>>

    //단일 투두 완료
    @PATCH("/todos/{memberId}/complete")
    suspend fun completeTodoById(
        @Path("memberId") memberId: Long,
        @Query("todoId") todoId: Long
    ): Response<CommonResponse<Long>>

    //댓글 -----------

    //단일 댓글 추가
    @POST("/todos/{memberId}/comments")
    suspend fun addCommentToTodo(
        @Path("memberId") memberId: Long,
        @Query("todoId") todoId: Long,
        @Body addCommentReq: AddCommentReq
    ): Response<CommonResponse<Long>>

    //단일 댓글 조회(수정필요)
    @GET("/comments/{todoId}")
    suspend fun getCommentByTodo(
        @Path("todoId") todoId: Long,
        @Query("commentId") commentId: Long
    ): Response<CommonResponse<CommentResponse>>

    //단일 댓글 삭제
    @DELETE("/comments/{todoId}")
    suspend fun deleteCommentByTodo(
        @Path("todoId") todoId: Long,
        @Query("commentId") commentId: Long
    ): Response<CommonResponse<Long>>

    //단일 댓글 수정
    @PATCH("/comments/{todoId}")
    suspend fun updateCommentByTodo(
        @Path("todoId") todoId: Long,
        @Query("commentId") commentId: Long,
        @Body updateCommentReq: UpdateCommentReq
    ): Response<CommonResponse<Long>>

    //투두 댓글 리스트 조회
    @GET("/todos{memberId}}/comments")
    suspend fun getCommentsByTodo(
        @Path("memberId") memberId: Long,
        @Query("todoId") todoId: Long,
    ): Response<CommonResponse<List<CommentResponse>>>
}