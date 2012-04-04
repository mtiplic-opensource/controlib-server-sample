package com.epita.mti.plic.opensource.controlibserversample.observer;

import com.epita.mti.plic.opensource.controlibutility.beans.CLPressure;
import com.epita.mti.plic.opensource.controlibutility.serialization.CLSerializable;
import java.util.Observable;
import java.util.Observer;

/**
 *
 * @author Julien "Roulyo" Fraisse
 */
public class DemoObserver implements Observer
{

  @Override
  public void update(Observable o, Object arg)
  {
    if (((CLSerializable) arg).getType().compareTo("pressure") == 0)
    {
      System.out.println("x : " + ((CLPressure) arg).getX());
      System.out.println("y : " + ((CLPressure) arg).getY());
    }
  }
}
