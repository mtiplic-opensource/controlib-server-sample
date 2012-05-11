/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.epita.mti.plic.opensource.controlibserversample.observer;

import com.epita.mti.plic.opensource.controlibserversample.jarloader.JarClassLoader;
import com.epita.mti.plic.opensource.controlibutility.beans.CLJarFile;
import com.epita.mti.plic.opensource.controlibutility.serialization.CLSerializable;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Benoit "KIDdAe" Vasseur
 */
public class JarFileObserver implements Observer
{
  private JarClassLoader classLoader;

  @Override
  public void update(Observable o, Object arg)
  {
    if (((CLSerializable) arg).getType().equals("jarFile"))
    {
      String fileContent = ((CLJarFile) arg).getFile();
      String fileName = ((CLJarFile) arg).getFileName();
      try {
        FileWriter fWriter = new FileWriter(fileName);
        BufferedWriter bWriter = new BufferedWriter(fWriter);
        int len = fileContent.length();
        int off = 0;
        while (off < len)
        {
          if (len - off > 256)
          {
            bWriter.write(fileName, off, 256);
            off += 256;
          }
          else
          {
            bWriter.write(fileName, off, len - off);
            break;
          }
        }
        bWriter.close();
        fWriter.close();
       } catch (IOException ex) {
        Logger.getLogger(JarFileObserver.class.getName()).log(Level.SEVERE, null, ex);
      }

    }
  }

  public JarClassLoader getClassLoader() {
    return classLoader;
  }

  public void setClassLoader(JarClassLoader classLoader) {
    this.classLoader = classLoader;
  }

}
