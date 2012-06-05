/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epita.mti.plic.opensource.controlibserversample.controller;

import com.epita.mti.plic.opensource.controlibserversample.view.PluginsView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Julien "Roulyo" Fraisse
 */
public class PluginItemAction implements ActionListener
{
  private PluginsView view = null;
  
  @Override
  public void actionPerformed(ActionEvent e)
  {
    view = new PluginsView();
  }
  
}
