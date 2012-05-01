package com.epita.mti.plic.opensource.controlibserversample;

import com.epita.mti.plic.opensource.controlibserver.connection.ConnectionManager;
import com.epita.mti.plic.opensource.controlibserversample.observer.MouseObserver;
import com.epita.mti.plic.opensource.controlibserversample.observer.TrackpadObserver;
import com.epita.mti.plic.opensource.controlibserversample.view.ServerView;
import com.epita.mti.plic.opensource.controlibutility.serialization.ObjectReceiver;
import java.awt.AWTException;
import java.awt.Frame;
import java.awt.SystemTray;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observer;
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
        ArrayList<Observer> observers = new ArrayList<Observer>();
        MouseObserver mouseObserver = new MouseObserver();
        TrackpadObserver trackpasObserver = new TrackpadObserver();
        observers.add(trackpasObserver);
        observers.add(mouseObserver);
        ObjectReceiver receiver = new ObjectReceiver(ss, observers);
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

  public static String getIPV4(String networkInterface) throws SocketException
  {
    HashMap<String, String> ninterface = connectionManager.getInterfaces().get(networkInterface);
    if (ninterface == null)
    {
      System.err.println("The interface " + networkInterface + " was not found.");
      return null;
    }
    return ninterface.get("IPV4");
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
