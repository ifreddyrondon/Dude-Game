package com.spantons.gameStagesMenus;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import org.imgscalr.Scalr;

import com.spantons.gameStages.GameStagesManager;
import com.spantons.gameStages.StagesMenus;
import com.spantons.magicNumbers.FontPath;
import com.spantons.magicNumbers.ImagePath;
import com.spantons.magicNumbers.SoundPath;
import com.spantons.main.GamePanel;
import com.spantons.singleton.FontCache;
import com.spantons.singleton.ImageCache;
import com.spantons.singleton.SoundCache;
import com.spantons.tileMap.Background;

public class MenuStage extends StagesMenus {

	private Background bg;
	private BufferedImage tricycle;
	private BufferedImage all;
	private BufferedImage bloodyHand;
	
	private int currentChoice = 0;
	private String[] choices = { "Jugar", "Ayuda", "Salir" };
	private String footer = "Copyright © 2013 Wasting Time For Game C.A Todos los derechos reservados.";

	private Color titleColor;
	private Font titleFont;
	private Font choicesFont;
	private Font footerFont;
	private FontMetrics fm;
	private Rectangle2D r;

	/****************************************************************************************/
	public MenuStage(GameStagesManager _gsm) {
		gsm = _gsm;

		try {
			bg = new Background(ImagePath.BACKGROUND_MENU, 0, true);
			bg.setVector(-0.1, 0);
			tricycle = ImageCache.getInstance().getImage(ImagePath.BACKGROUND_TRICYCLE);
			tricycle = Scalr.resize(tricycle, 365);
			all = ImageCache.getInstance().getImage(ImagePath.BACKGROUND_ALL);
			all = Scalr.resize(all, 300);
			bloodyHand = ImageCache.getInstance().getImage(ImagePath.BACKGROUND_BLOODY_HAND);
			
			titleColor = Color.BLACK;
			titleFont = FontCache.getInstance().getFont(FontPath.FONT_SIXTY); 
			titleFont = titleFont.deriveFont(Font.PLAIN, 90);

			choicesFont = FontCache.getInstance().getFont(FontPath.FONT_HORRENDO);
			choicesFont = choicesFont.deriveFont(Font.PLAIN, 30);
			footerFont = new Font("Arial", 8, 12);

			
			
			SoundCache.getInstance().getSound(SoundPath.MUSIC_HORROR_MOVIE_AMBIANCE).loop();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/****************************************************************************************/
	@Override
	public void update() {
		bg.update();
	}
	/****************************************************************************************/
	@Override
	public void draw(Graphics2D g) {
		bg.draw(g);
		
		g.drawImage(bloodyHand,200,200, null);
		g.drawImage(all,0,
				GamePanel.RESOLUTION_HEIGHT - 300, null);
		g.drawImage(tricycle, 
				GamePanel.RESOLUTION_WIDTH - tricycle.getWidth(),
				105, null);

		// Dibujar titulo
		g.setColor(titleColor);
		g.setFont(titleFont);
		fm = g.getFontMetrics();

		String stringTime = "Dead Room";
		r = fm.getStringBounds(stringTime, g);
		int x = (GamePanel.RESOLUTION_WIDTH - (int) r.getWidth()) / 2;
		int y = (GamePanel.RESOLUTION_HEIGHT - (int) r.getHeight()) / 2 - 160;
		g.drawString(stringTime, x, y);

		// Dibujar menu de opciones
		y = y + 110;
		g.setFont(choicesFont);
		for (int i = 0; i < choices.length; i++) {
			fm = g.getFontMetrics();
			r = fm.getStringBounds(choices[i], g);
			x = (GamePanel.RESOLUTION_WIDTH - (int) r.getWidth()) / 2
					+ fm.getDescent();
			
			if (i == currentChoice)
				g.setColor(Color.BLACK);
			else
				g.setColor(Color.LIGHT_GRAY);

			g.drawString(choices[i], x, y + i * 40);
		}
		
		g.setFont(footerFont);
		g.setColor(Color.LIGHT_GRAY);
		g.drawString(footer, 
				GamePanel.RESOLUTION_WIDTH - 438, 
				GamePanel.RESOLUTION_HEIGHT - 20);

	}
	/****************************************************************************************/
	private void select() {
		if (currentChoice == 0) 
			gsm.setStage(GameStagesManager.LEVEL_1_STAGE_1);
		else if (currentChoice == 1) 
			gsm.setStage(GameStagesManager.HELP_STAGE);
		else if (currentChoice == 2) {
			SoundCache.getInstance().closeAllSound();
			System.exit(0);
		}
			
	}

	/****************************************************************************************/
	@Override
	public void keyPressed(int k) {
		SoundCache.getInstance().getSound(SoundPath.SFX_SCRATCH).play();
		
		if (k == KeyEvent.VK_ENTER) {
			select();
		} else if (k == KeyEvent.VK_UP) {
			currentChoice--;
			if (currentChoice == -1) {
				currentChoice = choices.length - 1;
			}
		} else if (k == KeyEvent.VK_DOWN) {
			currentChoice++;
			if (currentChoice == choices.length) {
				currentChoice = 0;
			}
		}
	}

	@Override
	public void keyReleased(int k) {
		// TODO Auto-generated method stub
		
	}
	
}
