package org.medianik.exchangerates

import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate

@Suppress("UsePropertyAccessSyntax")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OverallTest(
	@Autowired
	private val restTemplate: TestRestTemplate,
) {
	private val urlUSD = "${restTemplate.rootUri}/currency/USD";
	private val urlEUR = "${restTemplate.rootUri}/currency/EUR";
	private val urlJPY = "${restTemplate.rootUri}/currency/JPY";

	@Test
	fun `Test that given gif for USD is not null and has some data`() {
		assertThat(
			this.restTemplate.getForObject(urlUSD, ByteArray::class.java))
			.isNotNull()
			// 10_000 is guaranteed to be for one single gif, if it's false then something probably went wrong
			.hasSizeGreaterThan(10_000)
	}
	@Test
	fun `Test that given gif for EUR is not null and has some data`() {
		assertThat(
			this.restTemplate.getForObject(urlEUR, ByteArray::class.java))
			.isNotNull()
			// 10_000 is guaranteed to be for one single gif, if it's false then something probably went wrong
			.hasSizeGreaterThan(10_000)
	}
	@Test
	fun `Test that given gif for JPY is not null and has some data`() {
		assertThat(
			this.restTemplate.getForObject(urlJPY, ByteArray::class.java))
			.isNotNull()
			// 10_000 is guaranteed to be for one single gif, if it's false then something probably went wrong
			.hasSizeGreaterThan(10_000)
	}
}

class NoImplementationTest