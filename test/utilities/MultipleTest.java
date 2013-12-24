package utilities;

import static org.junit.Assert.assertEquals;

import java.awt.geom.Point2D;

import org.junit.Test;

public class MultipleTest {
	
	@Test
	public void multiplo_de_64_mas_cercano_a_107() {
		int number = Multiple.findNumberCloserTo(107, 64);
		assertEquals(64, number);
	}

	@Test
	public void multiplo_de_64_mas_cercano_a_577() {
		int number = Multiple.findNumberCloserTo(577, 64);
		assertEquals(576, number);
	}
	
	@Test
	public void multiplo_de_64_mas_cercano_a_menos_33() {
		int number = Multiple.findNumberCloserTo(-33, 64);
		assertEquals(0, number);
	}
	
	@Test
	public void multiplo_de_64_mas_cercano_a_menos_243() {
		int number = Multiple.findNumberCloserTo(-243, 64);
		assertEquals(-192, number);
	}
	
	@Test
	public void multiplo_de_0_mas_cercano_a_menos_243() {
		int number = Multiple.findNumberCloserTo(-243, 0);
		assertEquals(0, number);
	}

	/***********************************************************************/
	
	@Test
	public void multiplo_del_punto_64_32_mas_cercano_al_punto_569_288() {
		Point2D.Double point = Multiple.findPointCloserTo(new Point2D.Double(569,288), new Point2D.Double(64,32));
		assertEquals(new Point2D.Double(512,288), point);
	}
	
	@Test
	public void multiplo_del_punto_64_32_mas_cercano_al_punto_569_menos_288() {
		Point2D.Double point = Multiple.findPointCloserTo(new Point2D.Double(569,-288), new Point2D.Double(64,32));
		assertEquals(new Point2D.Double(512,-288), point);
	}
	
	@Test
	public void multiplo_del_punto_0_0_mas_cercano_al_punto_569_menos_288() {
		Point2D.Double point = Multiple.findPointCloserTo(new Point2D.Double(569,-288), new Point2D.Double(0,0));
		assertEquals(new Point2D.Double(0,0), point);
	}
}
