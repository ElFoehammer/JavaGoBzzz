package Mapper;

import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;

import Mapper.Pixel;
import Mapper.mapper;
import javax.swing.SpringLayout;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.ListSelectionModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class points {

	private JFrame frame;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void update() {
		
	}
	
	
	public static void launch() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					System.out.println(mapper.getWindow().getFrame().getHeight());
					points window = new points();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public points() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(true);
		frame.setBounds(mapper.getWindow().getFrame().getX() + mapper.getWindow().getFrame().getWidth(), 100, 258, 481);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		SpringLayout springLayout = new SpringLayout();
		frame.getContentPane().setLayout(springLayout);
		
		JScrollPane scrollPane = new JScrollPane();
		springLayout.putConstraint(SpringLayout.NORTH, scrollPane, 10, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, scrollPane, 10, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, scrollPane, -10, SpringLayout.SOUTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, scrollPane, -10, SpringLayout.EAST, frame.getContentPane());
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		scrollPane.setViewportView(table);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setFont(new Font("Tahoma", Font.PLAIN, 12));
		table.setFillsViewportHeight(false);
		
		TableModel t = new DefaultTableModel(
				new String[] { "#", "Name", "R", "G", "B", "T", "X", "Y" }, mapper.getWindow().getPixels().size()+1) {
			/**
			 * 
			 */
			
			private static final long serialVersionUID = 1L;
			boolean[] columnEditables = new boolean[] { false, false, false, false, false, false, false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		};
		table.setModel(t);
		table.getColumnModel().getColumn(0).setPreferredWidth(10);
		table.getColumnModel().getColumn(1).setPreferredWidth(50);
		table.getColumnModel().getColumn(2).setPreferredWidth(10);
		table.getColumnModel().getColumn(3).setPreferredWidth(10);
		table.getColumnModel().getColumn(4).setPreferredWidth(10);
		table.getColumnModel().getColumn(5).setPreferredWidth(10);
		table.getColumnModel().getColumn(6).setPreferredWidth(10);
		table.getColumnModel().getColumn(7).setPreferredWidth(10);
		for (int i = 0; i < mapper.getWindow().getPixels().size(); i++) {
			t.setValueAt(mapper.getWindow().getPixels().get(i).getPixelNumber(), i, 0);
			t.setValueAt(mapper.getWindow().getPixels().get(i).getNickName(), i, 1);
			t.setValueAt(mapper.getWindow().getPixels().get(i).getR(), i, 2);
			t.setValueAt(mapper.getWindow().getPixels().get(i).getG(), i, 3);
			t.setValueAt(mapper.getWindow().getPixels().get(i).getB(), i, 4);
			t.setValueAt(mapper.getWindow().getPixels().get(i).getT(), i, 5);
			t.setValueAt(mapper.getWindow().getPixels().get(i).getX(), i, 6);
			t.setValueAt(mapper.getWindow().getPixels().get(i).getY(), i, 7);
		}
		
	
	}
}
