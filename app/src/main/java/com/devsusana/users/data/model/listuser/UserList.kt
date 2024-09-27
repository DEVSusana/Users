package com.devsusana.users.data.model.listuser

data class UserList(
    val `data`: List<UserDataList>,
    val page: Int,
    val per_page: Int,
    val userSupportList: UserSupportList,
    val total: Int,
    val total_pages: Int
)