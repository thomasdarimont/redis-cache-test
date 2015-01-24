package gadams;

public interface CarRepository {
	
	Car get(int id);
	void insert(Car car);
	void update(Car car);
	void delete(int id);
	
}
