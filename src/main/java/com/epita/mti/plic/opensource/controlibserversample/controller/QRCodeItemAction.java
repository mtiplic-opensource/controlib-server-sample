package com.epita.mti.plic.opensource.controlibserversample.controller;

import com.epita.mti.plic.opensource.controlibserver.qrcode.QrcodeGenerator;
import com.epita.mti.plic.opensource.controlibserversample.ServerSample;
import com.epita.mti.plic.opensource.controlibserversample.view.QRCodeView;
import com.google.zxing.WriterException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
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
      String ipv4 = ServerSample.getIPV4("wlan0");
      int port = ServerSample.PORT;

      if (ipv4 != null)
      {
        BufferedImage qrcode = QrcodeGenerator.generateQrcode(ipv4, port, WIDTH, HEIGHT);
        ServerSample.setQrcodeView(new QRCodeView(qrcode));
      }
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
