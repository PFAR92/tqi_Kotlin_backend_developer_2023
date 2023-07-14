package com.tqi.kotlinbackenddeveloper2023

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestPropertySource


@SpringBootTest
@TestPropertySource(locations = ["classpath:test.properties"])
class KotlinBackendDeveloper2023ApplicationTests {


    @Test
    fun contextLoads() {
    }

}
