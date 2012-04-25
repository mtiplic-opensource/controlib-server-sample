package com.epita.mti.plic.opensource.controlibserversample.view;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 *
 * @author Julien "Roulyo" Fraisse
 */
public class QRCodeView extends Frame
{

  Image img;

  public QRCodeView(Image qrcode)
  {
    super("QRCode");
    MediaTracker mt = new MediaTracker(this);
    Dimension dim = getToolkit().getScreenSize();

    img = qrcode;
    mt.addImage(img, 0);
    setSize(220, 220);
    setLocation((dim.width - getBounds().width) / 2, (dim.height - getBounds().height) / 2);
    setVisible(true);
    addWindowListener(new WindowAdapter()
    {

      @Override
      public void windowClosing(WindowEvent we)
      {
        dispose();
      }
    });
  }

  @Override
  public void update(Graphics g)
  {
    paint(g);
  }

  @Override
  public void paint(Graphics g)
  {
    if (img != null)
    {
      g.drawImage(img, 10, 10, this);
    }
    else
    {
      g.clearRect(0, 0, getSize().width, getSize().height);
    }
  }
}
