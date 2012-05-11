 package com.epita.mti.plic.opensource.controlibserversample.jarloader;

import java.io.File;
import java.io.FilenameFilter;

public class JarFilter implements FilenameFilter
{
	private String extension; 

	public JarFilter()
	{
		this.extension = "jar";
	}
	  
	@Override
	public boolean accept(File dir, String filename)
	{
		if (extension != null && filename.endsWith('.' + extension))
			{
				return true;
		    }
		return false;
		
	}

}
