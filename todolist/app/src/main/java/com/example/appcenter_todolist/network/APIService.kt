package com.example.appcenter_todolist.network

import com.example.appcenter_todolist.model.comment.AddCommentReq
import com.example.appcenter_todolist.model.todo.AddTodoReq
import com.example.appcenter_todolist.model.comment.CommentResponse
import com.example.appcenter_todolist.model.CommonResponse
import com.example.appcenter_todolist.model.member.SignupMemberReq
import com.example.appcenter_todolist.model.todo.TodoResponse
import com.example.appcenter_todolist.model.comment.UpdateCommentReq
import com.example.appcenter_todolist.model.member.LoginMemberReq
import com.example.appcenter_todolist.model.member.MemberResponse
import com.example.appcenter_todolist.model.member.UpdateMemberReq
import com.example.appcenter_todolist.model.todo.UpdateTodoReq
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
    ): Response<CommonResponse<Void>>

    @GET("/members")
    suspend fun getMemberInfo(): Response<CommonResponse<MemberResponse>>

    @DELETE("/members")
    suspend fun deleteMember(): Response<CommonResponse<Void>>

    @PATCH("/members")
    suspend fun updateMember(
        @Body updateMemberReq: UpdateMemberReq
    ): Response<CommonResponse<MemberResponse>>

    @POST("/members/login")
    suspend fun login(
        @Body loginMemberReq: LoginMemberReq
    ): Response<CommonResponse<Void>>

    //전체 투두 조회
    @GET("/todos")
    suspend fun getMyTodos(): Response<CommonResponse<List<TodoResponse>>>

    @GET("/todos/by-nickname")
    suspend fun getTodosByNickname(
        @Query("nickname") nickname : String
    ): Response<CommonResponse<List<TodoResponse>>>

    //투두 추가
    @POST("/todos")
    suspend fun addTodo(
        @Body addTodoReq: AddTodoReq
    ): Response<CommonResponse<TodoResponse>>

    //단일 투두 삭제
    @DELETE("/todos/{todoId}")
    suspend fun deleteTodoById(
        @Path("todoId") todoId: Long,
    ): Response<CommonResponse<Void>>

    //단일 투두 수정
    @PATCH("/todos/{todoId}")
    suspend fun updateTodoById(
        @Path("todoId") todoId: Long,
        @Body updateTodoReq: UpdateTodoReq
    ): Response<CommonResponse<TodoResponse>>

    //단일 투두 완료
    @PATCH("/todos/{todoId}/complete")
    suspend fun completeTodoById(
        @Path("todoId") todoId: Long,
    ): Response<CommonResponse<TodoResponse>>

    //댓글 -----------

    //단일 댓글 추가
    @POST("/comments")
    suspend fun addCommentToTodo(
        @Query("todoId") todoId: Long,
        @Body addCommentReq: AddCommentReq
    ): Response<CommonResponse<CommentResponse>>

//    //단일 댓글 조회(수정필요)
//    @GET("/comments/{todoId}")
//    suspend fun getCommentByTodo(
//        @Path("todoId") todoId: Long,
//        @Query("commentId") commentId: Long
//    ): Response<CommonResponse<CommentResponse>>

    //단일 댓글 삭제
    @DELETE("/comments/{commentId}")
    suspend fun deleteCommentByTodo(
        @Path("commentId") commentId: Long,
    ): Response<CommonResponse<Void>>

    //단일 댓글 수정
    @PATCH("/comments/{commentId}")
    suspend fun updateCommentByTodo(
        @Path("commentId") commentId: Long,
        @Body updateCommentReq: UpdateCommentReq
    ): Response<CommonResponse<CommentResponse>>

    //투두 댓글 리스트 조회
    @GET("/comments")
    suspend fun getCommentsByTodo(
        @Query("todoId") todoId: Long,
    ): Response<CommonResponse<List<CommentResponse>>>
}