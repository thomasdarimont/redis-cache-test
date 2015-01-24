package gadams;

import java.util.HashMap;
import java.util.Map;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

@Repository("carRepository")
public class MockCarRepositoryImpl implements CarRepository {

	private Map<Integer, Car> cars = new HashMap<>();
	{
		cars.put(1, new Car(1, "Honda", "CR-V", 2005));
		cars.put(2, new Car(2, "Toyota", "Tacoma", 1998));
		cars.put(3, new Car(3, "Hyundai", "Sonata", 2015));
		cars.put(4, new Car(4, "Kia", "Optima", 2015));
	}
	
	@Override
	@Cacheable(value = "car")
	public Car get(int id) {
		return cars.get(id);
	}
	
	@Override
	@CachePut(value = "car", key="#car.id")
	public void insert(Car car) {
		cars.put(car.getId(), car);
	}
	
	@Override
	@CachePut(value = "car", key = "#car.id")
	public void update(Car car) {
		cars.put(car.getId(), car);
	}
	
	@Override
	@CacheEvict(value = "car")
	public void delete(int id) {
		cars.remove(id);
	}
}
