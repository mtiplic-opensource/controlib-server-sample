package com.epita.mti.plic.opensource.controlibserversample;

import com.epita.mti.plic.opensource.controlibserversample.observer.DemoObserver;
import com.epita.mti.plic.opensource.controlibutility.serialization.ObjectReceiver;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 *
 */
public class App
{

  public static void main(String[] args) throws Exception
  {
    ServerSocket socket = new ServerSocket(4200);
    Socket ss = socket.accept();

    DemoObserver observer = new DemoObserver();
    ObjectReceiver receiver = new ObjectReceiver(ss.getInputStream(), observer);
    receiver.run();
  }
}
