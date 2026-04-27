import java.io.File
import javax.xml.parsers.DocumentBuilderFactory

data class TestSummary(val total: Int, val failedTestNames: List<String>) {
    val passed = total - failedTestNames.size
    val rate = if (total > 0) (passed.toDouble() / total * 100).toInt() else 0
}

fun main(args: Array<String>) {
    // If any arguments are passed to main, use those as paths
    // Otherwise use predefined
    val paths = if (args.size >= 3) {
        args
    } else {
        arrayOf(
            "javaVersion/build/test-results/test",
            "kotlinVersion/build/test-results/test",
            "j2kVersion/build/test-results/test"
        )
    }

    val labels = arrayOf("Java (Original)", "Kotlin (Official)", "J2K (Converted)")
    val results = paths.map { path ->
        val dir = File(path)
        if (dir.exists()) parseTestFolder(dir) else TestSummary(0, emptyList())
    }

    val (javaRes, kotlinRes, j2kRes) = results

    // Construct Markdown Content
    val reportContent = StringBuilder()
    reportContent.append("# J2K Conversion Evaluation Report\n\n")
    reportContent.append("### Conversion Quality Statistics\n\n")
    reportContent.append("| Metric | ${labels[0]} | ${labels[1]} | ${labels[2]} |\n")
    reportContent.append("| :--- | :--- | :--- | :--- |\n")
    reportContent.append("| Total Tests | ${javaRes.total} | ${kotlinRes.total} | ${j2kRes.total} |\n")
    reportContent.append("| Passed Tests | ${javaRes.passed} | ${kotlinRes.passed} | ${j2kRes.passed} |\n")
    reportContent.append("| Pass Rate | ${javaRes.rate}% | ${kotlinRes.rate}% | ${j2kRes.rate}% |\n\n")

    if (j2kRes.failedTestNames.isNotEmpty()) {
        reportContent.append("### J2K Failed Test Cases\n")
        reportContent.append("The following tests failed after the J2K conversion and require manual inspection:\n\n")
        j2kRes.failedTestNames.forEach { reportContent.append("- $it\n") }
    } else if (j2kRes.total > 0) {
        reportContent.append("### J2K Status\n✅ All converted tests passed successfully!\n")
    } else {
        reportContent.append("### J2K Status\n⚠️ No test results were found for the J2K version.\n")
    }

    // Write to report.md
    try {
        File("../report.md").writeText(reportContent.toString())
        println("Evaluation complete. Report generated at: report.md")
    } catch (e: Exception) {
        println("Error writing report file: ${e.message}")
    }
}

fun parseTestFolder(root: File): TestSummary {
    var totalTests = 0
    val failureList = mutableListOf<String>()

    root.walkTopDown()
        .filter { it.extension == "xml" && it.name.startsWith("TEST-") }
        .forEach { file ->
            try {
                val doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file)
                val suite = doc.getElementsByTagName("testsuite").item(0) ?: return@forEach

                totalTests += suite.attributes.getNamedItem("tests")?.nodeValue?.toInt() ?: 0

                val testCases = doc.getElementsByTagName("testcase")
                for (i in 0 until testCases.length) {
                    val testCase = testCases.item(i)
                    val nodes = testCase.childNodes
                    var failed = false

                    for (j in 0 until nodes.length) {
                        val nodeName = nodes.item(j).nodeName
                        if (nodeName == "failure" || nodeName == "error") {
                            failed = true
                            break
                        }
                    }

                    if (failed) {
                        val className = testCase.attributes.getNamedItem("classname")?.nodeValue ?: "Unknown"
                        val methodName = testCase.attributes.getNamedItem("name")?.nodeValue ?: "Unknown"
                        failureList.add("$className: $methodName")
                    }
                }
            } catch (e: Exception) { /* Skip invalid XML */ }
        }
    return TestSummary(totalTests, failureList)
}