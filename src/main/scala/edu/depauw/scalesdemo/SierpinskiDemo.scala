package edu.depauw.scalesdemo

import edu.depauw.scales.ScalesApp
import edu.depauw.scales.graphics._

object SierpinskiDemo extends ScalesApp {
  val sierp = sierpinski(Square(1000) -* Colors.Blue)(10)
  
  // sierp.writePNG("/tmp/Sierpinski.png")
  
  // create panel to draw a Sierpinski Triangle on
  val panel = GraphicPanel(sierp)
  
  // add panel to ScalesApp window
  addPanel(panel)
  
  /* ================================= METHODS ================================== */ 
  
  /**
   * Freezes a given graphic, creates 3 copies of it, each transformed differently,
   * then composites the result. This is a single iteration of a Sierpinski transform.
   */
  def sierpinskify(g: Graphic): Graphic = {
    val f = Freeze(g) -* 0.5
    Translate(f.bounds.getCenterX, 0, f) -^ (f ||| f)
  }
  
  /**
   * Creates a stream where each subsequent item is a sierpinski-transformed version
   * of the previous item.
   * @param g Graphic provides the seed graphic to iterate over
   * @return Stream[Graphic] each item in the stream is an iteration of the sierpinski transform
   */
  def sierpinski(g: Graphic): Stream[Graphic] = Stream.iterate(g)(sierpinskify)
}