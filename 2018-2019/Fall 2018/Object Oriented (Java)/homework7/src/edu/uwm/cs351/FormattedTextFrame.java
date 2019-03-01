package edu.uwm.cs351;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Iterator;

import javax.swing.JFrame;
import javax.swing.JPanel;

public abstract class FormattedTextFrame extends JFrame {
	/**
	 * Keep Eclipse happy
	 */
	private static final long serialVersionUID = 1L;

	public FormattedTextFrame() {
		setContentPane(new ContentPane());
	}

	protected class ContentPane extends JPanel {

		/**
		 * Keep Eclipse Happy
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void paint(Graphics g) {
			super.paint(g);
			g.setColor(Color.BLACK);
			int y=0;
			Line line = new Line(g,this.getWidth());
			Iterator<Word> it = getWords();
			while (it.hasNext()) {
				Word w = it.next();
				if (w == null) {
					y += line.getHeight();
					line.draw(y, false);
					line.clear();
				} else if (!line.add(w)) {
					y += line.getHeight();
					line.draw(y, true);
					if (y > this.getHeight()) return; // no point drawing
					line.clear();
					if (!line.add(w)) {
						throw new AssertionError("should be able to add to a blank line.");
					}
				}
			}
			y += line.getHeight();
			line.draw(y, false);
		}
	}

	protected abstract Iterator<Word> getWords();
}
