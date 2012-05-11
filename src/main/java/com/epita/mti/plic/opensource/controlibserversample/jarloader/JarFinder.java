package com.epita.mti.plic.opensource.controlibserversample.jarloader;

import java.io.File;

public class JarFinder
{
	public JarFinder()
	{
	}

	public File[] listFiles(String url)
	{
		File dir = new File(url);
		JarFilter filter = new JarFilter();
		return dir.listFiles(filter);
	}
}
