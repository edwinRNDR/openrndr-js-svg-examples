import kotlinx.browser.document
import kotlinx.browser.window
import library.SVGDrawer
import org.openrndr.animatable.Animatable
import org.openrndr.animatable.easing.Easing
import org.openrndr.color.ColorRGBa
import org.openrndr.draw.LineCap
import org.openrndr.extra.noise.scatter
import org.openrndr.extra.noise.uniform
import org.openrndr.extra.shapes.hobbyCurve

import org.openrndr.shape.Circle
import org.w3c.dom.svg.SVGElement
import kotlin.math.cos

fun drawing03() {
    val g = document.getElementById("inner-svg") as SVGElement

    val c = Circle(400.0, 400.0, 200.0)
    val points = c.scatter(50.0)
    val hobby = hobbyCurve(points, closed = true)

    val drawer = SVGDrawer(g)

    fun draw(time: Double) {
        val seconds = time / 1000.0
        drawer.clear()
        drawer.fill = null
        drawer.strokeWeight = 24.0
        drawer.lineCap = LineCap.ROUND
        val start = cos(seconds) * 0.5 + 0.5
        val end = start + 0.2
        drawer.contour(hobby.sub(start, end))
        window.requestAnimationFrame(::draw)
    }
    draw(0.0)
}