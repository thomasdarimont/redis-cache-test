package gadams;

public interface CarRepository {
	
	Car get(int id);
	Car insert(Car car);
	Car update(Car car);
	void delete(int id);
	
}
