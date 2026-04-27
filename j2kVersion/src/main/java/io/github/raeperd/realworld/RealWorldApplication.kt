package io.github.raeperd.realworld

import org.springframework.boot.SpringApplication

@SpringBootApplication
object RealWorldApplication {
    fun main(args: Array<String?>?) {
        SpringApplication.run(RealWorldApplication::class.java, args)
    }
}
