package edu.depauw.scalesdemo

import edu.depauw.scales.ScalesApp
import edu.depauw.scales.graphics._
import edu.depauw.scales.reactive._

import java.awt.geom.AffineTransform
import java.awt.event.MouseEvent

object Telephone extends ScalesApp(600, 600) {
  val buttons = ((button("1") -|| button("2")) -^
                 (button("3") -|| button("4")) -^
                 button("Flash", 200)
                ) -||
                (button("Police") -^ button("Taxi") -^ button("Doctor"))
  
  def button(name: String, width: Int = 100, height: Int = 100): Graphic =
    (Name(name, Rectangle(width, height).pad(20).center) -& Text(name, FontSize(28)).center).topLeft
  
  val buttonNames = buttons.names
  
  type State = String
  
  val transitions: Map[(State, String), State] = Map(
      ("start", "1") -> "one", ("start", "2") -> "two", ("start", "3") -> "three", ("start", "4") -> "four",
      ("start", "Flash") -> "talk", ("start", "Police") -> "police", ("start", "Taxi") -> "taxi", ("start", "Doctor") -> "doctor",
      
      ("one", "1") -> "circle", ("one", "2") -> "two", ("one", "3") -> "three", ("one", "4") -> "four",
      ("one", "Flash") -> "talk", ("one", "Police") -> "police", ("one", "Taxi") -> "taxi", ("one", "Doctor") -> "doctor",
      
      ("two", "1") -> "one", ("two", "2") -> "heart", ("two", "3") -> "three", ("two", "4") -> "four",
      ("two", "Flash") -> "talk", ("two", "Police") -> "police", ("two", "Taxi") -> "taxi", ("two", "Doctor") -> "doctor",
      
      ("three", "1") -> "one", ("three", "2") -> "two", ("three", "3") -> "triangle", ("three", "4") -> "four",
      ("three", "Flash") -> "talk", ("three", "Police") -> "police", ("three", "Taxi") -> "taxi", ("three", "Doctor") -> "doctor",
      
      ("four", "1") -> "one", ("four", "2") -> "two", ("four", "3") -> "three", ("four", "4") -> "square",
      ("four", "Flash") -> "talk", ("four", "Police") -> "police", ("four", "Taxi") -> "taxi", ("four", "Doctor") -> "doctor",
      
      ("circle", "1") -> "one", ("circle", "2") -> "heart", ("circle", "3") -> "triangle", ("circle", "4") -> "square",
      ("circle", "Flash") -> "talk", ("circle", "Police") -> "police", ("circle", "Taxi") -> "taxi", ("circle", "Doctor") -> "doctor",
      
      ("heart", "1") -> "circle", ("heart", "2") -> "two", ("heart", "3") -> "triangle", ("heart", "4") -> "square",
      ("heart", "Flash") -> "talk", ("heart", "Police") -> "police", ("heart", "Taxi") -> "taxi", ("heart", "Doctor") -> "doctor",
      
      ("triangle", "1") -> "circle", ("triangle", "2") -> "heart", ("triangle", "3") -> "three", ("triangle", "4") -> "square",
      ("triangle", "Flash") -> "talk", ("triangle", "Police") -> "police", ("triangle", "Taxi") -> "taxi", ("triangle", "Doctor") -> "doctor",
      
      ("square", "1") -> "circle", ("square", "2") -> "heart", ("square", "3") -> "triangle", ("square", "4") -> "four",
      ("square", "Flash") -> "talk", ("square", "Police") -> "police", ("square", "Taxi") -> "taxi", ("square", "Doctor") -> "doctor",
      
      ("police", "1") -> "one", ("police", "2") -> "two", ("police", "3") -> "three", ("police", "4") -> "four",
      ("police", "Flash") -> "talk", ("police", "Police") -> "siren", ("police", "Taxi") -> "taxi", ("police", "Doctor") -> "doctor",
      
      ("taxi", "1") -> "one", ("taxi", "2") -> "two", ("taxi", "3") -> "three", ("taxi", "4") -> "four",
      ("taxi", "Flash") -> "talk", ("taxi", "Police") -> "police", ("taxi", "Taxi") -> "honk", ("taxi", "Doctor") -> "doctor",
      
      ("doctor", "1") -> "one", ("doctor", "2") -> "two", ("doctor", "3") -> "three", ("doctor", "4") -> "four",
      ("doctor", "Flash") -> "talk", ("doctor", "Police") -> "police", ("doctor", "Taxi") -> "taxi", ("doctor", "Doctor") -> "ambulance",
      
      ("siren", "1") -> "circle", ("siren", "2") -> "heart", ("siren", "3") -> "triangle", ("siren", "4") -> "square",
      ("siren", "Flash") -> "talk", ("siren", "Police") -> "police", ("siren", "Taxi") -> "taxi", ("siren", "Doctor") -> "doctor",
      
      ("honk", "1") -> "circle", ("honk", "2") -> "heart", ("honk", "3") -> "triangle", ("honk", "4") -> "square",
      ("honk", "Flash") -> "talk", ("honk", "Police") -> "police", ("honk", "Taxi") -> "taxi", ("honk", "Doctor") -> "doctor",
      
      ("ambulance", "1") -> "circle", ("ambulance", "2") -> "heart", ("ambulance", "3") -> "triangle", ("ambulance", "4") -> "square",
      ("ambulance", "Flash") -> "talk", ("ambulance", "Police") -> "police", ("ambulance", "Taxi") -> "taxi", ("ambulance", "Doctor") -> "doctor",
      
      ("talk", "1") -> "one", ("talk", "2") -> "two", ("talk", "3") -> "three", ("talk", "4") -> "four",
      ("talk", "Flash") -> "hold", ("talk", "Police") -> "police", ("talk", "Taxi") -> "taxi", ("talk", "Doctor") -> "doctor",
      
      ("hold", "1") -> "circle", ("hold", "2") -> "heart", ("hold", "3") -> "triangle", ("hold", "4") -> "square",
      ("hold", "Flash") -> "play", ("hold", "Police") -> "police", ("hold", "Taxi") -> "taxi", ("hold", "Doctor") -> "doctor",
      
      ("play", "1") -> "one", ("play", "2") -> "two", ("play", "3") -> "three", ("play", "4") -> "four",
      ("play", "Flash") -> "hello", ("play", "Police") -> "police", ("play", "Taxi") -> "taxi", ("play", "Doctor") -> "doctor",
      
      ("hello", "1") -> "circle", ("hello", "2") -> "heart", ("hello", "3") -> "triangle", ("hello", "4") -> "square",
      ("hello", "Flash") -> "talk", ("hello", "Police") -> "police", ("hello", "Taxi") -> "taxi", ("hello", "Doctor") -> "doctor"
    )
  
  val panel = ReactivePanel(0, new AffineTransform, "start", render, onMouse)
  
  addPanel(panel)
  
  def render: State => Graphic = {
    case state => buttons -^ Text(state, FontSize(28)).pad(40)
  }
  
  def onMouse: (MouseEvent, State) => State = {
    case (e, state) if e.getID == MouseEvent.MOUSE_CLICKED =>
      buttonNames.find(buttons.withName(_).head.shape.contains(e.getX, e.getY)) match {
        case Some(name) => transitions((state, name))
        
        case None => state // not on a button; ignore
      }
    
    case (_, state) => state // not a mouse click; ignore
  }
}