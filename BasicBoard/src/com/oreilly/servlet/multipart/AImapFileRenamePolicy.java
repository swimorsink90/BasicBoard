package com.oreilly.servlet.multipart;

import java.io.*;
import java.util.Date;
import java.text.SimpleDateFormat;


public class AImapFileRenamePolicy implements FileRenamePolicy{
	
	public File rename(File f) {
		long currentTime = System.currentTimeMillis();
		SimpleDateFormat simDf = new SimpleDateFormat("yyyyMMddHHmmss");
		int randomNumber = (int)(Math.random()*100000);
		
		String uniqueFileName = "" + randomNumber + simDf.format(new Date(currentTime));
		
		String name = f.getName();
		String body = null;
		String ext = null;
		
		int dot = name.lastIndexOf(".");
		if(dot != -1) {
			body = name.substring(0,dot);
			ext = name.substring(dot);
		}else {
			body = name;
			ext = "";
		}
		
		String tempName = uniqueFileName + ext;
		f = new File(f.getParent(), tempName);
		if(createNewFile(f)) {
			return f;
		}
		
		int count = 0;
		while(!createNewFile(f) && count<9999) {
			count++;
			String newName = uniqueFileName + "_" + count + ext;
			f = new File(f.getParent(), newName);
		}
		
		return f;
	}
	
	private boolean createNewFile(File f) {
		try {
			return f.createNewFile();
		}catch(IOException ignored) {
			return false;
		}
	}

}
