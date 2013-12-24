package utilities;

import static org.junit.Assert.assertEquals;

import java.awt.geom.Point2D;

import org.junit.Test;

public class TileWalkTest {
	
	Point2D.Double sol;
	
	private void resolve(String direction, int xOrigin, int yOrigin, double steps,double xDest, double yDest){
		sol = TileWalk.walkTo(direction,new Point2D.Double(xOrigin,yOrigin),steps);
		assertEquals(new Point2D.Double(xDest, yDest), sol);
	}
	
	@Test
	public void no_caminar_a_ninguna_direccion() {
		resolve("non", 1, 1, 1, 1, 1);
	}

	@Test
	public void no_caminar_a_ninguna_direccion_cuando_pasos_0() {
		resolve("N", 1, 1, 0, 1, 1);
	}
	
	@Test
	public void no_caminar_a_ninguna_direccion_cuando_no_entiende_la_direccion() {
		resolve("NWD", 1, 1, 1, 1, 1);
	}
	
	@Test
	public void caminar_al_norte_oeste_un_paso() {
		resolve("NW", 1, 1, 1, 0, 1);
	}
	
	@Test
	public void caminar_al_norte_oeste_0_punto_5_pasos() {
		resolve("NW", 1, 1, 0.5, 0.5, 1);
	}
	
	@Test
	public void caminar_al_norte_este_un_paso() {
		resolve("NE", 1, 1, 1, 1, 0);
	}
	
	@Test
	public void caminar_al_norte_este_5_pasos() {
		resolve("NW", 6, 3, 5, 1, 3);
	}
	
	@Test
	public void caminar_al_sur_este_un_paso() {
		resolve("SE", 1, 1, 1, 2, 1);
	}
	
	@Test
	public void caminar_al_sur_oeste_un_paso() {
		resolve("SW", 1, 1, 1, 1, 2);
	}
	
	@Test
	public void caminar_al_norte_un_paso() {
		resolve("N", 1, 1, 1, 0, 0);
	}
	
	@Test
	public void caminar_al_norte_0_punto_5_pasos() {
		resolve("N", 1, 1, 0.5, 0.5, 0.5);
	}
	
	@Test
	public void caminar_al_este_un_paso() {
		resolve("E", 1, 1, 1, 2, 0);
	}
	
	@Test
	public void caminar_al_este_0_punto_5_pasos() {
		resolve("E", 1, 1, 0.5, 1.5, 0.5);
	}
	
	@Test
	public void caminar_al_sur_un_paso() {
		resolve("S", 1, 1, 1, 2, 2);
	}
	
	@Test
	public void caminar_al_oeste_un_paso() {
		resolve("W", 1, 1, 1, 0, 2);
	}
	
	@Test
	public void no_caminar_cuando_x_menor_0() {
		resolve("NW", 1, 1, 2, 1, 1);
	}
	
	@Test
	public void no_caminar_cuando_y_menor_0() {
		resolve("NE", 1, 1, 2, 1, 1);
	}
	
}
