package org.tendiwa.canvas.awt

import org.tendiwa.canvas.algorithms.geometry.drawArrow
import org.tendiwa.canvas.algorithms.geometry.drawBillboard
import org.tendiwa.derasterization.point
import org.tendiwa.geometry.circles.Circle
import org.tendiwa.geometry.constructors.spanHorizontalSegment
import org.tendiwa.geometry.points.Point
import org.tendiwa.geometry.segments.Segment
import org.tendiwa.grid.rectangles.GridRectangle
import org.tendiwa.grid.rectangles.corner
import org.tendiwa.plane.directions.OrdinalDirection
import java.awt.Color

fun main(args: Array<String>) {
    AwtCanvas(scale = 3, viewport = GridRectangle(-10, 21, 200, 200))
        .apply { drawShit() }
    AwtCanvas(scale = 2).apply { drawShit() }
    AwtCanvas(scale = 1).apply { drawShit() }
    AwtCanvas(scale = 101, viewport = GridRectangle(0, 0, 4, 2))
        .apply { drawShit() }
}

private fun AwtCanvas.drawShit() {
    draw(GridRectangle(0, 0, 1, 1), Color.blue)
    draw(Segment(Point(0.0, 0.0), Point(1.0, 1.0)), Color.red)
    draw(Circle(Point(1.0, 0.0), 0.5), Color.red)
    draw(GridRectangle(11, 11, 30, 50), Color.blue)
    draw(10, 10, Color.red)
    draw(20, 10, Color.red)
    draw(Point(10.0, 15.0).spanHorizontalSegment(100.0), Color.black)
    draw(GridRectangle(40, 100, 30, 50), Color.blue)
    drawBillboard(
        Point(77.0, 53.0),
        "byoggibidTl",
        Color.yellow
    )
    val rec = GridRectangle(300, 100, 50, 50)
    draw(rec, Color.black)
    drawText("hello", rec.corner(OrdinalDirection.SW).point, Color.white)
    draw(Circle(Point(100.0, 100.0), radius = 5.0), Color.red)
    drawArrow(Segment(Point(80.0, 90.0), Point(144.0, 159.0)), Color.black, 3.0)
}
