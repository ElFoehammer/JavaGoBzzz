package Mapper;

import java.awt.EventQueue;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Robot;
import java.awt.Toolkit;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.SwingWorker;

import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import javax.swing.JSpinner;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.SystemColor;

public class mapper {

    private static mapper window;
    private JFrame frame;
    private JLabel lbimage;
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private int screenWidth = screenSize.width;
    private int screenHeight = screenSize.height;
    private int scaledDownWidth = 400;
    private int scaledDownHeight = 225;
    private int bootcount = 0;
    private int xCoordScaled;
    private int yCoordScaled;
    private int xCoord;
    private int yCoord;
    private int xImage;
    private int yImage;
    private Image original;
    private BufferedImage bufferedOriginal;
    private boolean updateCoords = false;
    private boolean imageUploaded = false;
    private boolean inPanel = false;
    private boolean altSelect = false;
    private boolean ctrlSelect = false;
    private JLabel lblXcoord;
    private JLabel lblYcoord;
    private JSpinner spin_Red;
    private JSpinner spin_Green;
    private JSpinner spin_Blue;
    private JSpinner spin_Tresh;
    private int red;
    private int green;
    private int blue;
    private String hexColor = "#000000";
    private Color c;
    private JPanel panel_1;
    private File selectedFile;
    private ArrayList<Pixel> pixels = new ArrayList<>();
    private boolean getColor = false;
    private boolean setPixel = false;
    private boolean pointsExists = false;
    private JButton btDropplet;
    private JButton btnPointList;
    private JTextField textHexClr;
    private JPanel panel;
    private JButton btnSnapShot;
    private BufferedImage screenFullImage;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    window = new mapper();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void updateBtnPointList() {
        btnPointList.setText("Points (" + pixels.size() + ")");
    }

    public static mapper getWindow() {
        return window;
    }

    public JFrame getFrame() {
        return this.frame;
    }

    public ArrayList<Pixel> getPixels() {
        return this.pixels;
    }

