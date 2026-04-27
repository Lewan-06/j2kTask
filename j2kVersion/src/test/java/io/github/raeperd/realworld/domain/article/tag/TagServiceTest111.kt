package io.github.raeperd.realworld.domain.article.tag

import org.junit.jupiter.api.BeforeEach

@ExtendWith(MockitoExtension::class)
internal class TagServiceTest {
    private var tagService: TagService? = null

    @Mock
    private val tagRepository: TagRepository? = null

    @BeforeEach
    fun initializeService() {
        this.tagService = TagService(tagRepository)
    }

    @Test
    fun when_findAll_expect_repository_findAll_called() {
        tagService.findAll()

        then(tagRepository).should(times(1)).findAll()
    }

    @Test
    fun given_tags_reloadAllTagsIfAlreadyPresent_then_findFirstByValue() {
        tagService.reloadAllTagsIfAlreadyPresent(singleton(Tag("tag-to-search")))

        then(tagRepository).should(times(1)).findFirstByValue("tag-to-search")
    }

    @Test
    fun when_repository_return_empty_expect_return_same_tags_expect_reloadAllTagsIfAlreadyPresent_return_same() {
        val tagNotExists: Tag = Tag("tag-not-exits")
        `when`(tagRepository.findFirstByValue(valueOf(tagNotExists))).thenReturn(empty())

        val tagsReloaded: Unit /* TODO: class org.jetbrains.kotlin.nj2k.types.JKJavaNullPrimitiveType */? =
            tagService.reloadAllTagsIfAlreadyPresent(singleton(tagNotExists))

        assertThat(tagsReloaded).contains(tagNotExists)
    }

    @Test
    fun when_repository_find_already_exists_tags_expect_reloadAllTagsIfAlreadyPresent_return_from_repository(@Mock tagAlreadyExists: Tag?) {
        `when`(tagRepository.findFirstByValue("tag-already-exists")).thenReturn(of(tagAlreadyExists))

        val tagsReloaded: Unit /* TODO: class org.jetbrains.kotlin.nj2k.types.JKJavaNullPrimitiveType */? =
            tagService.reloadAllTagsIfAlreadyPresent(singleton(Tag("tag-already-exists")))

        assertThat(tagsReloaded).contains(tagAlreadyExists)
    }
}