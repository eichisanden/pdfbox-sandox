import Util.pathFromResource
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.pdmodel.PDPage
import org.apache.pdfbox.pdmodel.PDPageContentStream
import org.apache.pdfbox.pdmodel.common.PDRectangle
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject

object DrawImage {
    @Throws(Exception::class)
    @JvmStatic
    fun main(args: Array<String>) {
        PDDocument().use { doc ->
            doc.addPage(makePage(doc))
            doc.save("/tmp/DrawImage.pdf")
        }
    }

    private fun makePage(doc: PDDocument): PDPage {
        val page = PDPage(PDRectangle.A4)
        PDPageContentStream(doc, page).use { stream ->
            val image = PDImageXObject.createFromFile(pathFromResource("image/adpIMG_1808.jpg"), doc)
            val imageWidth = image.width
            val imageHeight = image.height
            val pageWidth = page.cropBox.width
            val pageHeight = page.cropBox.height
            stream.drawImage(image,
                (pageWidth - imageWidth) / 2,
                (pageHeight - imageHeight) / 2,
                image.width.toFloat(),
                image.height.toFloat())
        }
        return page
    }
}