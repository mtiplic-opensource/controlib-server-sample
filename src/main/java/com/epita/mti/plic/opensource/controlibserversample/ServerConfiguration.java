/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epita.mti.plic.opensource.controlibserversample;

import java.io.Serializable;

/**
 *
 * @author Julien "Roulyo" Fraisse
 */
public class ServerConfiguration implements Serializable
{
  private static final long serialVersionUID = 42L;
  
  private int inputPort = 4200;
  private int outputPort = 4201;
  private String defaultInterface;

  public String getDefaultInterface()
  {
    return defaultInterface;
  }

  public void setDefaultInterface(String defaultInterface)
  {
    this.defaultInterface = defaultInterface;
  }

  public int getInputPort()
  {
    return inputPort;
  }

  public boolean setInputPort(int inputPort)
  {
    if (inputPort < 1000)
      return false;
    
    this.inputPort = inputPort;
    return true;
  }

  public int getOutputPort()
  {
    return outputPort;
  }

  public boolean setOutputPort(int outputPort)
  {
    if (outputPort < 1000)
      return false;
    
    this.outputPort = outputPort;
    return true;
  }
  
}
