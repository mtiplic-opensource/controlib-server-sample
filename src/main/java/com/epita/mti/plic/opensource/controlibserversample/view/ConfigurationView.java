/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epita.mti.plic.opensource.controlibserversample.view;

import com.epita.mti.plic.opensource.controlibserversample.Server;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 *
 * @author Julien "Roulyo" Fraisse
 */
public class ConfigurationView extends Frame
{

  private TextField csField = new TextField(Server.getServerConfiguration().getOutputPort().toString());
  private TextField scField = new TextField(Server.getServerConfiguration().getInputPort().toString());
  private Button cancelButton = new Button("Cancel");
  private Button okButton = new Button("Ok");
  
  public ConfigurationView()
  {
    super("Settings");

    Dimension dim = getToolkit().getScreenSize();
    
    add(new Label("Server to Client port:"));
    add(csField);
    add(new Label("Client to Server port:"));
    add(scField);
    add(new Label("Values must be between 1000 and 65535."));
    add(new Label("Changes will occur at restart."));
    add(cancelButton);
    add(okButton);

    addWindowListener(new WindowAdapter()
    {

      @Override
      public void windowClosing(WindowEvent we)
      {
        dispose();
      }
    });

    cancelButton.addActionListener(new ActionListener()
    {

      @Override
      public void actionPerformed(ActionEvent e)
      {
        dispose();
      }
    });
    
    okButton.addActionListener(new ActionListener()
    {

      @Override
      public void actionPerformed(ActionEvent e)
      {
        Server.getServerConfiguration().setInputPort(Integer.parseInt(scField.getText()));
        Server.getServerConfiguration().setOutputPort(Integer.parseInt(csField.getText()));
        dispose();
      }
    });

    GridLayout gl = new GridLayout(4, 2);
    gl.setHgap(5);
    gl.setVgap(5);
    setLayout(gl);
    setSize(520, 180);
    setLocation((dim.width - getBounds().width) / 2, (dim.height - getBounds().height) / 2);
    setVisible(true);
  }
}
