package com.juanmartin.videos.data.dto.comercios

data class Photo(
    val _id: String,
    val format: String,
    val thumbnails: ThumbnailsX,
    val url: String
)