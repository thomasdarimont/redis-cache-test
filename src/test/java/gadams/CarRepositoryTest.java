package gadams;

import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

@ContextConfiguration(classes=Application.class)
public class CarRepositoryTest extends AbstractJUnit4SpringContextTests {
	
	@Autowired
	private CarRepository carRepository;
	
	@Autowired
	private RedisTemplate<?, ?> redisTemplate;
	
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
		for (int i = 0; i < 12; i++) {
			log.debug("update(4)");
			car4.setModel("" + ++x);
			carRepository.update(car4);
			Thread.sleep(1000);
		}

		final byte[] zsetKey = "car~keys".getBytes();
		final Long carKeysSize = redisTemplate.execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection)
					throws DataAccessException {
				return connection.zCard(zsetKey);
			}});
		log.debug("car~keys size: " + carKeysSize);
		
		Set<byte[]> carKeys = redisTemplate.execute(new RedisCallback<Set<byte[]>>() {

			@Override
			public Set<byte[]> doInRedis(RedisConnection connection)
					throws DataAccessException {
				return connection.zRange(zsetKey, 0, carKeysSize);
			}});
		
		for (final byte[] carKey : carKeys) {
			boolean keyExists = redisTemplate.execute(new RedisCallback<Boolean>() {

				@Override
				public Boolean doInRedis(RedisConnection connection)
						throws DataAccessException {
					return connection.exists(carKey);
				}});
			log.debug("key " + new String(carKey) + " exists: " + keyExists);
		}
	}

}
