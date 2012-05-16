package com.epita.mti.plic.opensource.controlibserversample;

import com.epita.mti.plic.opensource.controlibserver.connection.ConnectionManager;
import com.epita.mti.plic.opensource.controlibserversample.jarloader.JarClassLoader;
import com.epita.mti.plic.opensource.controlibserversample.observer.JarFileObserver;
import com.epita.mti.plic.opensource.controlibserversample.observer.MouseObserver;
import com.epita.mti.plic.opensource.controlibserversample.observer.TrackpadObserver;
import com.epita.mti.plic.opensource.controlibserversample.view.ServerView;
import com.epita.mti.plic.opensource.controlibutility.serialization.ObjectReceiver;
import java.awt.AWTException;
import java.awt.Frame;
import java.awt.SystemTray;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Julien "Roulyo" Fraisse
 */
public class ServerSample implements CLServer
{

  public final static int PORT = 4200;
  private static ConnectionManager connectionManager = new ConnectionManager();
  private static ServerView serverView = new ServerView();
  private static Frame qrcodeView = null;
  private static JarClassLoader classLoader = new JarClassLoader();
  private static ObjectReceiver receiver;

  public static void main(String[] args)
  {
    try
    {
      final SystemTray tray = SystemTray.getSystemTray();
      connectionManager.openConnection(PORT);

      tray.add(serverView.getTrayIcon());

      while (true)
      {
        JarFileObserver jarFileObserver = new JarFileObserver();
        Socket ss = connectionManager.getServer().accept();

        closeQrcodeView();
        jarFileObserver.setClassLoader(classLoader);
        receiver = new ObjectReceiver(ss, jarFileObserver);
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
    if (qrcodeView != null)
    {
      qrcodeView.dispose();
      qrcodeView = null;
    }
  }

  @Override
  public void updatePlugins()
  {
    ArrayList<Class<?>> plugins = classLoader.getPlugins();
    for (Class<?> plugin : plugins)
    {
      try
      {
        Constructor<?> constructor = plugin.getConstructor();
        Observer observer = (Observer) constructor.newInstance();
        receiver.addObserver(observer);
      }
      catch (InstantiationException ex)
      {
        Logger.getLogger(ServerSample.class.getName()).log(Level.SEVERE, null, ex);
      }
      catch (IllegalAccessException ex)
      {
        Logger.getLogger(ServerSample.class.getName()).log(Level.SEVERE, null, ex);
      }
      catch (IllegalArgumentException ex)
      {
        Logger.getLogger(ServerSample.class.getName()).log(Level.SEVERE, null, ex);
      }
      catch (InvocationTargetException ex)
      {
        Logger.getLogger(ServerSample.class.getName()).log(Level.SEVERE, null, ex);
      }
      catch (NoSuchMethodException ex)
      {
        Logger.getLogger(ServerSample.class.getName()).log(Level.SEVERE, null, ex);
      }
      catch (SecurityException ex)
      {
        Logger.getLogger(ServerSample.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
    classLoader.getPlugins().clear();
  }
}
