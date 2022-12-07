import kotlinx.browser.document
import kotlinx.browser.window
import library.SVGDrawer
import org.openrndr.color.ColorRGBa
import org.openrndr.extra.noise.scatter

import org.openrndr.shape.Circle
import org.w3c.dom.svg.SVGElement
import kotlin.math.cos

fun drawing01() {
    val g = document.getElementById("inner-svg") as SVGElement
    val drawer = SVGDrawer(g)

    fun draw(time: Double) {
        val seconds = time / 1000.0
        drawer.clear()
        drawer.fill = ColorRGBa.RED
        val c = Circle(100.0 + cos(seconds)*100.0, 300.0, 200.0).contour
        drawer.contour(c)
        val scatters = c.shape.scatter(10.0, distanceToEdge = 20.0)

        drawer.fill = ColorRGBa.WHITE
        for (s in scatters) {
            val c = Circle(s, 5.0).contour
            drawer.contour(c)
        }
        window.requestAnimationFrame(::draw)
    }
    draw(0.0)
}