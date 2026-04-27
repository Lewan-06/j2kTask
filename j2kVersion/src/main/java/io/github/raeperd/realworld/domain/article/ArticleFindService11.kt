package io.github.raeperd.realworld.domain.article

import java.util.Optional

interface ArticleFindService {
    fun getArticleBySlug(slug: String?): Optional<Article?>?
}
