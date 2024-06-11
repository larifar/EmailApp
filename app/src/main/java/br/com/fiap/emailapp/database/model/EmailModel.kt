package br.com.fiap.emailapp.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

enum class EmailLabel {
    PRIMARY,
    SOCIAL,
    PROMOTIONS,
    UPDATES,
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
    val isNew: Boolean
    val initialLabel: MutableList<EmailLabel>
}

@Entity(tableName = "tbl_emails")
data class Email(
    @PrimaryKey(autoGenerate = true)
    override val id: Long,
    override val sender: String,
    override val receiver: String,
    override val title: String,
    override val content: String,
    override val date: String,
    @ColumnInfo(name = "is_new")
    override val isNew: Boolean,
    @ColumnInfo(name = "initial_label")
    override val initialLabel: MutableList<EmailLabel>
) : IEmail

