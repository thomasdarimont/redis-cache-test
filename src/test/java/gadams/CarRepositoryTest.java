package gadams;

import gadams.Application;
import gadams.Car;
import gadams.CarRepository;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

@ContextConfiguration(classes=Application.class)
public class CarRepositoryTest extends AbstractJUnit4SpringContextTests {
	
	@Autowired
	private CarRepository carRepository;
	
	private Log log = LogFactory.getLog(CarRepositoryTest.class);
	
	@Test
	public void test() throws Exception {
		for (int i = 1; i <= 4; i++) {
			log.debug("get(" + i + ")");
			carRepository.get(i);
		}
		
		log.debug("get(4)");
		Car car4 = carRepository.get(4);
		int x = 0;
		while (true) {
			log.debug("update(4)");
			car4.setModel("" + ++x);
			carRepository.update(car4);
			Thread.sleep(1000);
		}
	}

}
