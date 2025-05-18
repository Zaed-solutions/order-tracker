package com.zaed.ordertracker.domain.model

data class MpGroup(
    val id :String = "",
    val name :String = "",
    val color :Int = 0,
    val members :List<MpMember> = emptyList(),
    val canEditName :Boolean = true,
    val canDelete :Boolean = true,
)
