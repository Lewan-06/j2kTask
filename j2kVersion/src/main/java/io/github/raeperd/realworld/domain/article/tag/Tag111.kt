package io.github.raeperd.realworld.domain.article.tag

import java.util.Objects

@Table(name = "tags")
@Entity
class Tag {
    @GeneratedValue(strategy = IDENTITY)
    @Id
    private val id: Long? = null

    @Column(name = "value", unique = true, nullable = false)
    private var value: String? = null

    constructor(value: String?) {
        this.value = value
    }

    protected constructor()

    @Override
    fun toString(): String? {
        return value
    }

    @Override
    fun equals(o: Object?): Boolean {
        if (this === o) return true
        if (o == null || getClass() !== o.getClass()) return false
        val tag: Tag = o as Tag
        return value.equals(tag.value)
    }

    @Override
    fun hashCode(): Int {
        return Objects.hash(value)
    }
}
