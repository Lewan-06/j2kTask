package io.github.raeperd.realworld.domain.article.tag

import org.junit.jupiter.api.Test

internal class TagTest {
    @Test
    fun tag_has_no_args_protected_constructor() {
        class ChildTag : Tag()
        assertThat(ChildTag()).isInstanceOf(Tag::class.java)
    }

    @Test
    fun when_tag_toString_expect_return_value() {
        val tag: Tag = Tag()
        ReflectionTestUtils.setField(tag, "value", "some-tag")

        assertThat(tag).hasToString("some-tag")
    }

    @Test
    fun when_tag_has_different_value_expect_not_equal_and_hashCode() {
        val tag: Tag = Tag("some-vale")
        val tagWithDifferentValue: Tag = Tag("some-different-value")

        assertThat(tag)
            .isNotEqualTo(tagWithDifferentValue)
            .extracting(Tag::hashCode).isNotEqualTo(tagWithDifferentValue.hashCode())
    }

    @Test
    fun when_tag_has_same_value_expect_equal() {
        val tag: Tag = Tag("some-value")
        val tagWithSameValue: Tag = Tag("some-value")

        assertThat(tag)
            .isEqualTo(tagWithSameValue)
            .hasSameHashCodeAs(tagWithSameValue)
    }
}