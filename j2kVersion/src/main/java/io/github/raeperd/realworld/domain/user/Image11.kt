package io.github.raeperd.realworld.domain.user

import javax.persistence.Column

@Embeddable
class Image {
    @Column(name = "image")
    private var address: String? = null

    constructor(address: String?) {
        this.address = address
    }

    protected constructor()

    @Override
    fun toString(): String? {
        return address
    }

    @Override
    fun equals(o: Object?): Boolean {
        if (this === o) return true
        if (o == null || getClass() !== o.getClass()) return false
        val image: Image = o as Image
        return address.equals(image.address)
    }

    @Override
    fun hashCode(): Int {
        return Objects.hash(address)
    }
}
