package com.codlogic.activity;
import java.awt.FlowLayout;
import java.awt.Robot;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MouseMove {

	static Robot robot;
	static volatile boolean running = false;
	static Thread mouseThread;
	
	public MouseMove(){
		JFrame frame = new JFrame("MouseMove");
		frame.setSize(250,100);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		
		JButton btnStart = new JButton("Start");
		JButton btnStop = new JButton("Stop");
		
		JPanel jPanel = new JPanel();
		jPanel.setLayout(new FlowLayout());
		jPanel.add(btnStart);
		jPanel.add(btnStop);
		
		frame.setContentPane(jPanel);
		//frame.pack();
		frame.setVisible(true);
		
		btnStart.addActionListener((e)-> {
				start(); 
				btnStart.setEnabled(false);
				btnStop.setEnabled(true);
			
		});
		
		btnStop.setEnabled(false);
		btnStop.addActionListener((e)->{
			stop();
			btnStart.setEnabled(true);
			btnStop.setEnabled(false);
		});
	}
	
	private static void start() {
		running = true;
		
		if(mouseThread != null && mouseThread.isAlive()) {
			mouseThread.stop();
		}else {
			mouseThread = new Thread(()->startMovement());
			mouseThread.start();
			System.out.println(""+  mouseThread.getName() + "[" +mouseThread.getId()+"]");
		}
			
	}
	
	private static void stop() {
		running =false;
		System.out.println(""+  mouseThread.getName() + "[" +mouseThread.getId()+"]");
		if(mouseThread.isAlive())
			mouseThread.stop();
		
		System.gc();
	}
	
	public static void startMovement() {
		try {
			robot = new Robot();
			int W= 1024;
			int H= 768;
			
			while(running) {
				Thread.sleep(2000L);
				int x = (int)(Math.random() * W);
				int y = (int)(Math.random() * H);
				robot.mouseMove(x, y);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	 	
	public static void main(String... rgs) {
		new MouseMove();
	}
}
