package Mapper;

import java.awt.EventQueue;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.SpringLayout;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.ListSelectionModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JMenuItem;

public class points {

	private JFrame frame;
	private static JTable table;
	private JButton btnSave;
	private JButton btnCancel;
	private static points window;
	private JLabel lblValues;
	private static JMenuItem mntmNewMenuItem;

	/**
	 * Launch the application.
	 */
	public static void update(JTable tab) {
		TableModel t = new DefaultTableModel(new String[] { "#", "Name", "R", "G", "B", "T", "X", "Y" },
				mapper.getWindow().getPixels().size()) {
			/**
			 * 
			 */

			private static final long serialVersionUID = 1L;
			boolean[] columnEditables = new boolean[] { false, true, true, true, true, true, true, true};

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		};
		tab.setModel(t);
		tab.getColumnModel().getColumn(0).setPreferredWidth(10);
		tab.getColumnModel().getColumn(1).setPreferredWidth(50);
		tab.getColumnModel().getColumn(2).setPreferredWidth(10);
		tab.getColumnModel().getColumn(3).setPreferredWidth(10);
		tab.getColumnModel().getColumn(4).setPreferredWidth(10);
		tab.getColumnModel().getColumn(5).setPreferredWidth(10);
		tab.getColumnModel().getColumn(6).setPreferredWidth(10);
		tab.getColumnModel().getColumn(7).setPreferredWidth(10);
		for (int i = 0; i < mapper.getWindow().getPixels().size(); i++) {
			mapper.getWindow().getPixels().get(i).setPixelNumber(i + 1);
			t.setValueAt(mapper.getWindow().getPixels().get(i).getPixelNumber(), i, 0);
			t.setValueAt(mapper.getWindow().getPixels().get(i).getNickName(), i, 1);
			t.setValueAt(mapper.getWindow().getPixels().get(i).getR(), i, 2);
			t.setValueAt(mapper.getWindow().getPixels().get(i).getG(), i, 3);
			t.setValueAt(mapper.getWindow().getPixels().get(i).getB(), i, 4);
			t.setValueAt(mapper.getWindow().getPixels().get(i).getT(), i, 5);
			t.setValueAt(mapper.getWindow().getPixels().get(i).getX(), i, 6);
			t.setValueAt(mapper.getWindow().getPixels().get(i).getY(), i, 7);
		}
		Pixel.setPixelCounter(mapper.getWindow().getPixels().size());
	}

	public static void launch() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window = new points();
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
		frame.setTitle("Points");
		frame.getContentPane().setEnabled(false);
		frame.setResizable(true);
		frame.setBounds(mapper.getWindow().getFrame().getX() + mapper.getWindow().getFrame().getWidth(), mapper.getWindow().getFrame().getY(), 380, 480);
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		SpringLayout springLayout = new SpringLayout();
		frame.getContentPane().setLayout(springLayout);

		JScrollPane scrollPane = new JScrollPane();
		springLayout.putConstraint(SpringLayout.NORTH, scrollPane, 10, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, scrollPane, 10, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, scrollPane, -44, SpringLayout.SOUTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, scrollPane, -10, SpringLayout.EAST, frame.getContentPane());
		frame.getContentPane().add(scrollPane);

		table = new JTable();
		scrollPane.setViewportView(table);
		scrollPane.setViewportView(table);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setFont(new Font("Tahoma", Font.PLAIN, 12));
		table.setFillsViewportHeight(false);
		table.getSelectedRow();

		TableModel t = new DefaultTableModel(new String[] { "#", "Name", "R", "G", "B", "T", "X", "Y" },
				mapper.getWindow().getPixels().size()) {
			/**
			 * 
			 */

			private static final long serialVersionUID = 1L;
			boolean[] columnEditables = new boolean[] { false, true, true, true, true, true, true, true };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		};
		table.setModel(t);
		table.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
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
		
