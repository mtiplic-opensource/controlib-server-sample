package com.epita.mti.plic.opensource.controlibserversample.view;

import com.epita.mti.plic.opensource.controlibserver.qrcode.QrcodeGenerator;
import com.epita.mti.plic.opensource.controlibserversample.Server;
import com.epita.mti.plic.opensource.controlibserversample.controller.SelectInterfaceAction;
import com.google.zxing.WriterException;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Julien "Roulyo" Fraisse
 */
public class QRCodeView extends Frame
{

  private Image img;
  private HashMap<String, HashMap<String, String>> interfaces;

  public QRCodeView() throws SocketException, WriterException
  {
    super("QRCode");

    Choice selector = new Choice();
    Dimension dim = getToolkit().getScreenSize();
    MediaTracker mt = new MediaTracker(this);

    interfaces = Server.getConnectionManager().getInterfaces();
    for (Map.Entry<String, HashMap<String, String>> entry : interfaces.entrySet())
    {
      String string = entry.getKey();
      selector.add(string);
    }

    
    selector.select(Server.getServerConfiguration().getDefaultInterface());
    String ip = Server.getConnectionManager().getInterfaces().get(selector.getSelectedItem()).get("IPV4");
    img = QrcodeGenerator.generateQrcode(ip, Server.getServerConfiguration().getMainPort(),
                                             Server.getServerConfiguration().getOutputPort(),
                                             Server.getServerConfiguration().getInputPort(), 200, 200);
    mt.addImage(img, 0);

    setLayout(null);
    setSize(220, 245);
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

    selector.setSize(200, 20);
    selector.setLocation(10, 215);
    selector.addItemListener(new SelectInterfaceAction(this));
    
    add(selector);
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

  public void setQrcode(String ninterface) throws WriterException, SocketException
  {
    String ip = Server.getConnectionManager().getInterfaces().get(ninterface).get("IPV4");
    img = QrcodeGenerator.generateQrcode(ip, Server.getServerConfiguration().getMainPort(),
                                             Server.getServerConfiguration().getOutputPort(),
                                             Server.getServerConfiguration().getInputPort(), 200, 200);
    update(getGraphics());
  }
}
