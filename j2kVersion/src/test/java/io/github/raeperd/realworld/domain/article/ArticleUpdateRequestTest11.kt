package io.github.raeperd.realworld.domain.article

import org.junit.jupiter.api.Test

@ExtendWith(MockitoExtension::class)
internal class ArticleUpdateRequestTest {
    @Test
    fun when_articleUpdateRequest_created_without_field_expect_get_return_empty() {
        val requestWithoutFields: Unit /* TODO: class org.jetbrains.kotlin.nj2k.types.JKJavaNullPrimitiveType */? =
            builder().build()

        assertThat(requestWithoutFields.getTitleToUpdate()).isEmpty()
        assertThat(requestWithoutFields.getDescriptionToUpdate()).isEmpty()
        assertThat(requestWithoutFields.getBodyToUpdate()).isEmpty()
    }

    @Test
    fun when_articleUpdateRequest_created_with_all_fields_expect_all_fields(@Mock title: ArticleTitle?) {
        val requestWithAllFields: Unit /* TODO: class org.jetbrains.kotlin.nj2k.types.JKJavaNullPrimitiveType */? =
            builder()
                .titleToUpdate(title)
                .descriptionToUpdate("descriptionToUpdate")
                .bodyToUpdate("bodyToUpdate")
                .build()

        assertThat(requestWithAllFields).hasNoNullFieldsOrProperties()
        assertThat(requestWithAllFields.getTitleToUpdate()).contains(title)
        assertThat(requestWithAllFields.getDescriptionToUpdate()).contains("descriptionToUpdate")
        assertThat(requestWithAllFields.getBodyToUpdate()).contains("bodyToUpdate")
    }
}