package io.github.raeperd.realworld.domain.article

import javax.persistence.Column

@Embeddable
class ArticleTitle {
    @Column(nullable = false)
    private var title: String? = null

    @Column(nullable = false)
    private var slug: String? = null

    private constructor(title: String?, slug: String?) {
        this.title = title
        this.slug = slug
    }

    protected constructor()

    fun getSlug(): String? {
        return slug
    }

    fun getTitle(): String? {
        return title
    }

    @Override
    fun equals(o: Object?): Boolean {
        if (this === o) return true
        if (o == null || getClass() !== o.getClass()) return false
        val that: ArticleTitle = o as ArticleTitle
        return slug.equals(that.slug)
    }

    @Override
    fun hashCode(): Int {
        return Objects.hash(slug)
    }

    companion object {
        fun of(title: String): ArticleTitle {
            return ArticleTitle(title, slugFromTitle(title))
        }

        private fun slugFromTitle(title: String): String? {
            return title.toLowerCase()
                .replaceAll("\\$,'\"|\\s|\\.|\\?", "-")
                .replaceAll("-{2,}", "-")
                .replaceAll("(^-)|(-$)", "")
        }
    }
}
