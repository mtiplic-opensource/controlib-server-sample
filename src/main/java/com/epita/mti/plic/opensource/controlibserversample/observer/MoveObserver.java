/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epita.mti.plic.opensource.controlibserversample.observer;

import com.epita.mti.plic.opensource.controlibutility.Serialization.CLSerializable;
import java.awt.AWTException;
import java.awt.Robot;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Julien "Roulyo" Fraisse
 */
public class MoveObserver implements Observer
{

  public Robot robot = null;
  
  public MoveObserver()
  {
    try
    {
      robot = new Robot();
    }
    catch (AWTException ex)
    {
      Logger.getLogger(MoveObserver.class.getName()).log(Level.SEVERE, "Cannot instantiate the robot", ex);
    }
  }
  
  @Override
  public void update(Observable o, Object arg)
  {
    System.out.println("Done that");
    System.out.println(((CLSerializable) arg).getType());
  }
  
}