		mntmNewMenuItem = new JMenuItem("Delete");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mapper.getWindow().getPixels().remove(table.getSelectedRow());
				mapper.getWindow().updateBtnPointList();
				update(table);
			}
		});

		btnSave = new JButton("Save");
		springLayout.putConstraint(SpringLayout.NORTH, btnSave, 6, SpringLayout.SOUTH, scrollPane);
		springLayout.putConstraint(SpringLayout.SOUTH, btnSave, -6, SpringLayout.SOUTH, frame.getContentPane());
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean canSave = true;
				for (int i = 0; i < mapper.getWindow().getPixels().size(); i++) {
					// Check if values in R are valid
					if (!((table.getModel().getValueAt(i, 2).getClass() == Integer.class
							&& (int) table.getModel().getValueAt(i, 2) >= 0
							&& (int) table.getModel().getValueAt(i, 2) <= 255)
							|| (isNumeric((String) table.getModel().getValueAt(i, 2))
									&& Integer.parseInt((String) table.getModel().getValueAt(i, 2)) >= 0
									&& Integer.parseInt((String) table.getModel().getValueAt(i, 2)) <= 255))) {
						canSave = false;
						break;
					}
					// Check if values in G are valid
					if (!((table.getModel().getValueAt(i, 3).getClass() == Integer.class
							&& (int) table.getModel().getValueAt(i, 3) >= 0
							&& (int) table.getModel().getValueAt(i, 3) <= 255)
							|| (isNumeric((String) table.getModel().getValueAt(i, 3))
									&& Integer.parseInt((String) table.getModel().getValueAt(i, 3)) >= 0
									&& Integer.parseInt((String) table.getModel().getValueAt(i, 3)) <= 255))) {
						canSave = false;
						break;
					}
					// Check if values in B are valid
					if (!((table.getModel().getValueAt(i, 4).getClass() == Integer.class
							&& (int) table.getModel().getValueAt(i, 4) >= 0
							&& (int) table.getModel().getValueAt(i, 4) <= 255)
							|| (isNumeric((String) table.getModel().getValueAt(i, 4))
									&& Integer.parseInt((String) table.getModel().getValueAt(i, 4)) >= 0
									&& Integer.parseInt((String) table.getModel().getValueAt(i, 4)) <= 255))) {
						canSave = false;
						break;
					}
					// Check if values in T are valid
					if (!((table.getModel().getValueAt(i, 5).getClass() == Integer.class
							&& (int) table.getModel().getValueAt(i, 5) >= 0
							&& (int) table.getModel().getValueAt(i, 5) <= 255)
							|| (isNumeric((String) table.getModel().getValueAt(i, 5))
									&& Integer.parseInt((String) table.getModel().getValueAt(i, 5)) >= 0
									&& Integer.parseInt((String) table.getModel().getValueAt(i, 5)) <= 255))) {
						canSave = false;
						break;
					}
					// Check if values in X are valid
					if (!((table.getModel().getValueAt(i, 6).getClass() == Integer.class
							&& (int) table.getModel().getValueAt(i, 6) >= 0
							&& (int) table.getModel().getValueAt(i, 6) < mapper.getWindow().getScreenWidth())
							|| (isNumeric((String) table.getModel().getValueAt(i, 6))
									&& Integer.parseInt((String) table.getModel().getValueAt(i, 6)) >= 0
									&& Integer.parseInt((String) table.getModel().getValueAt(i, 6)) < mapper.getWindow()
											.getScreenWidth()))) {
						canSave = false;
						break;
					}
					// Check if values in Y are valid
					if (!((table.getModel().getValueAt(i, 7).getClass() == Integer.class
							&& (int) table.getModel().getValueAt(i, 7) >= 0
							&& (int) table.getModel().getValueAt(i, 7) < mapper.getWindow().getScreenHeight())
							|| (isNumeric((String) table.getModel().getValueAt(i, 7))
									&& Integer.parseInt((String) table.getModel().getValueAt(i, 7)) >= 0
									&& Integer.parseInt((String) table.getModel().getValueAt(i, 7)) < mapper.getWindow()
											.getScreenHeight()))) {
						canSave = false;
						break;
					}
				}
				if (canSave) {
					for (int i = 0; i < mapper.getWindow().getPixels().size(); i++) {
						// Set Name
						mapper.getWindow().getPixels().get(i).setNickName((String) table.getModel().getValueAt(i, 1));
						// Set R
						if (table.getModel().getValueAt(i, 2).getClass() == Integer.class)
							mapper.getWindow().getPixels().get(i).setR((int) table.getModel().getValueAt(i, 2));
						else
							mapper.getWindow().getPixels().get(i)
									.setR(Integer.parseInt((String) table.getModel().getValueAt(i, 2)));
						// Set G
						if (table.getModel().getValueAt(i, 3).getClass() == Integer.class)
							mapper.getWindow().getPixels().get(i).setG((int) table.getModel().getValueAt(i, 3));
						else
							mapper.getWindow().getPixels().get(i)
									.setG(Integer.parseInt((String) table.getModel().getValueAt(i, 3)));
						// Set B
						if (table.getModel().getValueAt(i, 4).getClass() == Integer.class)
							mapper.getWindow().getPixels().get(i).setB((int) table.getModel().getValueAt(i, 4));
						else
							mapper.getWindow().getPixels().get(i)
									.setB(Integer.parseInt((String) table.getModel().getValueAt(i, 4)));
						// Set T
						if (table.getModel().getValueAt(i, 5).getClass() == Integer.class)
							mapper.getWindow().getPixels().get(i).setT((int) table.getModel().getValueAt(i, 5));
						else
							mapper.getWindow().getPixels().get(i)
									.setT(Integer.parseInt((String) table.getModel().getValueAt(i, 5)));
						// Set X
						if (table.getModel().getValueAt(i, 6).getClass() == Integer.class)
							mapper.getWindow().getPixels().get(i).setX((int) table.getModel().getValueAt(i, 6));
						else
							mapper.getWindow().getPixels().get(i)
									.setX(Integer.parseInt((String) table.getModel().getValueAt(i, 6)));
						// Set Y
						if (table.getModel().getValueAt(i, 7).getClass() == Integer.class)
							mapper.getWindow().getPixels().get(i).setY((int) table.getModel().getValueAt(i, 7));
						else
							mapper.getWindow().getPixels().get(i)
									.setY(Integer.parseInt((String) table.getModel().getValueAt(i, 7)));
					}
					lblValues.setVisible(false);
				} else
					lblValues.setVisible(true);
			}
		});
		springLayout.putConstraint(SpringLayout.WEST, btnSave, 10, SpringLayout.WEST, frame.getContentPane());
		frame.getContentPane().add(btnSave);

		btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				update(table);
			}
		});
		springLayout.putConstraint(SpringLayout.NORTH, btnCancel, 6, SpringLayout.SOUTH, scrollPane);
		springLayout.putConstraint(SpringLayout.WEST, btnCancel, 6, SpringLayout.EAST, btnSave);
		springLayout.putConstraint(SpringLayout.SOUTH, btnCancel, 0, SpringLayout.SOUTH, btnSave);
		frame.getContentPane().add(btnCancel);

		lblValues = new JLabel("Invalid Value(s)");
		springLayout.putConstraint(SpringLayout.EAST, lblValues, 109, SpringLayout.EAST, btnCancel);
		lblValues.setVisible(false);
		springLayout.putConstraint(SpringLayout.NORTH, lblValues, 9, SpringLayout.NORTH, btnSave);
		springLayout.putConstraint(SpringLayout.WEST, lblValues, 6, SpringLayout.EAST, btnCancel);
		frame.getContentPane().add(lblValues);

		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (table.getSelectedRow() != -1) {
					mapper.getWindow().getPixels().remove(table.getSelectedRow());
					mapper.getWindow().updateBtnPointList();
					update(table);
				}
			}
		});
		springLayout.putConstraint(SpringLayout.NORTH, btnDelete, 6, SpringLayout.SOUTH, scrollPane);
		springLayout.putConstraint(SpringLayout.EAST, btnDelete, 0, SpringLayout.EAST, scrollPane);
		frame.getContentPane().add(btnDelete);

	}

	public JTable getTable() {
		return table;
	}

	public static points getWindow() {
		return window;
	}

	public static boolean isNumeric(final String str) {
		if (str == null || str.length() == 0) {
			return false;
		}
		return str.chars().allMatch(Character::isDigit);
	}

}
