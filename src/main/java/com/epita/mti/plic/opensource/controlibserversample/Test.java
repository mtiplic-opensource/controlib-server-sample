package com.epita.mti.plic.opensource.controlibserversample;

import com.epita.mti.plic.opensource.controlibutility.Serialization.CLSerializable;
import java.io.*;

public class Test
{
  static class Lol extends CLSerializable
  {
    public Lol(int n)
    {
      type = n;
    }
  }
  
  public static void main(String argv[])
  {

    Lol lol = new Lol(42);

    try
    {

      FileOutputStream fichier = new FileOutputStream("/tmp/personne.ser");

      ObjectOutputStream oos = new ObjectOutputStream(fichier);

      oos.writeObject(lol);

      oos.flush();

      oos.close();

    }
    catch (java.io.IOException e)
    {

      e.printStackTrace();

    }
  }
}