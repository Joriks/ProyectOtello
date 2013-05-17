package aima.gui.applications.search.games;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import aima.core.environment.connectfour.ConnectFourAIPlayer;
import aima.core.environment.otello.OtelloAIPlayer;
import aima.core.environment.otello.OtelloGame;
import aima.core.environment.otello.OtelloState;
import aima.core.search.adversarial.AdversarialSearch;
import aima.core.search.adversarial.AlphaBetaSearch;
import aima.core.search.adversarial.IterativeDeepeningAlphaBetaSearch;
import aima.core.search.adversarial.MinimaxSearch;
import aima.core.search.framework.Metrics;
import aima.core.util.datastructure.XYLocation;


public class OtelloApp {

	/** Used for integration into the universal demo application. */
	public JFrame constructApplicationFrame() {
		JFrame frame = new JFrame();
		JPanel panel = new OtelloPanel();
		frame.add(panel, BorderLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		return frame;
	}

	/** Application starter. */
	public static void main(String[] args) {
		JFrame frame = new OtelloApp().constructApplicationFrame();
		frame.setSize(400, 400);
		frame.setVisible(true);
	}

	/** Simple panel to control the game. */
	private static class OtelloPanel extends JPanel implements
			ActionListener {
		private static final long serialVersionUID = 1L;
		JComboBox strategyCombo;
		JComboBox timeCombo;
		JButton clearButton;
		JButton proposeButton;
		JButton[][] squares;
		JLabel statusBar;

		OtelloGame game;
		OtelloState currState;
		Metrics searchMetrics;

		/** Standard constructor. */
		OtelloPanel() {
			this.setLayout(new BorderLayout());
			JToolBar tbar = new JToolBar();
			tbar.setFloatable(false);
			strategyCombo = new JComboBox(new String[] {"OtelloAIPlayer", "OtelloAIPlayer (log)" });
			timeCombo = new JComboBox(new String[] { "5sec", "10sec", "15sec","20sec", "30sec", "1min" });
			timeCombo.setSelectedIndex(2);
			strategyCombo.setSelectedIndex(1);
			tbar.add(strategyCombo);
			tbar.add(timeCombo);
			tbar.add(Box.createHorizontalGlue());
			clearButton = new JButton("Clear");
			clearButton.addActionListener(this);
			tbar.add(clearButton);
			proposeButton = new JButton("Propose Move");
			proposeButton.addActionListener(this);
			tbar.add(proposeButton);

			add(tbar, BorderLayout.NORTH);
			JPanel spanel = new JPanel();
			spanel.setLayout(new GridLayout(8, 8));
			add(spanel, BorderLayout.CENTER);
			squares = new JButton[8][8];
			Font f = new java.awt.Font(Font.SANS_SERIF, Font.PLAIN, 32);
			for (int i = 0; i < 8; i++) {
				for (int j = 0; j < 8; j++) {
					JButton square = new JButton("");
					square.setFont(f);
					square.setBackground(Color.WHITE);
					square.addActionListener(this);
					squares[i][j] = square;
					spanel.add(square);
				}
			}
			statusBar = new JLabel(" ");
			statusBar.setBorder(BorderFactory.createEtchedBorder());
			add(statusBar, BorderLayout.SOUTH);

			game = new OtelloGame();
			actionPerformed(null);
		}

		/** Handles all button events and updates the view. */
		@Override
		public void actionPerformed(ActionEvent ae) {
			searchMetrics = null;
			if (ae == null || ae.getSource() == clearButton)
				currState = game.getInitialState();
			else if (!game.isTerminal(currState)) {
				if (ae.getSource() == proposeButton)
					proposeMove();
				else {
					for (int i = 0; i < 8; i++)
						for(int j = 0;j < 8;j++)
							if (ae.getSource() == squares[i][j])
								currState = game.getResult(currState, new XYLocation(i, j));
				}
			}
			for (int i = 0; i < 8; i++) {
				for (int j = 0; j < 8; j++) {
					String val = currState.getValue(i,j);
					if (val == OtelloState.EMPTY)
						val = "";
					else
						if(val == OtelloState.white)
							val = "X";
						else
							val = "O";
					squares[i][j].setText(val);
				}
			}
			updateStatus();
		}

		/** Uses adversarial search for selecting the next action. */
		private void proposeMove() {
			AdversarialSearch<OtelloState, XYLocation> search = null;
			XYLocation action;
			int time = 15;
			switch (timeCombo.getSelectedIndex()){
				case 0:
					time = 5;
					break;
				case 1:
					time = 10;
					break;
				case 2:
					time = 15;
				case 3:
					time = 20;
					break;
				case 4:
					time = 30;
					break;
				case 5:
					time = 60;
					break;
			}			
			switch (strategyCombo.getSelectedIndex()) {
				case 0:
					search = new OtelloAIPlayer(game, time);
					break;
				case 1:
					search = new OtelloAIPlayer(game, time);
					((OtelloAIPlayer) search).setLogEnabled(true);
					break;
				default:
					search = new OtelloAIPlayer(game, time);
					((OtelloAIPlayer) search).setLogEnabled(true);
			
				/*default:
					search = new OtelloAIPlayer(game, time);
					((OtelloAIPlayer) search).setLogEnabled(true);*/
			}
			action = search.makeDecision(currState);
			searchMetrics = search.getMetrics();
			currState = game.getResult(currState, action);
		}

		/** Updates the status bar. */
		private void updateStatus() {
			String statusText;
			if (game.isTerminal(currState))
				if (game.getUtility(currState, OtelloState.white) == 1)
					statusText = "white has won :-)";
				else if (game.getUtility(currState, OtelloState.black) == 1)
					statusText = "black has won :-)";
				else
					statusText = "No winner...";
			else
				statusText = "Next move: " + game.getPlayer(currState);
			if (searchMetrics != null)
				statusText += "    " + searchMetrics;
			statusBar.setText(statusText);
		}
	}
}
