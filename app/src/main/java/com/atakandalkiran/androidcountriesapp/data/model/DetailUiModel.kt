package com.atakandalkiran.androidcountriesapp.data.model

open class DetailUiModel(
    open val id: Int,
    open val type: Int
)

data class DetailHeaderModel(
    override val type: Int = 0,
    override val id: Int,
    val title: String
) : DetailUiModel(id, type) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as DetailHeaderModel

        if (type != other.type) return false
        if (id != other.id) return false
        if (title != other.title) return false

        return true
    }

    override fun hashCode(): Int {
        var result = type
        result = 31 * result + id
        result = 31 * result + title.hashCode()
        return result
    }
}

data class DetailItemModel(
    override val type: Int = 1,
    override val id: Int,
    val item: String
) : DetailUiModel(id, type) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as DetailItemModel

        if (type != other.type) return false
        if (id != other.id) return false
        if (item != other.item) return false

        return true
    }

    override fun hashCode(): Int {
        var result = type
        result = 31 * result + id
        result = 31 * result + item.hashCode()
        return result
    }
}
