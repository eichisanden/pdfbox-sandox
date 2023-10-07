import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.pdmodel.PDPage
import org.apache.pdfbox.pdmodel.PDPageContentStream
import org.apache.pdfbox.pdmodel.common.PDRectangle
import java.awt.Color

object GraphPaper {
    @Throws(Exception::class)
    @JvmStatic
    fun main(args: Array<String>) {
        PDDocument().use { doc ->
            // 10pt * 10pt
            doc.addPage(makePage(doc, 10))
            // 10mm * 10mm
            doc.addPage(makePage(doc, Util.mm2pt(10f).toInt()))
            doc.save("/tmp/GraphPaper.pdf")
        }
    }

    private fun makePage(doc: PDDocument, pt: Int): PDPage {
        val page = PDPage(PDRectangle.A4) // A4: 210mm * 297mm(595.2756pt * 841.8898pt)
        val rect = page.cropBox
        val width = rect.width.toInt()
        val height = rect.height.toInt()
        PDPageContentStream(doc, page).use { stream ->
            stream.setStrokingColor(Color.MAGENTA)

            for (x in 0..width step pt) {
                stream.moveTo(x.toFloat(), 0f)
                stream.lineTo(x.toFloat(), height.toFloat())
                stream.setLineWidth(if (x % (pt * 10) == 0) 1.5f else 0.5f)
                stream.stroke()
            }

            for (y in 0..height step pt) {
                stream.moveTo(0f, y.toFloat())
                stream.lineTo(width.toFloat(), y.toFloat())
                stream.setLineWidth(if (y % (pt * 10) == 0) 1.5f else 0.5f)
                stream.stroke()
            }
        }
        return page
    }
}