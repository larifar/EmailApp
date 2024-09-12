package br.com.fiap.emailapp.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

enum class EmailLabel {
    PRIMARY,
    SOCIAL,
    PROMOTIONS,
    FORUMS,
    FAVORITE
}
interface IEmail{
    val id: Long
    val sender: String
    val receiver: String
    val title: String
    val content: String
    val date: String
    val isArchived: Boolean
    val isNew: Boolean
    val isFavorite: Boolean
    val initialLabel: MutableList<EmailLabel>
}

@Entity(tableName = "tbl_emails")
data class Email(
    @PrimaryKey(autoGenerate = true)
    override val id: Long = 0,
    override val sender: String,
    override val receiver: String,
    override val title: String,
    override val content: String,
    override val date: String,
    @ColumnInfo(name = "is_new")
    override val isNew: Boolean,
    @ColumnInfo(name = "is_archived")
    override var isArchived: Boolean = false,
    @ColumnInfo(name = "is_favorite")
    override var isFavorite: Boolean = false,
    @ColumnInfo(name = "initial_label")
    override val initialLabel: MutableList<EmailLabel>
) : IEmail{
    constructor(
        sender: String,
        receiver: String,
        title: String,
        content: String,
        date: LocalDateTime
    ) : this(
        id = 0,
        sender = sender,
        receiver = receiver,
        title = title,
        content = content,
        date = getDate(date),
        isNew = true,
        initialLabel = mutableListOf()
    )

    companion object {
        private fun getDate(date: LocalDateTime): String {
            val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")
            return date.format(formatter)
        }
    }
}


