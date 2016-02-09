package org.tendiwa.canvas.awt

import org.tendiwa.canvas.algorithms.geometry.drawArrow
import org.tendiwa.canvas.algorithms.geometry.drawBillboard
import org.tendiwa.derasterization.toPoint
import org.tendiwa.plane.directions.OrdinalDirection.*
import org.tendiwa.plane.geometry.circles.Circle
import org.tendiwa.plane.geometry.points.Point
import org.tendiwa.plane.geometry.points.spanHorizontalSegment
import org.tendiwa.plane.geometry.segments.Segment
import org.tendiwa.plane.grid.rectangles.GridRectangle
import org.tendiwa.plane.grid.rectangles.corner
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
    drawGridRectangle(GridRectangle(0, 0, 1, 1), Color.green)
    drawSegment(Segment(Point(0.0, 0.0), Point(1.0, 1.0)), Color.black)
    drawCircle(Circle(Point(1.0, 0.0), 0.5), Color.lightGray)
    drawGridRectangle(GridRectangle(11, 11, 30, 50), Color.blue)
    drawCircle(10, 10, Color.red)
    drawCircle(20, 10, Color.red)
    drawSegment(Point(10.0, 15.0).spanHorizontalSegment(100.0), Color.black)
    drawGridRectangle(GridRectangle(40, 100, 30, 50), Color.blue)
    drawBillboard(
        Point(70.0, 50.0),
        "byoggibidTl",
        Color.black,
        Color.red
    )
    val rec = GridRectangle(300, 100, 50, 50)
    drawGridRectangle(rec, Color.black)
    drawText("hello", rec.corner(SW).toPoint(), Color.white)
    drawCircle(Circle(Point(100.0, 100.0), radius = 5.0), Color.red)
    drawArrow(Segment(Point(80.0, 90.0), Point(144.0, 159.0)), Color.black, 3.0)
}
