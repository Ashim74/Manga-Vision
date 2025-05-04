package com.droidnova.mangavision.data.remote.api

import com.droidnova.mangavision.data.remote.dto.MangaApiResponse
import com.droidnova.mangavision.data.remote.dto.MangaDto
import retrofit2.http.GET
import retrofit2.http.Query

interface MangaApi {
    @GET("manga/fetch")
    suspend fun fetchManga(
        @Query("page") page: Int,
        @Query("type") type: String = "all"
    ): MangaApiResponse

}
