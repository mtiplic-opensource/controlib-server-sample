package com.epita.mti.plic.opensource.controlibserversample.observer;

import com.epita.mti.plic.opensource.controlibutility.beans.CLPressure;
import com.epita.mti.plic.opensource.controlibutility.serialization.CLSerializable;
import java.awt.AWTException;
import java.awt.Robot;
import java.util.Observable;
import java.util.Observer;

/**
 *
 * @author Julien "Roulyo" Fraisse
 */
public class MouseObserver implements Observer
{

  private Robot robot;

  public MouseObserver() throws AWTException
  {
    this.robot = new Robot();
  }
  
  @Override
  public void update(Observable o, Object arg)
  {
    if (((CLSerializable) arg).getType().compareTo("pressure") == 0)
    {
      robot.mouseMove(((CLPressure) arg).getX(), ((CLPressure) arg).getY());
      System.out.println("x : " + ((CLPressure) arg).getX());
      System.out.println("y : " + ((CLPressure) arg).getY());
    }
  }
}
