package org.ldemetrios.graphics

import java.awt.Color
import java.awt.Graphics

class GridCanvas : ContinuousCanvas(
    centerX = .0,
    centerY = .0,
    scale = 1.0,
    inverseY = true,
    autoRepaint = true,
    zoomType = ZoomType.ZoomToPoint,
    zoomFactor = 1.1,
    dragType = DragType.Drag
) {
    override fun setUp(g: Graphics) {
        g.color = Color.BLACK
        g.fillRect(0, 0, width, height)
        super.setUp(g)
    }

    override fun paint(g: ContinuousGraphics) {
        g.color = Color.WHITE
        for (i in -3..3) for (j in -3..3) {
            g.drawRect(i * 10.0, j * 10.0, 10.0, 10.0)
        }
    }
}

fun main() {
    createCanvasWindow(GridCanvas(), "Grid").run {
        isVisible = true
        maximize()
    }

}
