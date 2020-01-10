package com.wagoodman.stackattack

open class Identity : Any() {
    @JvmField var id: String
    var groupId: String? = null

    fun removeGroupMembership() {
        groupId = null
    }

    val isInGroup: Boolean
        get() = groupId != null

    fun tolkenizeId(): Int {
        return BaseConverterUtil.tolkenizeStringToInt(id)
    }

    init {
        id = BaseConverterUtil.Base62Random()
    }
}