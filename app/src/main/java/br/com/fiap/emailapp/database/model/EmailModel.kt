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
    override val id: Long = 0,
    override val sender: String,
    override val receiver: String,
    override val title: String,
    override val content: String,
    override val date: String,
    @ColumnInfo(name = "is_new")
    override val isNew: Boolean,
    @ColumnInfo(name = "initial_label")
    override val initialLabel: MutableList<EmailLabel>
) : IEmail{
    constructor(
        sender: String,
        receiver: String,
        title: String,
        content: String,
    ) : this(
        id = 0,
        sender = sender,
        receiver = receiver,
        title = title,
        content = content,
        date = getCurrentDate(),
        isNew = false,
        initialLabel = mutableListOf()
    )

    companion object {
        private fun getCurrentDate(): String {

            // Retorna a data atual no formato desejado
            return "2024-06-17" // Substitua com a lógica real para obter a data atual
        }
    }
}


