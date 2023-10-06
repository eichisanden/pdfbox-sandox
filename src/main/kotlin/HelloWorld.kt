import org.apache.fontbox.ttf.TTFParser
import org.apache.pdfbox.io.RandomAccessReadBufferedFile
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.pdmodel.PDPage
import org.apache.pdfbox.pdmodel.PDPageContentStream
import org.apache.pdfbox.pdmodel.common.PDRectangle
import org.apache.pdfbox.pdmodel.font.PDType0Font
import java.io.File
import java.io.IOException


object HelloWorld {
    @Throws(Exception::class)
    @JvmStatic
    fun main(args: Array<String>) {
        PDDocument().use { doc ->
            val page = PDPage(PDRectangle.A4)
            PDPageContentStream(doc, page).use { stream ->
                val type0Font = loadFont(doc)
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

    @Throws(IOException::class)
    private fun loadFont(doc: PDDocument): PDType0Font {
        val classLoader = Thread.currentThread().getContextClassLoader()
        val url = classLoader.getResource("font/NotoSansJP-Regular.ttf") ?: error("font not found")
        return RandomAccessReadBufferedFile(File(url.path)).use {buff ->
            TTFParser().parse(buff).use {ttf ->
                PDType0Font.load(doc, ttf, true)
            }
        }
    }
}