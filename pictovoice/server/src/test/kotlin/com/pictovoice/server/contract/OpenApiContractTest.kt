package com.pictovoice.server.contract

import io.swagger.v3.parser.OpenAPIV3Parser
import java.io.File
import kotlin.test.Test
import kotlin.test.assertNotNull

class OpenApiContractTest {
    @Test
    fun openApiSpecParses() {
        val specRoot =
            File(System.getProperty("user.dir"))
                .resolve("../../specs/002-kmp-pictovoice-aac/contracts/openapi.yaml")
                .canonicalFile
        val parsed = OpenAPIV3Parser().read(specRoot.path)
        assertNotNull(parsed)
        assertNotNull(parsed.openapi)
    }
}
