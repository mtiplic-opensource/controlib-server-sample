/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epita.mti.plic.opensource.controlibserversample.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Julien "Roulyo" Fraisse
 */
public class ExitItemAction implements ActionListener
{

  @Override
  public void actionPerformed(ActionEvent e)
  {
    System.exit(0);
  }
}
