package com.epita.mti.plic.opensource.controlibserversample;

import com.epita.mti.plic.opensource.controlibserver.connection.ConnectionManager;
import com.epita.mti.plic.opensource.controlibserversample.observer.MouseObserver;
import com.epita.mti.plic.opensource.controlibserversample.view.ServerView;
import com.epita.mti.plic.opensource.controlibutility.serialization.ObjectReceiver;
import java.awt.AWTException;
import java.awt.Frame;
import java.awt.SystemTray;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Julien "Roulyo" Fraisse
 */
public class ServerSample
{

  public final static int PORT = 4200;
  private static ConnectionManager connectionManager = new ConnectionManager();
  private static ServerView serverView = new ServerView(); 
  private static Frame qrcodeView = null;
          
  public static void main(String[] args)
  {
    try
    {
      final SystemTray tray = SystemTray.getSystemTray();
      connectionManager.openConnection(PORT);

      tray.add(serverView.getTrayIcon());

      while (true)
      {
        Socket ss = connectionManager.getServer().accept();
        closeQrcodeView();
        MouseObserver observer = new MouseObserver();
        ObjectReceiver receiver = new ObjectReceiver(ss, observer);
        new Thread(receiver).start();
      }
    }
    catch (IOException ex)
    {
      Logger.getLogger(ServerSample.class.getName()).log(Level.SEVERE, null, ex);
    }
    catch (AWTException ex)
    {
      Logger.getLogger(ServerSample.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
  
  public static ConnectionManager getConnectionManager()
  {
    return connectionManager;
  }
  
  public static void setQrcodeView(Frame f)
  {
    qrcodeView = f;
  }
  
  public static void closeQrcodeView()
  {
    qrcodeView.dispose();
    qrcodeView = null;
  }
}
