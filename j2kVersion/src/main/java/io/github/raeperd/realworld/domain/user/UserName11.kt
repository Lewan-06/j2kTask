package io.github.raeperd.realworld.domain.user

import javax.persistence.Column

@Embeddable
class UserName {
    @Column(name = "name", nullable = false)
    private var name: String? = null

    constructor(name: String?) {
        this.name = name
    }

    protected constructor()

    @Override
    fun toString(): String? {
        return name
    }

    @Override
    fun equals(o: Object?): Boolean {
        if (this === o) return true
        if (o == null || getClass() !== o.getClass()) return false
        val userName: UserName = o as UserName
        return name.equals(userName.name)
    }

    @Override
    fun hashCode(): Int {
        return Objects.hash(name)
    }
}
