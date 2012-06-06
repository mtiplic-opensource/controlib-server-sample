package com.epita.mti.plic.opensource.controlibserversample;

import com.epita.mti.plic.opensource.controlibserver.connection.ConnectionManager;
import com.epita.mti.plic.opensource.controlibserver.jarloader.JarClassLoader;
import com.epita.mti.plic.opensource.controlibserver.server.CLServer;
import com.epita.mti.plic.opensource.controlibserversample.observer.JarFileObserver;
import com.epita.mti.plic.opensource.controlibserversample.view.ServerView;
import com.epita.mti.plic.opensource.controlibutility.serialization.ObjectReceiver;
import com.epita.mti.plic.opensource.controlibutility.serialization.ObjectSender;
import java.awt.AWTException;
import java.awt.Frame;
import java.awt.SystemTray;
import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
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
  private static ObjectReceiver receiver;
  private static ObjectSender sender;
  private static ServerConfiguration conf;
  private ArrayList<Observer> currentObservers = new ArrayList<Observer>();

  public static ServerConfiguration getServerConfiguration()
  {
    return conf;
  }

  /**
   * This method update the plugins available for the server
   */
  @Override
  public void updatePlugins()
  {
    ArrayList<Class<?>> plugins = classLoader.getPlugins();

    for (Observer o : currentObservers)
    {
      receiver.deleteObserver(o);
    }
    currentObservers.clear();
    for (Class<?> plugin : plugins)
    {
      try
      {
        Constructor<?> constructor = plugin.getConstructor();
        Observer observer = (Observer) constructor.newInstance();
        currentObservers.add(observer);
        receiver.addObserver(observer);
      }
      catch (InstantiationException ex)
      {
        Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
      }
      catch (IllegalAccessException ex)
      {
        Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
      }
      catch (IllegalArgumentException ex)
      {
        Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
      }
      catch (InvocationTargetException ex)
      {
        Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
      }
      catch (NoSuchMethodException ex)
      {
        Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
      }
      catch (SecurityException ex)
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
      JarFileObserver jarFileObserver = new JarFileObserver();

      loadConf();
      connectionManager.openConnection(conf.getMainPort());
      jarFileObserver.setClassLoader(classLoader);

      tray.add(serverView.getTrayIcon());

      Socket mainSocket = connectionManager.getMainSocket().accept();
      closeQrcodeView();

      InputStreamReader isr = new InputStreamReader(mainSocket.getInputStream());
      BufferedReader br = new BufferedReader(isr);

      while (true)
      {
        String message = br.readLine();
        if (message.equals("Pomme"))
        {
          OutputStreamWriter osw = new OutputStreamWriter(mainSocket.getOutputStream());
          osw.write("Cerise");
          Socket inputSocket = connectionManager.getInputSocket().accept();
          Socket outputSocket = connectionManager.getOutputSocket().accept();

          receiver = new ObjectReceiver(inputSocket, jarFileObserver);
          sender = new ObjectSender(outputSocket.getOutputStream());
          new Thread(receiver).start();
        }
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
