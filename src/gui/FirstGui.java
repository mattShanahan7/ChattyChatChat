package gui;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class FirstGui implements ActionListener
{
	JButton open;
	JButton save;
	JButton close;
	JFrame frame;
	
	public FirstGui()
	{
		frame = new JFrame();
		save = new JButton("Save");
		close = new JButton("Close");
		open = new JButton("Open");
	}
	
	public void actionPerformed(ActionEvent event)
	{
		
	}
	
	public class OpenButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			open.setText("Opened");
		}
	}
	
	public class SaveButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			save.setText("Saved");
		}
	}
	
	public class CloseButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			close.setText("Closed");
		}
	}
	
	
	
	public void run()
	{
		frame.getContentPane().setLayout(new FlowLayout() );
		frame.getContentPane().add(open);
		frame.getContentPane().add(save);
		frame.getContentPane().add(close);
		
		open.addActionListener(new OpenButtonListener());
		save.addActionListener(new SaveButtonListener());
		close.addActionListener(new CloseButtonListener());
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(300, 300);
		frame.setVisible(true);
	}
	
	
	public static void main(String[] args)
	{
		FirstGui myGui = new FirstGui();
		myGui.run();
	}

}
