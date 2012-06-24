/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epita.mti.plic.opensource.controlibserversample.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

/**
 *
 * @author Julien "Roulyo" Fraisse
 */
public class PluginsView extends Frame
{

  private List list = new List();
  private Button deleteButton = new Button("Delete");

  public PluginsView()
  {
    super("Plugins");

    Dimension dim = getToolkit().getScreenSize();

    File dir = new File("plugins/");
    String[] children = dir.list();
    if (children != null)
    {
      for (int i = 0; i < children.length; i++)
      {
        list.add(children[i].replaceAll("\\.jar$", ""));
      }
    }

    add(new Label("Installed plugins:"));
    add(new Container().add(list));
    add(deleteButton);

    addWindowListener(new WindowAdapter()
    {

      @Override
      public void windowClosing(WindowEvent we)
      {
        dispose();
      }
    });
    
    deleteButton.addActionListener(new ActionListener()
    {

      @Override
      public void actionPerformed(ActionEvent e)
      {
        if (list.getSelectedIndex() != -1)
        {
          new File("plugins/" + list.getSelectedItem() + ".jar").delete();
          list.remove(list.getSelectedItem());
        }
      }
    });

    GridBagLayout gb = new GridBagLayout();
    setLayout(gb);

    GridBagConstraints gbcl = new GridBagConstraints();
    gbcl.fill = GridBagConstraints.BOTH;
    gbcl.gridheight = 8;
    gbcl.weightx = 1;
    gbcl.weighty = 1;
    gbcl.gridy = 1;

    GridBagConstraints gbcb = new GridBagConstraints();
    gbcb.gridy = 10;

    gb.setConstraints(list, gbcl);
    gb.setConstraints(deleteButton, gbcb);

    setSize(200, 200);
    setLocation((dim.width - getBounds().width) / 2, (dim.height - getBounds().height) / 2);
    setVisible(true);
  }
}
