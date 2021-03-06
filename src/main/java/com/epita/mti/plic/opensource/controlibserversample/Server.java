package com.epita.mti.plic.opensource.controlibserversample;

import com.epita.mti.plic.opensource.controlibserver.connection.ConnectionManager;
import com.epita.mti.plic.opensource.controlibserver.jarloader.JarClassLoader;
import com.epita.mti.plic.opensource.controlibserver.server.CLServer;
import com.epita.mti.plic.opensource.controlibserversample.observer.JarFileObserver;
import com.epita.mti.plic.opensource.controlibserversample.view.ServerView;
import com.epita.mti.plic.opensource.controlibutility.plugins.CLObserver;
import com.epita.mti.plic.opensource.controlibutility.plugins.CLObserverSend;
import com.epita.mti.plic.opensource.controlibutility.serialization.CLSerializable;
import com.epita.mti.plic.opensource.controlibutility.serialization.ObjectReceiver;
import com.epita.mti.plic.opensource.controlibutility.serialization.ObjectSender;
import java.awt.AWTException;
import java.awt.Frame;
import java.awt.SystemTray;
import java.io.*;
import java.lang.reflect.Constructor;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Julien "Roulyo" Fraisse This the server used for the demonstration.
 */
public class Server implements CLServer
{

  private static ConnectionManager connectionManager = new ConnectionManager();
  private static ServerView serverView = new ServerView();
  private static Frame qrcodeView = null;
  private JarClassLoader classLoader = new JarClassLoader(this);
  private static ArrayList<ObjectReceiver> receivers = new ArrayList<>();
  private static ArrayList<ObjectSender> senders = new ArrayList<>();
  private static ServerConfiguration conf;

  public static ServerConfiguration getServerConfiguration()
  {
    return conf;
  }

  /**
   * This method update the plugins available for the server
   */
  @Override
  public void updatePlugins(int index)
  {
    ArrayList<Class<?>> plugins = classLoader.getPlugins();

    receivers.get(index).clearPlugins();
    for (Class<?> plugin : plugins)
    {
      boolean isSerializable = false;
      Class<?> superClass = plugin.getSuperclass();

      while (superClass != null)
      {
        if (superClass == CLSerializable.class)
        {
          isSerializable = true;
          break;
        }
        else
        {
          superClass = superClass.getSuperclass();
        }
      }
      if (isSerializable)
      {
        Constructor<?> constructor;
        try
        {
          constructor = plugin.getConstructor();
          CLSerializable cls = (CLSerializable) constructor.newInstance();
          if (!receivers.get(index).getBeansMap().containsKey(cls.getType()))
          {
            receivers.get(index).getBeansMap().put(cls.getType(), plugin);
          }

        }
        catch (Exception ex)
        {
          Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
      }
      try
      {
        Class[] interfaces = plugin.getInterfaces();
        Observer observer = null;
        for (Class c : interfaces)
        {
          if (c == Observer.class)
          {
            Constructor<?> constructor = plugin.getConstructor();
            observer = (Observer) constructor.newInstance();
            break;
          }
          if (c == CLObserver.class)
          {
            Constructor<?> constructor = plugin.getConstructor();
            observer = (CLObserver) constructor.newInstance();
            break;
          }
          else if (c == CLObserverSend.class)
          {
            Constructor<?> constructor = plugin.getConstructor();
            observer = (CLObserverSend) constructor.newInstance();
            ((CLObserverSend) observer).setObjectSender(senders.get(index));
            break;
          }
        }
        if (observer != null)
        {
          receivers.get(index).addObserver(observer);
        }
      }
      catch (Exception ex)
      {
        Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
    classLoader.getPlugins().clear();
  }

  public void loadConf()
  {
    try
    {
      FileInputStream file = new FileInputStream("server.conf");
      ObjectInputStream ois = new ObjectInputStream(file);

      conf = (ServerConfiguration) ois.readObject();
    }
    catch (java.io.IOException e)
    {
      conf = new ServerConfiguration();
    }
    catch (ClassNotFoundException e)
    {
      e.printStackTrace();
    }
  }

  /**
   * This methods launches the server, shows the Qr code and opens the socket.
   */
  public void start()
  {
    try
    {
      final SystemTray tray = SystemTray.getSystemTray();


      loadConf();
      connectionManager.openPluginConnection(conf.getInputPort(), conf.getOutputPort());

      tray.add(serverView.getTrayIcon());

      while (true)
      {
        Socket inputSocket = connectionManager.getInputSocket().accept();
        Socket outputSocket = connectionManager.getOutputSocket().accept();
        System.out.println("Connected");

        JarFileObserver jarFileObserver = new JarFileObserver();
        jarFileObserver.setClassLoader(classLoader);
        jarFileObserver.setIndex(receivers.size());

        ObjectReceiver or = new ObjectReceiver(inputSocket, jarFileObserver);
        receivers.add(or);
        senders.add(new ObjectSender(outputSocket.getOutputStream()));
        new Thread(or).start();
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
}
