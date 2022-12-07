package library

import kotlinx.browser.document
import org.openrndr.color.ColorRGBa
import org.openrndr.draw.LineCap

import org.openrndr.shape.ShapeContour
import org.w3c.dom.svg.SVGCircleElement
import org.w3c.dom.svg.SVGElement
import org.w3c.dom.svg.SVGPathElement

private val svgns = "http://www.w3.org/2000/svg"

fun ColorRGBa.toWeb(): String {
    return "rgba(${(r * 255).toInt()},${(g * 255).toInt()},${(b * 255).toInt()}, $alpha)"
}

fun ShapeContour.toSVG(): String {
    val sb = StringBuilder()
    if (this.empty) {
        return ""
    } else {
        val first = segments.first().start
        sb.append("M ${first.x} ${first.y}")
        segments.forEach {
            when (it.control.size) {
                0 -> sb.append("L ${it.end.x} ${it.end.y}")
                1 -> sb.append("Q ${it.control[0].x} ${it.control[0].y} ${it.end.x} ${it.end.y}")
                2 -> sb.append("C ${it.control[0].x} ${it.control[0].y} ${it.control[1].x} ${it.control[1].y} ${it.end.x} ${it.end.y}")
            }
        }
        if (closed) {
            sb.append("Z")
        }
        return sb.toString()
    }
}

class SVGDrawer(val svg: SVGElement) {
    var fill: ColorRGBa? = ColorRGBa.WHITE
    var stroke: ColorRGBa? = ColorRGBa.BLACK
    var lineCap = LineCap.BUTT
    var strokeWeight = 1.0

    fun clear() {
        svg.innerHTML = ""
    }

    fun contour(contour: ShapeContour): SVGPathElement {
        val pathElement = document.createElementNS(svgns, "path") as SVGPathElement
        val path = contour.toSVG()
        pathElement.setAttribute("d", path)
        setDrawAttributes(pathElement)
        svg.appendChild(pathElement)
        return pathElement
    }

    fun contours(contours: List<ShapeContour>): List<SVGPathElement> {
        return contours.map { contour(it) }
    }

    fun circle(x: Double, y: Double, radius: Double): SVGCircleElement {
        val circleElement = document.createElementNS(svgns, "circle") as SVGCircleElement
        circleElement.setAttribute("cx", x.toString())
        circleElement.setAttribute("cy", y.toString())
        circleElement.setAttribute("r", radius.toString())
        setDrawAttributes(circleElement)
        svg.appendChild(circleElement)
        return circleElement
    }


    private fun setDrawAttributes(element: SVGElement) {
        val lfill = fill
        if (lfill != null) {
            element.setAttribute("fill", lfill.toWeb())
        } else {
            element.setAttribute("fill", "none")
        }

        val lstroke = stroke
        if (lstroke != null) {
            element.setAttribute("stroke", lstroke.toWeb())
        } else {
            element.setAttribute("stroke", "none")
        }

        val svgLineCap = when (lineCap) {
            LineCap.ROUND -> "round"
            LineCap.BUTT -> "butt"
            LineCap.SQUARE -> "square"
        }
        element.setAttribute("stroke-linecap", svgLineCap)
        element.setAttribute("stroke-width", strokeWeight.toString())
    }


}