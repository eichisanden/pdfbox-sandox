import org.apache.fontbox.ttf.TTFParser
import org.apache.pdfbox.io.RandomAccessReadBufferedFile
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.pdmodel.font.PDType0Font
import java.io.File
import java.io.IOException

object Util {
    private const val INCH_TO_MM = 25.4f // 1in = 25.4mm
    fun point2mm(point: Float): Float {
        return inch2mm(point2Inch(point))
    }

    fun point2Inch(point: Float): Float {
        return point * (1 / 72f) // 0.013888888888888888f
    }

    fun inch2mm(inch: Float): Float {
        return inch * INCH_TO_MM
    }

    fun mm2pt (mm: Float): Float {
        return mm * (72f / INCH_TO_MM)
    }

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