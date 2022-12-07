import kotlinx.browser.document
import kotlinx.browser.window
import library.SVGDrawer
import org.openrndr.animatable.Animatable
import org.openrndr.animatable.easing.Easing
import org.openrndr.color.ColorRGBa
import org.openrndr.extra.noise.scatter
import org.openrndr.extra.noise.uniform

import org.openrndr.shape.Circle
import org.w3c.dom.svg.SVGElement
import kotlin.math.cos

fun drawing02() {
    val g = document.getElementById("inner-svg") as SVGElement
    val drawer = SVGDrawer(g)

    class BouncyBall: Animatable() {
        var x = 100.0
        var y = 100.0
        var radius = 40.0
    }

    val bb = BouncyBall()

    fun draw(time: Double) {
        println(time)
        bb.updateAnimation()
        if (!bb.hasAnimations()) {
            bb.animate(bb::x,  Double.uniform(0.0, 400.0), 1000, Easing.QuadInOut)
            bb.animate(bb::y,  Double.uniform(0.0, 400.0), 1000, Easing.QuadInOut)
            bb.animate(bb::radius,  Double.uniform(20.0, 100.0), 1000, Easing.QuadInOut)
        }
        drawer.fill = ColorRGBa.RED
        drawer.clear()
        drawer.circle(bb.x, bb.y, bb.radius)
        window.requestAnimationFrame(::draw)
    }
    draw(0.0)
}