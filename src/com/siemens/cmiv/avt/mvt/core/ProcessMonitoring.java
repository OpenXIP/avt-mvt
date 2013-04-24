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
