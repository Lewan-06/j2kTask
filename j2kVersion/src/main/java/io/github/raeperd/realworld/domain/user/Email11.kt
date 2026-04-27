package io.github.raeperd.realworld.domain.user

import javax.persistence.Column

@Embeddable
class Email {
    @Column(name = "email", nullable = false)
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
        val email: Email = o as Email
        return address.equals(email.address)
    }

    @Override
    fun hashCode(): Int {
        return Objects.hash(address)
    }
}
