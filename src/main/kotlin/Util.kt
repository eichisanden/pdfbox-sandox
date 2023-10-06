import org.apache.fontbox.ttf.TTFParser
import org.apache.pdfbox.io.RandomAccessReadBufferedFile
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.pdmodel.font.PDType0Font
import java.io.File
import java.io.IOException

object Util {
    @Throws(IOException::class)
    fun loadFont(doc: PDDocument): PDType0Font {
        val classLoader = Thread.currentThread().getContextClassLoader()
        val url = classLoader.getResource("font/NotoSansJP-Regular.ttf") ?: error("font not found")
        return RandomAccessReadBufferedFile(File(url.path)).use { buff ->
            TTFParser().parse(buff).use { ttf ->
                PDType0Font.load(doc, ttf, true)
            }
        }
    }
}