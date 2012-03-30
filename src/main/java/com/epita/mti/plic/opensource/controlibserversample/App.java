package com.epita.mti.plic.opensource.controlibserversample;

import com.epita.mti.plic.opensource.controlibserversample.observer.MoveObserver;
import com.epita.mti.plic.opensource.controlibutility.Serialization.ObjectReceiver;
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

    MoveObserver observer = new MoveObserver();
    ObjectReceiver receiver = new ObjectReceiver(ss.getInputStream(), observer);
    receiver.run();
  }
  
}
