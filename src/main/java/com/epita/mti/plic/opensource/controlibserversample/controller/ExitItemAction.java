/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epita.mti.plic.opensource.controlibserversample.controller;

import com.epita.mti.plic.opensource.controlibserversample.Server;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 *
 * @author Julien "Roulyo" Fraisse
 */
public class ExitItemAction implements ActionListener
{

  @Override
  public void actionPerformed(ActionEvent e)
  {
    try
    {
      FileOutputStream file = new FileOutputStream("server.conf");
      ObjectOutputStream oos = new ObjectOutputStream(file);
      oos.writeObject(Server.getServerConfiguration());
      oos.flush();
      oos.close();
    }
    catch (IOException ex)
    {
      ex.printStackTrace();
    }
    System.exit(0);
  }
}
