package com.droidnova.mangavision.data.mapper

import com.droidnova.mangavision.data.local.entities.MangaEntity
import com.droidnova.mangavision.data.remote.dto.MangaDto
import com.droidnova.mangavision.domain.data.Manga

fun MangaDto.toEntity(): MangaEntity = MangaEntity(
    id = id,
    title = title,
    thumb = thumb,
    summary = summary,
    status = status,
    totalChapter = total_chapter
)

fun MangaEntity.toDomain(): Manga = Manga(
    id = id,
    title = title,
    thumb = thumb,
    summary = summary,
    status = status,
    totalChapter = totalChapter
)

fun MangaDto.toDomain() = Manga(
    id = id,
    title = title,
    thumb = thumb,
    summary = summary,
    status = status,
    totalChapter = total_chapter
)



