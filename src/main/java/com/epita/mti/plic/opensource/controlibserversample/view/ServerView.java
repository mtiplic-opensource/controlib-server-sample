package com.epita.mti.plic.opensource.controlibserversample.view;

import com.epita.mti.plic.opensource.controlibserversample.controller.ConfigurationItemAction;
import com.epita.mti.plic.opensource.controlibserversample.controller.ExitItemAction;
import com.epita.mti.plic.opensource.controlibserversample.controller.PluginItemAction;
import com.epita.mti.plic.opensource.controlibserversample.controller.QRCodeItemAction;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Julien "Roulyo" Fraisse
 */
public class ServerView
{

  private TrayIcon trayIcon = null;
  private PopupMenu popUp = null;
  private MenuItem qrcodeItem = null;
  private MenuItem exitItem = null;
  private MenuItem configItem = null;
  private MenuItem pluginItem = null;

  public ServerView()
  {
    if (!SystemTray.isSupported())
    {
      System.out.println("SystemTray is not supported");
      return;
    }

    // Init materials
    popUp = new PopupMenu();
    try
    {
      trayIcon = new TrayIcon(ImageIO.read(new File("asset/controlib-logo.png")));
      trayIcon.setImageAutoSize(true);
    }
    catch (IOException ex)
    {
      trayIcon = new TrayIcon(new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB));
    }

    qrcodeItem = new MenuItem("Connect new device...");
    exitItem = new MenuItem("Exit");
    configItem = new MenuItem("Settings...");
    pluginItem = new MenuItem("Plugins...");

    // Add listeners
    qrcodeItem.addActionListener(new QRCodeItemAction());
    exitItem.addActionListener(new ExitItemAction());
    configItem.addActionListener(new ConfigurationItemAction());
    pluginItem.addActionListener(new PluginItemAction());

    // Add materials
    popUp.add(qrcodeItem);
    popUp.add(configItem);
    popUp.add(pluginItem);
    popUp.addSeparator();
    popUp.add(exitItem);
    
    
    trayIcon.setPopupMenu(popUp);
  }

  public TrayIcon getTrayIcon()
  {
    return trayIcon;
  }
}