    /**
     * Create the application.
     */
    public mapper() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {

        if (((double) screenWidth / (double) screenHeight) == 16D / 9D) {
            scaledDownHeight = 225;
            scaledDownWidth = 400;
        } else if (((double) screenWidth / (double) screenHeight) > 16D / 9D) {
            scaledDownHeight = screenHeight * 400 / screenWidth;
            scaledDownWidth = 400;

        } else {
            scaledDownHeight = 225;
            scaledDownWidth = screenWidth * 225 / screenHeight;
        }

        frame = new JFrame();
        frame.setBounds(100, 100, 563, (int) (563 * ((screenHeight / screenWidth))));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        SpringLayout springLayout = new SpringLayout();
        frame.getContentPane().setLayout(springLayout);
        frame.setMinimumSize(new Dimension(563, (int) (563 * (screenHeight * 1.0 / screenWidth))));
        frame.setFocusable(true);
        frame.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                bootcount++;
                Image resized = getScaledImage(original, lbimage.getWidth(), lbimage.getHeight());
                lbimage.setIcon(new ImageIcon(resized));
                if (bootcount > 0) {
                    frame.setSize(frame.getWidth(), (int) (frame.getWidth() * (screenHeight * 1.0 / screenWidth)));
                }
            }
        });

        lbimage = new JLabel("");
        lbimage.setVerticalAlignment(SwingConstants.TOP);
        springLayout.putConstraint(SpringLayout.NORTH, lbimage, 6, SpringLayout.NORTH, frame.getContentPane());
        springLayout.putConstraint(SpringLayout.WEST, lbimage, 6, SpringLayout.WEST, frame.getContentPane());
        springLayout.putConstraint(SpringLayout.SOUTH, lbimage,
                -((int) (frame.getWidth() * ((screenHeight * 1.0 / screenWidth))) - 24 - scaledDownHeight),
                SpringLayout.SOUTH, frame.getContentPane());
        springLayout.putConstraint(SpringLayout.EAST, lbimage, -(frame.getWidth() - 6 - scaledDownWidth),
                SpringLayout.EAST, frame.getContentPane());
        lbimage.setBackground(Color.WHITE);
        frame.getContentPane().add(lbimage);

        panel = new JPanel();
        springLayout.putConstraint(SpringLayout.NORTH, panel, 6, SpringLayout.NORTH, frame.getContentPane());
        springLayout.putConstraint(SpringLayout.WEST, panel, 6, SpringLayout.WEST, frame.getContentPane());
        springLayout.putConstraint(SpringLayout.SOUTH, panel,
                -((int) (frame.getWidth() * ((screenHeight * 1.0 / screenWidth))) - 24 - scaledDownHeight),
                SpringLayout.SOUTH, frame.getContentPane());
        springLayout.putConstraint(SpringLayout.EAST, panel, -(frame.getWidth() - 6 - scaledDownWidth),
                SpringLayout.EAST, frame.getContentPane());
        panel.setBackground(Color.WHITE);
        frame.getContentPane().add(panel);

        btDropplet = new JButton("✎");
        btDropplet.setFont(new Font("Lucida Grande", Font.BOLD, 21));
        btDropplet.setForeground(SystemColor.BLACK);
        springLayout.putConstraint(SpringLayout.NORTH, btDropplet, 186, SpringLayout.NORTH, frame.getContentPane());
        springLayout.putConstraint(SpringLayout.WEST, btDropplet, -100, SpringLayout.EAST, frame.getContentPane());
        springLayout.putConstraint(SpringLayout.SOUTH, btDropplet, 221, SpringLayout.NORTH, frame.getContentPane());
        springLayout.putConstraint(SpringLayout.EAST, btDropplet, -10, SpringLayout.EAST, frame.getContentPane());
        frame.getContentPane().add(btDropplet);
        btDropplet.getModel().setEnabled(false);
        btDropplet.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (getColor) {
                    lbimage.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                    btDropplet.setForeground(SystemColor.BLACK);
                    btDropplet.getModel().setPressed(false);
                    getColor = false;
                } else {
                    btDropplet.setForeground(SystemColor.controlHighlight);
                    btDropplet.getModel().setPressed(true);
                    getColor = true;
                }
            }
        });

        JButton btnUpload = new JButton("Upload Image");
        springLayout.putConstraint(SpringLayout.NORTH, btnUpload, -35, SpringLayout.SOUTH, frame.getContentPane());
        springLayout.putConstraint(SpringLayout.WEST, btnUpload, 6, SpringLayout.WEST, frame.getContentPane());
        springLayout.putConstraint(SpringLayout.SOUTH, btnUpload, -6, SpringLayout.SOUTH, frame.getContentPane());
        springLayout.putConstraint(SpringLayout.EAST, btnUpload, 123, SpringLayout.WEST, frame.getContentPane());
        btnUpload.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
                FileNameExtensionFilter filter = new FileNameExtensionFilter("png, jpg & JPEG", "jpg", "png", "JPEG");
                jfc.setFileFilter(filter);
                jfc.setDialogTitle("Choose an Image");
                int returnValue = jfc.showOpenDialog(null);
                // int returnValue = jfc.showSaveDialog(null);

                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    selectedFile = jfc.getSelectedFile();
                    ImageIcon source = new ImageIcon(selectedFile.getAbsolutePath());
                    original = source.getImage();
                    Image resized = getScaledImage(original, lbimage.getWidth(), lbimage.getHeight());
                    lbimage.setIcon(new ImageIcon(resized));
                    btDropplet.getModel().setEnabled(true);
                    imageUploaded = true;
                    try {
                        bufferedOriginal = ImageIO.read(selectedFile);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }

        });
        frame.getContentPane().add(btnUpload);

        JButton btnSettings = new JButton("Settings");
        springLayout.putConstraint(SpringLayout.NORTH, btnSettings, -35, SpringLayout.SOUTH, frame.getContentPane());
        springLayout.putConstraint(SpringLayout.WEST, btnSettings, 124, SpringLayout.WEST, frame.getContentPane());
        springLayout.putConstraint(SpringLayout.SOUTH, btnSettings, -6, SpringLayout.SOUTH, frame.getContentPane());
        springLayout.putConstraint(SpringLayout.EAST, btnSettings, 241, SpringLayout.WEST, frame.getContentPane());
        btnSettings.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });
        frame.getContentPane().add(btnSettings);

        JButton btnExport = new JButton("Export");
        springLayout.putConstraint(SpringLayout.NORTH, btnExport, -35, SpringLayout.SOUTH, frame.getContentPane());
        springLayout.putConstraint(SpringLayout.WEST, btnExport, 242, SpringLayout.WEST, frame.getContentPane());
        springLayout.putConstraint(SpringLayout.SOUTH, btnExport, -6, SpringLayout.SOUTH, frame.getContentPane());
        springLayout.putConstraint(SpringLayout.EAST, btnExport, 359, SpringLayout.WEST, frame.getContentPane());
        btnExport.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    export();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
        frame.getContentPane().add(btnExport);

        btnPointList = new JButton("Points (" + Pixel.getPixelCounter() + ")");
        springLayout.putConstraint(SpringLayout.NORTH, btnPointList, -37, SpringLayout.SOUTH, frame.getContentPane());
        springLayout.putConstraint(SpringLayout.WEST, btnPointList, -106, SpringLayout.EAST, frame.getContentPane());
        springLayout.putConstraint(SpringLayout.SOUTH, btnPointList, -5, SpringLayout.SOUTH, frame.getContentPane());
        springLayout.putConstraint(SpringLayout.EAST, btnPointList, 0, SpringLayout.EAST, frame.getContentPane());
        frame.getContentPane().add(btnPointList);
        btnPointList.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                pointsExists = true;
                points.launch();
            }
        });

        panel_1 = new JPanel();
        panel_1.setBackground(Color.BLACK);
        springLayout.putConstraint(SpringLayout.NORTH, panel_1, 6, SpringLayout.NORTH, frame.getContentPane());
        springLayout.putConstraint(SpringLayout.WEST, panel_1, 0, SpringLayout.WEST, btDropplet);
        springLayout.putConstraint(SpringLayout.SOUTH, panel_1, 96, SpringLayout.NORTH, frame.getContentPane());
        springLayout.putConstraint(SpringLayout.EAST, panel_1, 0, SpringLayout.EAST, btDropplet);
        frame.getContentPane().add(panel_1);
        panel_1.setLayout(new BorderLayout(0, 0));

        textHexClr = new JTextField();

        textHexClr.setHorizontalAlignment(SwingConstants.CENTER);
        textHexClr.setText("#000000");
        textHexClr.setFont(new Font("Consolas", Font.PLAIN, 12));
        panel_1.add(textHexClr, BorderLayout.NORTH);
        textHexClr.setColumns(10);
        textHexClr.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String Sclr = textHexClr.getText();
                String clr = "BadFormat";
                int newRed = (int) spin_Red.getValue();
                int newGreen = (int) spin_Green.getValue();
                int newBlue = (int) spin_Blue.getValue();
                Boolean isHex = false;

                if ((textHexClr.getText().equals("BadFormat"))) {
                    red = (int) spin_Red.getValue();
                    green = (int) spin_Green.getValue();
                    blue = (int) spin_Blue.getValue();
                    c = new Color(red, green, blue);
                    panel_1.setBackground(c);
                    String hex = String.format("#%02x%02x%02x", red, green, blue);
                    textHexClr.setText(hex);
                } else {
                    if (Sclr.length() == 7 && Sclr.charAt(0) == '#') {
                        clr = Sclr.substring(1, 7);
                    } else if (Sclr.length() == 6) {
                        clr = Sclr.substring(0, 6);
                    } else {
                        textHexClr.setText("BadFormat");
                    }

                    isHex = checkHex(clr);
                    if (isHex) {
                        newRed = Integer.parseInt(clr.substring(0, 2), 16);
                        newGreen = Integer.parseInt(clr.substring(2, 4), 16);
                        newBlue = Integer.parseInt(clr.substring(4, 6), 16);
                    } else {
                        textHexClr.setText("BadFormat");
                    }
                }

                spin_Red.setValue(newRed);
                spin_Green.setValue(newGreen);
                spin_Blue.setValue(newBlue);
            }

        });

        JLabel lblRed = new JLabel("R:");
        springLayout.putConstraint(SpringLayout.NORTH, lblRed, 105, SpringLayout.NORTH, frame.getContentPane());
        springLayout.putConstraint(SpringLayout.WEST, lblRed, 0, SpringLayout.WEST, btDropplet);
        springLayout.putConstraint(SpringLayout.EAST, lblRed, 16, SpringLayout.WEST, btDropplet);
        frame.getContentPane().add(lblRed);

        JLabel lblGreen = new JLabel("G:");
        springLayout.putConstraint(SpringLayout.NORTH, lblGreen, 133, SpringLayout.NORTH, frame.getContentPane());
        springLayout.putConstraint(SpringLayout.WEST, lblGreen, 0, SpringLayout.WEST, btDropplet);
        springLayout.putConstraint(SpringLayout.EAST, lblGreen, 16, SpringLayout.WEST, btDropplet);
        frame.getContentPane().add(lblGreen);

        JLabel lblBlue = new JLabel("B:");
        springLayout.putConstraint(SpringLayout.NORTH, lblBlue, 161, SpringLayout.NORTH, frame.getContentPane());
        springLayout.putConstraint(SpringLayout.WEST, lblBlue, 0, SpringLayout.WEST, btDropplet);
        springLayout.putConstraint(SpringLayout.EAST, lblBlue, 16, SpringLayout.WEST, btDropplet);
        frame.getContentPane().add(lblBlue);

        lblXcoord = new JLabel("x:");
        springLayout.putConstraint(SpringLayout.NORTH, lblXcoord, 2, SpringLayout.SOUTH, panel);
        springLayout.putConstraint(SpringLayout.WEST, lblXcoord, 16, SpringLayout.WEST, frame.getContentPane());
        springLayout.putConstraint(SpringLayout.EAST, lblXcoord, 80, SpringLayout.WEST, frame.getContentPane());
        frame.getContentPane().add(lblXcoord);

        lblYcoord = new JLabel("y:");
        springLayout.putConstraint(SpringLayout.NORTH, lblYcoord, 2, SpringLayout.SOUTH, panel);
        springLayout.putConstraint(SpringLayout.WEST, lblYcoord, 92, SpringLayout.WEST, frame.getContentPane());
        springLayout.putConstraint(SpringLayout.EAST, lblYcoord, 156, SpringLayout.WEST, frame.getContentPane());
        frame.getContentPane().add(lblYcoord);

        spin_Tresh = new JSpinner();
        springLayout.putConstraint(SpringLayout.NORTH, spin_Tresh, 2, SpringLayout.SOUTH, panel);
        springLayout.putConstraint(SpringLayout.WEST, spin_Tresh, -65, SpringLayout.EAST, panel);
        springLayout.putConstraint(SpringLayout.EAST, spin_Tresh, 0, SpringLayout.EAST, panel);
        spin_Tresh.setModel(new SpinnerNumberModel(0, 0, 255, 1));
        frame.getContentPane().add(spin_Tresh);

        JLabel lblTreshhold = new JLabel("Treshold:");
        springLayout.putConstraint(SpringLayout.NORTH, lblTreshhold, 7, SpringLayout.SOUTH, panel);
        springLayout.putConstraint(SpringLayout.WEST, lblTreshhold, -65, SpringLayout.WEST, spin_Tresh);
        springLayout.putConstraint(SpringLayout.EAST, lblTreshhold, 2, SpringLayout.WEST, spin_Tresh);
        frame.getContentPane().add(lblTreshhold);

        spin_Red = new JSpinner();
        springLayout.putConstraint(SpringLayout.NORTH, spin_Red, 105, SpringLayout.NORTH, frame.getContentPane());
        springLayout.putConstraint(SpringLayout.WEST, spin_Red, -85, SpringLayout.EAST, frame.getContentPane());
        springLayout.putConstraint(SpringLayout.SOUTH, spin_Red, 121, SpringLayout.NORTH, frame.getContentPane());
        springLayout.putConstraint(SpringLayout.EAST, spin_Red, -10, SpringLayout.EAST, frame.getContentPane());
        frame.getContentPane().add(spin_Red);
        spin_Red.setModel(new SpinnerNumberModel(0, 0, 255, 1));
        spin_Red.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                red = (int) spin_Red.getValue();
                green = (int) spin_Green.getValue();
                blue = (int) spin_Blue.getValue();
                c = new Color(red, green, blue);
                panel_1.setBackground(c);
                hexColor = String.format("#%02x%02x%02x", red, green, blue);
                textHexClr.setText(hexColor);
            }
        });

        spin_Green = new JSpinner();
        springLayout.putConstraint(SpringLayout.NORTH, spin_Green, 133, SpringLayout.NORTH, frame.getContentPane());
        springLayout.putConstraint(SpringLayout.WEST, spin_Green, -85, SpringLayout.EAST, frame.getContentPane());
        springLayout.putConstraint(SpringLayout.SOUTH, spin_Green, 149, SpringLayout.NORTH, frame.getContentPane());
        springLayout.putConstraint(SpringLayout.EAST, spin_Green, -10, SpringLayout.EAST, frame.getContentPane());
        frame.getContentPane().add(spin_Green);
        spin_Green.setModel(new SpinnerNumberModel(0, 0, 255, 1));
        spin_Green.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                red = (int) spin_Red.getValue();
                green = (int) spin_Green.getValue();
                blue = (int) spin_Blue.getValue();
                c = new Color(red, green, blue);
                panel_1.setBackground(c);
                String hex = String.format("#%02x%02x%02x", red, green, blue);
                textHexClr.setText(hex);
            }
        });

        spin_Blue = new JSpinner();
        springLayout.putConstraint(SpringLayout.NORTH, spin_Blue, 162, SpringLayout.NORTH, frame.getContentPane());
        springLayout.putConstraint(SpringLayout.WEST, spin_Blue, -85, SpringLayout.EAST, frame.getContentPane());
        springLayout.putConstraint(SpringLayout.SOUTH, spin_Blue, 178, SpringLayout.NORTH, frame.getContentPane());
        springLayout.putConstraint(SpringLayout.EAST, spin_Blue, -10, SpringLayout.EAST, frame.getContentPane());
        frame.getContentPane().add(spin_Blue);
        spin_Blue.setModel(new SpinnerNumberModel(0, 0, 255, 1));
        spin_Blue.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                red = (int) spin_Red.getValue();
                green = (int) spin_Green.getValue();
                blue = (int) spin_Blue.getValue();
                c = new Color(red, green, blue);
                panel_1.setBackground(c);
                String hex = String.format("#%02x%02x%02x", red, green, blue);
                textHexClr.setText(hex);
            }
        });

        
        btnSnapShot = new JButton("");
		springLayout.putConstraint(SpringLayout.SOUTH, btnSnapShot, 25, SpringLayout.NORTH, panel);
		springLayout.putConstraint(SpringLayout.EAST, btnSnapShot, 25, SpringLayout.EAST, panel);
		btnSnapShot.setFont(new Font("Lucida Grande", Font.PLAIN, 5));
		springLayout.putConstraint(SpringLayout.NORTH, btnSnapShot, 0, SpringLayout.NORTH, panel);
		springLayout.putConstraint(SpringLayout.WEST, btnSnapShot, 0, SpringLayout.EAST, panel);
		frame.getContentPane().add(btnSnapShot);
				
		try {
			Image img = ImageIO.read(getClass().getResource("images/cam.jpg"));
			Image resized = getScaledImage(img, 15, 15);
			btnSnapShot.setIcon(new ImageIcon(resized));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		btnSnapShot.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
				try {
					Robot robot = new Robot();
					screenFullImage = robot.createScreenCapture(screenRect);
				} catch (AWTException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				
				ImageIcon source = new ImageIcon(screenFullImage);
				original = source.getImage();
				Image resized = getScaledImage(original, lbimage.getWidth(), lbimage.getHeight());
				lbimage.setIcon(new ImageIcon(resized));
				btDropplet.getModel().setEnabled(true);
				imageUploaded = true;
				bufferedOriginal = screenFullImage;
				
			}

		});
		
        lbimage.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int xScaled = xCoordScaled;
                int yScaled = yCoordScaled;
                lblXcoord.setText("x: " + xScaled);
                lblYcoord.setText("y: " + yScaled);
                updateCoords = false;

                if (setPixel && imageUploaded) {

                    pixels.add(new Pixel((int) spin_Red.getValue(), (int) spin_Green.getValue(),
                            (int) spin_Blue.getValue(), (int) spin_Tresh.getValue(), xScaled, yScaled));
                    if (pointsExists)
                        points.update(points.getWindow().getTable());
                    btnPointList.setText("Points (" + Pixel.getPixelCounter() + ")");
                }

                if (getColor && imageUploaded) {
                    int clr = bufferedOriginal.getRGB(xImage, yImage);
                    int newRed = (clr & 0x00ff0000) >> 16;
                    int newGreen = (clr & 0x0000ff00) >> 8;
                    int newBlue = clr & 0x000000ff;
                    spin_Red.setValue(newRed);
                    spin_Green.setValue(newGreen);
                    spin_Blue.setValue(newBlue);
                    updateCoords = true;
                    if (!altSelect) {
                        lbimage.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                        btDropplet.setForeground(SystemColor.BLACK);
                        btDropplet.getModel().setPressed(false);
                        getColor = false;
                        updateCoords = false;
                    }
                }

                if (!getColor && !setPixel) {
                    updateCoords = false;
                }

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                frame.requestFocus();
                updateCoords = true;
                inPanel = true;
                if (getColor) {
                    lbimage.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
                }
                if (altSelect) {
                    lbimage.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
                    btDropplet.setForeground(SystemColor.controlHighlight);
                    btDropplet.getModel().setPressed(true);
                    getColor = true;
                }

                if (ctrlSelect) {
                    lbimage.setCursor(new Cursor(Cursor.HAND_CURSOR));
                    setPixel = true;
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                updateCoords = false;
                inPanel = false;
                lbimage.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                if (altSelect) {
                    btDropplet.setForeground(SystemColor.BLACK);
                    btDropplet.getModel().setPressed(false);
                    getColor = false;
                }
                if (ctrlSelect) {
                    lbimage.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                    setPixel = false;
                }
            }

        });

        lbimage.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                frame.requestFocus();
                xCoord = MouseInfo.getPointerInfo().getLocation().x - lbimage.getLocationOnScreen().x;
                if ((int) ((xCoord * 1.0 * screenWidth) / lbimage.getWidth()) >= 0
                        && ((int) (xCoord * 1.0 * screenWidth) / lbimage.getWidth()) <= screenWidth) {
                    xCoordScaled = (int) (xCoord * 1.0 * screenWidth) / lbimage.getWidth();

                    if (updateCoords) {
                        lblXcoord.setText("x: " + xCoordScaled);
                    }
                }

                yCoord = MouseInfo.getPointerInfo().getLocation().y - lbimage.getLocationOnScreen().y;
                if ((int) (yCoord * 1.0 * screenHeight) / lbimage.getHeight() >= 0
                        && (int) (yCoord * 1.0 * screenHeight) / lbimage.getHeight() <= screenHeight) {
                    yCoordScaled = (int) (yCoord * 1.0 * screenHeight) / lbimage.getHeight();

                    if (updateCoords) {
                        lblYcoord.setText("y: " + yCoordScaled);
                    }
                }
                if (imageUploaded) {
                    if ((int) ((xCoord * 1.0 / lbimage.getWidth()) * bufferedOriginal.getWidth()) >= 0
                            && (int) ((xCoord * 1.0 / lbimage.getWidth())
                            * bufferedOriginal.getWidth()) <= bufferedOriginal.getWidth()) {
                        xImage = (int) ((xCoord * 1.0 / lbimage.getWidth()) * bufferedOriginal.getWidth());

                    }

                    if ((int) ((yCoord * 1.0 / lbimage.getHeight()) * bufferedOriginal.getHeight()) >= 0
                            && (int) ((yCoord * 1.0 / lbimage.getHeight())
                            * bufferedOriginal.getHeight()) <= bufferedOriginal.getWidth()) {
                        yImage = (int) ((yCoord * 1.0 / lbimage.getHeight()) * bufferedOriginal.getHeight());

                    }
                }
            }
        });

        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (imageUploaded && (e.getKeyCode() == KeyEvent.VK_SHIFT)) {
                    altSelect = true;
                }

                if (inPanel && imageUploaded && (e.getKeyCode() == KeyEvent.VK_SHIFT)) {
                    lbimage.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
                    btDropplet.setForeground(SystemColor.controlHighlight);
                    btDropplet.getModel().setPressed(true);
                    getColor = true;
                }

                if (inPanel && imageUploaded && e.getKeyCode() == KeyEvent.VK_CONTROL) {
                    lbimage.setCursor(new Cursor(Cursor.HAND_CURSOR));
                    ctrlSelect = true;
                    setPixel = true;
                }

                if (e.getKeyCode() == KeyEvent.VK_UP && (yCoordScaled > 0) && (yImage > 0)) {
                    updateCoords = true;
                    yCoordScaled--;
                    yImage--;
                    lblYcoord.setText("y: " + yCoordScaled);
                }

                if (e.getKeyCode() == KeyEvent.VK_DOWN && (yCoordScaled < screenHeight - 1)
                        && (yImage < bufferedOriginal.getHeight() - 1)) {
                    updateCoords = true;
                    yCoordScaled++;
                    yImage++;
                    lblYcoord.setText("y: " + yCoordScaled);
                }

                if (e.getKeyCode() == KeyEvent.VK_RIGHT && (xCoordScaled < screenWidth - 1)
                        && (xImage < bufferedOriginal.getWidth() - 1)) {
                    updateCoords = true;
                    xCoordScaled++;
                    xImage++;
                    lblXcoord.setText("x: " + xCoordScaled);
                }

                if (e.getKeyCode() == KeyEvent.VK_LEFT && (xCoordScaled > 0) && (xImage > 0)) {
                    updateCoords = true;
                    xCoordScaled--;
                    xImage--;
                    lblXcoord.setText("x: " + xCoordScaled);
                }

            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
                    altSelect = false;
                    lbimage.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                    btDropplet.setForeground(SystemColor.BLACK);
                    btDropplet.getModel().setPressed(false);
                    getColor = false;
                }

                if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
                    lbimage.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                    ctrlSelect = false;
                    setPixel = false;
                }
            }
        });

    }

    private boolean checkHex(String SHex) {
        for (int x = 0; x < SHex.length(); x++) {
            switch (SHex.charAt(x)) {
                case '0':
                    break;
                case '1':
                    break;
                case '2':
                    break;
                case '3':
                    break;
                case '4':
                    break;
                case '5':
                    break;
                case '6':
                    break;
                case '7':
                    break;
                case '8':
                    break;
                case '9':
                    break;
                case 'a':
                    break;
                case 'A':
                    break;
                case 'b':
                    break;
                case 'B':
                    break;
                case 'c':
                    break;
                case 'C':
                    break;
                case 'd':
                    break;
                case 'D':
                    break;
                case 'e':
                    break;
                case 'E':
                    break;
                case 'f':
                    break;
                case 'F':
                    break;
                default:
                    return false;
            }
        }
        return true;
    }

    public void export() throws IOException {
		File file = null;
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH.mm.ss");
        String filename = "Map " + LocalDateTime.now().format(format);
		try {
			file = new File(FileSystemView.getFileSystemView().getHomeDirectory() + "/" + filename + ".txt");
		} catch (Exception e) {
			e.printStackTrace();
		}
		assert file != null;

        String toBeExported = "v1.0";
        toBeExported += "\n" + screenWidth;
        toBeExported += "\n" + screenHeight;

        for (Pixel pixel : pixels) {
            toBeExported += "\n-";
            toBeExported += "\n" + pixel.getPixelNumber();
            toBeExported += "\n" + pixel.getNickName();
            toBeExported += "\n" + pixel.getR();
            toBeExported += "\n" + pixel.getG();
            toBeExported += "\n" + pixel.getB();
            toBeExported += "\n" + pixel.getT();
            toBeExported += "\n" + pixel.getX();
            toBeExported += "\n" + pixel.getY();
        }
		try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(file.getPath()))) {
			writer.write(toBeExported);
		}


    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public JButton getBtnPointList() {
        return btnPointList;
    }

    private Image getScaledImage(Image srcImg, int w, int h) {
        BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = resizedImg.createGraphics();

        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(srcImg, 0, 0, w, h, null);
        g2.dispose();

        return resizedImg;
    }
}