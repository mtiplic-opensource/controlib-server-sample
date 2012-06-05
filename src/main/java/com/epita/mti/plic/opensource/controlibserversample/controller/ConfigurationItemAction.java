/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epita.mti.plic.opensource.controlibserversample.controller;

import com.epita.mti.plic.opensource.controlibserversample.view.ConfigurationView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Julien "Roulyo" Fraisse
 */
public class ConfigurationItemAction implements ActionListener
{

  ConfigurationView view = null;
  
  @Override
  public void actionPerformed(ActionEvent e)
  {
    view = new ConfigurationView();
  }
  
}
