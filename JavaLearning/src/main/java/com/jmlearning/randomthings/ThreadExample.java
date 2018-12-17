package com.jmlearning.randomthings;

public class ThreadExample implements Runnable {
	
	public void run() {
		
		for(int i = 0; i < 5; i++) {
			
			System.out.println("Child Thread: " + i);
			
			try {
				
				Thread.sleep(50);
			} 
			catch(InterruptedException ie) {
				
				System.out.println("Child Thread Interrupted :( " + ie);
			}
		}
		
		System.out.println("Child Thread Finished");
	}

	public static void main(String[] args) {

		Thread t = new Thread(new ThreadExample(), "My Thread");
		t.start();
		
		for(int i = 0; i < 5; i++) {
			
			System.out.println("Main Thread: " + i);
			
			try {
				
				Thread.sleep(100);
			}
			catch(InterruptedException ie) {
				
				System.out.println("Child Thread Interrupted >:( " + ie);
			}
		}
		
		System.out.println("Main Thread Finished");
	}

}
