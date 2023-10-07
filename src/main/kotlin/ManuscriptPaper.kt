import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.pdmodel.PDPage
import org.apache.pdfbox.pdmodel.PDPageContentStream
import org.apache.pdfbox.pdmodel.common.PDRectangle
import java.awt.Color

object ManuscriptPaper {
    @Throws(Exception::class)
    @JvmStatic
    fun main(args: Array<String>) {
        PDDocument().use { doc ->
            doc.addPage(makePage(doc))
            doc.save("/tmp/ManuscriptPaper.pdf")
        }
    }

    private fun makePage(doc: PDDocument): PDPage {
        val page = PDPage(PDRectangle.A4) // A4: 210mm * 297mm(595.2756pt * 841.8898pt)
        page.rotation = 90
        val rect = page.cropBox
        val width = rect.width.toInt()
        val height = rect.height.toInt()
        PDPageContentStream(doc, page).use { stream ->
            stream.setStrokingColor(Color.LIGHT_GRAY)
            stream.setLineWidth(0.5f)

            stream.addRect(0f, 0f, width.toFloat(), height.toFloat())
            stream.stroke()

            val outerMargin = 5f
            stream.addRect(outerMargin, outerMargin, width.toFloat() - (outerMargin * 2), height.toFloat() - (outerMargin * 2))
            stream.stroke()

            val innerMargin = 1f
            val cellSize = (width.toFloat() - (outerMargin * 2) - (innerMargin * 2) /* add margin */) / 20

            val lineMargin = 10f
            for (x in 0..<20) {
                for (y in 0..<10) {
                    stream.addRect((outerMargin + innerMargin) + (x * cellSize), 15f + (y * cellSize) + (y * lineMargin), cellSize, cellSize)
                    stream.stroke()
                }
            }

            val centerY = 15f + (10 * cellSize) + (10 * 10)
            stream.addRect(6f, centerY, cellSize * 20, cellSize)
            stream.stroke()

            for (x in 0..<20) {
                for (y in 0..<10) {
                    stream.addRect(6f + (x * cellSize), centerY + cellSize + lineMargin + (y * cellSize) + (y * lineMargin), cellSize, cellSize)
                    stream.stroke()
                }
            }
        }

        return page
    }
}