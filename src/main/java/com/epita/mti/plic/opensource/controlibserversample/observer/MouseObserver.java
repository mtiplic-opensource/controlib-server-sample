package com.epita.mti.plic.opensource.controlibserversample.observer;

import com.epita.mti.plic.opensource.controlibutility.beans.CLButtonPressure;
import com.epita.mti.plic.opensource.controlibutility.serialization.CLSerializable;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Julien "Roulyo" Fraisse
 */
public class MouseObserver implements Observer
{
  final static private int START = 0;
  final static private int FULLSCREEN = 1;
  final static private int POINTER = 2;
  final static private int CLICK = 3;
  final static private int PREV = 4;
  final static private int NEXT = 5;

  private Robot robot;

  public MouseObserver() throws AWTException
  {
    this.robot = new Robot();
  }

  @Override
  public void update(Observable o, Object arg)
  {
    if (((CLSerializable) arg).getType().equals("button-pressure"))
    {
      switch (((CLButtonPressure) arg).getButtonId())
      {
        case START:
      try {
        Runtime.getRuntime().exec("evince ~/slides.pdf");
      } catch (IOException ex) {
        Logger.getLogger(MouseObserver.class.getName()).log(Level.SEVERE, null, ex);
      }
          break;
        case FULLSCREEN:
          robot.keyPress(KeyEvent.VK_F5);
          break;
        case POINTER:
          break;
        case NEXT:
          robot.keyPress(KeyEvent.VK_RIGHT);
          break;
        case PREV:
          robot.keyPress(KeyEvent.VK_LEFT);
          break;
        case CLICK:
          robot.mousePress(InputEvent.BUTTON1_MASK);
          robot.mouseRelease(InputEvent.BUTTON1_MASK);
          break;
      }
    }
  }
}
