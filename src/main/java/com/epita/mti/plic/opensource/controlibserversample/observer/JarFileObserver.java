package com.epita.mti.plic.opensource.controlibserversample.observer;

import com.epita.mti.plic.opensource.controlibserver.jarloader.JarClassLoader;
import com.epita.mti.plic.opensource.controlibutility.plugins.CLPlugin;
import com.epita.mti.plic.opensource.controlibutility.beans.CLJarFile;
import com.epita.mti.plic.opensource.controlibutility.serialization.CLSerializable;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.iharder.Base64;

/**
 *
 * @author Benoit "KIDdAe" Vasseur
 * This a the observer used to handle plugins reception.
 * The jar content is taken from the stream and put in a jar file in a local
 * directory.
 */
public class JarFileObserver implements CLPlugin
{
  private JarClassLoader classLoader;

  @Override
  public void update(Observable o, Object arg)
  {
    if (((CLSerializable) arg).getType().equals("jarFile"))
    {
      String fileContent = ((CLJarFile) arg).getFile();
      String fileName = ((CLJarFile) arg).getFileName();

      try
      {
        FileOutputStream fos = new FileOutputStream("plugins/" + fileName);
        BufferedOutputStream bos = new BufferedOutputStream(fos);

        bos.write(Base64.decode(fileContent));
        bos.flush();
        fos.close();
        try {
          classLoader.addPlugins("plugins/" + fileName);
        } catch (Exception ex) {
          Logger.getLogger(JarFileObserver.class.getName()).log(Level.SEVERE, null, ex);
        }
      }
      catch (IOException ex)
      {
        Logger.getLogger(JarFileObserver.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
  }

  public JarClassLoader getClassLoader()
  {
    return classLoader;
  }

  @Override
  public void setClassLoader(JarClassLoader classLoader)
  {
    this.classLoader = classLoader;
  }
}
