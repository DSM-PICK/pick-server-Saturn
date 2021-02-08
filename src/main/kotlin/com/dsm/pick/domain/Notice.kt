package com.dsm.pick.domain

import com.dsm.pick.domain.attribute.Category
import com.dsm.pick.domain.converter.CategoryConverter
import java.sql.Timestamp
import javax.persistence.*

@Entity
@Table(name = "notice")
class Notice(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notice_id")
    var id: Int? = null,

    @Column(name = "content")
    val content: String,

    @ManyToOne
    @JoinColumn(name = "admin_id", referencedColumnName = "id")
    val admin: Admin,

    @Column(name = "category")
    @Convert(converter = CategoryConverter::class)
    val category: Category,

    @Column(name = "created_at")
    val date: Timestamp,
) {

    override fun toString(): String {
        return "Notice(id=$id, content='$content', admin=$admin, category=$category, date=$date)"
    }
}