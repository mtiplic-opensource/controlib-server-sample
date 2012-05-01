package com.epita.mti.plic.opensource.controlibserversample.controller;

import com.epita.mti.plic.opensource.controlibserversample.ServerSample;
import com.epita.mti.plic.opensource.controlibserversample.view.QRCodeView;
import com.google.zxing.WriterException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Julien "Roulyo" Fraisse
 */
public class QRCodeItemAction implements ActionListener
{

  private final int WIDTH = 200;
  private final int HEIGHT = 200;

  @Override
  public void actionPerformed(ActionEvent e)
  {
    try
    {
      ServerSample.setQrcodeView(new QRCodeView());
    }
    catch (WriterException ex)
    {
      Logger.getLogger(ServerSample.class.getName()).log(Level.SEVERE, null, ex);
    }
    catch (SocketException ex)
    {
      Logger.getLogger(ServerSample.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
}
