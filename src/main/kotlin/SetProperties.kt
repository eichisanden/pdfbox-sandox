import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.pdmodel.PDPage
import org.apache.pdfbox.pdmodel.common.PDRectangle
import java.util.*

object SetProperties {
    @Throws(Exception::class)
    @JvmStatic
    fun main(args: Array<String>) {
        PDDocument().use { doc ->
            doc.addPage(makePage(doc))
            doc.save("/tmp/SetProperties.pdf")
        }
    }

    private fun makePage(doc: PDDocument): PDPage {
        val page = PDPage(PDRectangle.A4)
        val info = doc.documentInformation
        info.author = "Author"
        info.title = "Title"
        info.subject = "Subject"
        info.creationDate = Calendar.getInstance()
        info.keywords = "Keywords"
        info.setCustomMetadataValue("key1", "val1")
        info.setCustomMetadataValue("key2", "val2")
        info.creator = "Creator"
        info.producer = "Producer"
        return page
    }
}