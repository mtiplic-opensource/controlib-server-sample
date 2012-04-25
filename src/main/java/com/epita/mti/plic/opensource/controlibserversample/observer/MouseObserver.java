package com.epita.mti.plic.opensource.controlibserversample.observer;

import com.epita.mti.plic.opensource.controlibutility.beans.CLButtonPressure;
import com.epita.mti.plic.opensource.controlibutility.serialization.CLSerializable;
import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.util.Observable;
import java.util.Observer;

/**
 *
 * @author Julien "Roulyo" Fraisse
 */
public class MouseObserver implements Observer
{
  final static private int UP = 0;
  final static private int RIGHT = 1;
  final static private int DOWN = 2;
  final static private int LEFT = 3;
  final static private int CLICK = 4;
  
  private Robot robot;
  private int mouseX;
  private int mouseY;
  
  public MouseObserver() throws AWTException
  {
    this.robot = new Robot();
  }
  
  @Override
  public void update(Observable o, Object arg)
  {
    if (((CLSerializable) arg).getType().equals("button-pressure"))
    {
      mouseX = MouseInfo.getPointerInfo().getLocation().x;
      mouseY = MouseInfo.getPointerInfo().getLocation().y;
      
      switch (((CLButtonPressure) arg).getButtonId())
      {
        case UP:
          robot.mouseMove(mouseX, mouseY - 10);
          break;
        case RIGHT:
          robot.mouseMove(mouseX + 10, mouseY);
          break;
        case DOWN:
          robot.mouseMove(mouseX, mouseY + 10);
          break;
        case LEFT:
          robot.mouseMove(mouseX - 10, mouseY);
          break;
        case CLICK:
          robot.mousePress(InputEvent.BUTTON1_MASK);
          robot.mouseRelease(InputEvent.BUTTON1_MASK);
          break;
      }
    }
  }
}
