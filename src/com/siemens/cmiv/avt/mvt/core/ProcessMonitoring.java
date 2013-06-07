/*
Copyright (c) 2010, Siemens Corporate Research a Division of Siemens Corporation 
All rights reserved.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/
package com.siemens.cmiv.avt.mvt.core;

public class ProcessMonitoring {

	public static Process p;
	public static Thread waitForProcess;
	
	public void exec(String cmd, long timeOut){
		try {
			p = Runtime.getRuntime().exec(cmd);
			
			waitForProcess = new MonitorThread(p);
			waitForProcess.setName("Rserv thread");
			
			waitForProcess.start();
		}
	     catch (Exception err) {
	        err.printStackTrace();
	    }
			
	}

	public void kill(){
		try {
			if (waitForProcess.getName().compareToIgnoreCase("Rserv thread") == 0) {
				waitForProcess.interrupt();
				p.destroy();
			}
		} catch (Exception e) {
			System.out.println("UNKNOWN ERROR");
		}
	}
}

class MonitorThread extends Thread {

	private Process p;
	private boolean hasBeenInterrupted = false;

	public MonitorThread(Process process) {
		super();
		p = process;
	}

	public void run() {
		try {
			p.waitFor();
		} catch (InterruptedException e) {
			System.out.println("This process has been interrupted");
			hasBeenInterrupted = true;
		}
	}

	public boolean hasBeenInterrupted() {
		return hasBeenInterrupted;
	}
}
