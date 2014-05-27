package asgn2Simulators;


import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

import javax.swing.*;

import asgn2CarParks.CarPark;
import asgn2Exceptions.SimulationException;
import asgn2Exceptions.VehicleException;

@SuppressWarnings("serial")
public class GUISimulator extends JFrame  implements ActionListener, Runnable  {
	private static final long serialVersionUID = -7031008862559936404L;
	public static final int WIDTH = 900;
	public static final int HEIGHT = 600;
	
	private JPanel pnlDisplay;
	private JPanel pnlTwo;
	private JPanel pnlFour;
	private JPanel pnlFive;
	
	private JButton btnStart;
	private JButton btnUnload;
	private JButton btnFind;
	private JButton btnGraph;
	private JPanel pnlBtn;
	
	private JTextArea areDisplay; 
		
	public GUISimulator(String arg0) throws HeadlessException {
		super(arg0);
		// TODO Auto-generated constructor stub
	}
	private void createGUI() {
			setSize(WIDTH, HEIGHT);
		    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		    setLayout(new BorderLayout());
		    
		    pnlDisplay = createPanel(Color.WHITE);
		    pnlTwo = createPanel(Color.LIGHT_GRAY);
		    pnlBtn = createPanel(Color.LIGHT_GRAY);
		    pnlFour = createPanel(Color.LIGHT_GRAY);
		    pnlFive = createPanel(Color.LIGHT_GRAY);
		    
		    btnStart = createButton("Start");
		    btnUnload = createButton("Unload");
		    btnFind = createButton("Find");
		    btnGraph = createButton("Graph");
		    
		    areDisplay = createTextArea();
		    
		    pnlDisplay.setLayout(new BorderLayout());
		    pnlDisplay.add(areDisplay, BorderLayout.CENTER);
		    
		    layoutButtonPanel(); 
		    
		    this.getContentPane().add(pnlDisplay,BorderLayout.CENTER);
		    this.getContentPane().add(pnlTwo,BorderLayout.NORTH);
		    this.getContentPane().add(pnlBtn,BorderLayout.SOUTH);
		    this.getContentPane().add(pnlFour,BorderLayout.EAST);
		    this.getContentPane().add(pnlFive,BorderLayout.WEST);
		    repaint(); 
		    this.setVisible(true);
		}
		
		
		private JPanel createPanel(Color c) {
			JPanel jp = new JPanel();
			jp.setBackground(c);
			return jp;
		}
		
		private JButton createButton(String str) {
			JButton jb = new JButton(str); 
			jb.addActionListener(this);
			return jb; 
		}
		
		private JTextArea createTextArea() {
			JTextArea jta = new JTextArea(); 
			jta.setEditable(true);
			jta.setLineWrap(true);
			jta.setFont(new Font("Arial",Font.BOLD,24));
			jta.setBorder(BorderFactory.createEtchedBorder());
			return jta;
		}
		
		private void layoutButtonPanel() {
			GridBagLayout layout = new GridBagLayout();
		    pnlBtn.setLayout(layout);
		    
		    //add components to grid
		    GridBagConstraints constraints = new GridBagConstraints(); 
		    
		    //Defaults
		    constraints.fill = GridBagConstraints.NONE;
		    constraints.anchor = GridBagConstraints.CENTER;
		    constraints.weightx = 100;
		    constraints.weighty = 100;
		    
		    addToPanel(pnlBtn, btnStart,constraints,0,0,2,1); 
		    addToPanel(pnlBtn, btnUnload,constraints,3,0,2,1); 
		    addToPanel(pnlBtn, btnFind,constraints,0,2,2,1); 
		    addToPanel(pnlBtn, btnGraph,constraints,3,2,2,1); 	
		}
		
		/**
	     * 
	     * A convenience method to add a component to given grid bag
	     * layout locations. Code due to Cay Horstmann 
	     *
	     * @param c the component to add
	     * @param constraints the grid bag constraints to use
	     * @param x the x grid position
	     * @param y the y grid position
	     * @param w the grid width
	     * @param h the grid height
	     */
	   private void addToPanel(JPanel jp,Component c, GridBagConstraints constraints, int x, int y, int w, int h) {  
	      constraints.gridx = x;
	      constraints.gridy = y;
	      constraints.gridwidth = w;
	      constraints.gridheight = h;
	      jp.add(c, constraints);
	   }

	
		
	public void run() {
		createGUI();
	}
	
	public void actionPerformed(ActionEvent e)    {
		//Get event source 
		
		Object src=e.getSource();
		System.out.println(src);
		
		//Consider the alternatives - not all active at once. 
		if (src== btnStart) {
			try {
				runningSimulation();
			} catch (VehicleException | SimulationException | IOException e1) {
				areDisplay.append("Error in user input");
				e1.printStackTrace();
			}
		}
			
		else if (src==btnUnload) {
		      //areDisplay.setText(btn.getText().trim());
		} else if (src==btnGraph) {
			JOptionPane.showMessageDialog(this,"This will open graph","Wiring Class: Warning",JOptionPane.WARNING_MESSAGE);
		} else if (src==btnFind) {
			JOptionPane.showMessageDialog(this,"An Error Message","Wiring Class: Error",JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void runningSimulation() throws VehicleException, SimulationException, IOException{
		CarPark car = new CarPark();
		Simulator sim = new Simulator();
		Log log = new Log();
		//SimulationRunner simRun = new SimulationRunner(car, sim, log);
		for (int time=0; time<=Constants.CLOSING_TIME; time++) {
			

			
			//queue elements exceed max waiting time
			if (!car.queueEmpty()) {
				car.archiveQueueFailures(time);	
			}
			//vehicles whose time has expired
			if (!car.carParkEmpty()) {
				//force exit at closing time, otherwise normal
				boolean force = (time == Constants.CLOSING_TIME);
				car.archiveDepartingVehicles(time, force);
			}
			//attempt to clear the queue 
			if (!car.carParkFull()) {
				car.processQueue(time,sim);
			}
			// new vehicles from minute 1 until the last hour
			if (time < (Constants.CLOSING_TIME-60)) { 
				car.tryProcessNewVehicles(time,sim);
			}
			//Log progress 
			//log.logEntry(time,car);
			areDisplay.append(car.getStatus(time));
			}
		log.finalise(car);
		}
	
	
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JFrame.setDefaultLookAndFeelDecorated(true);
        SwingUtilities.invokeLater(new GUISimulator("BorderLayout"));
	}
}
