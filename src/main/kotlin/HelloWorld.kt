import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.pdmodel.PDPage
import org.apache.pdfbox.pdmodel.PDPageContentStream
import org.apache.pdfbox.pdmodel.common.PDRectangle


object HelloWorld {
    @Throws(Exception::class)
    @JvmStatic
    fun main(args: Array<String>) {
        PDDocument().use { doc ->
            val page = PDPage(PDRectangle.A4)
            PDPageContentStream(doc, page).use { stream ->
                val type0Font = Util.loadFont(doc)
                stream.beginText()
                stream.setFont(type0Font, 24f)
                stream.newLineAtOffset(50f, 600f)
                stream.showText("Hello 東京!")
                stream.endText()
            }
            doc.addPage(page)
            doc.save("/tmp/HelloWorld.pdf")
        }
    }
}