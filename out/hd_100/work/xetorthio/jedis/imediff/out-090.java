package redis.clients.jedis.tests;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.junit.Before;
import org.junit.Test;

import redis.clients.jedis.DebugParams;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;
import redis.clients.jedis.tests.utils.JedisSentinelTestUtil;

public class JedisSentinelPoolTest extends JedisTestBase {
	private static final String MASTER_NAME = "mymaster";

	protected static HostAndPort master = HostAndPortUtil.getRedisServers()
			.get(2);
	protected static HostAndPort slave1 = HostAndPortUtil.getRedisServers()
			.get(3);
	protected static HostAndPort slave2 = HostAndPortUtil.getRedisServers()
<<<<<<< commits-hd_100/xetorthio/jedis/18652b906b27b4a795fc0af562831df912adbc0e-5f8e4948707bae0fd5f574e0f2c5556a39e1ad04/A.java
	    .get(4);
||||||| commits-hd_100/xetorthio/jedis/18652b906b27b4a795fc0af562831df912adbc0e-5f8e4948707bae0fd5f574e0f2c5556a39e1ad04/O.java
    	.get(4);
=======
			.get(4);
>>>>>>> commits-hd_100/xetorthio/jedis/18652b906b27b4a795fc0af562831df912adbc0e-5f8e4948707bae0fd5f574e0f2c5556a39e1ad04/B.java
	protected static HostAndPort sentinel1 = HostAndPortUtil
			.getSentinelServers().get(1);
	protected static HostAndPort sentinel2 = HostAndPortUtil
			.getSentinelServers().get(2);

	protected static Jedis masterJedis;
	protected static Jedis slaveJedis1;
	protected static Jedis slaveJedis2;
	protected static Jedis sentinelJedis1;
	protected static Jedis sentinelJedis2;

	protected Set<String> sentinels = new HashSet<String>();

	@Before
	public void setUp() throws Exception {

		// set up master and slaves
		masterJedis = new Jedis(master.getHost(), master.getPort());
		masterJedis.auth("foobared");
		masterJedis.slaveofNoOne();

		slaveJedis1 = new Jedis(slave1.getHost(), slave1.getPort());
		slaveJedis1.auth("foobared");
		slaveJedis1.slaveof(master.getHost(), master.getPort());

		slaveJedis2 = new Jedis(slave2.getHost(), slave2.getPort());
		slaveJedis2.auth("foobared");
		slaveJedis2.slaveof(master.getHost(), master.getPort());

		sentinels.add(sentinel1.toString());
		sentinels.add(sentinel2.toString());

		List<HostAndPort> slaves = new ArrayList<HostAndPort>();
		slaves.add(slave1);
		slaves.add(slave2);

		JedisSentinelTestUtil.waitForSentinelRecognizeRedisReplication(sentinel1, 
				MASTER_NAME, master, slaves);
		JedisSentinelTestUtil.waitForSentinelRecognizeRedisReplication(sentinel2, 
				MASTER_NAME, master, slaves);
		
		// No need to wait for sentinels to recognize each other
	}	

	@Test
	public void ensureSafeTwiceFailover() throws InterruptedException {
<<<<<<< commits-hd_100/xetorthio/jedis/18652b906b27b4a795fc0af562831df912adbc0e-5f8e4948707bae0fd5f574e0f2c5556a39e1ad04/A.java
	JedisSentinelPool pool = new JedisSentinelPool("mymaster", sentinels,
||||||| commits-hd_100/xetorthio/jedis/18652b906b27b4a795fc0af562831df912adbc0e-5f8e4948707bae0fd5f574e0f2c5556a39e1ad04/O.java
    	JedisSentinelPool pool = new JedisSentinelPool("mymaster", sentinels,
=======
		JedisSentinelPool pool = new JedisSentinelPool(MASTER_NAME, sentinels,
>>>>>>> commits-hd_100/xetorthio/jedis/18652b906b27b4a795fc0af562831df912adbc0e-5f8e4948707bae0fd5f574e0f2c5556a39e1ad04/B.java
<<<<<<< commits-hd_100/xetorthio/jedis/18652b906b27b4a795fc0af562831df912adbc0e-5f8e4948707bae0fd5f574e0f2c5556a39e1ad04/A.java
		new GenericObjectPoolConfig(), 1000, "foobared", 2);
||||||| commits-hd_100/xetorthio/jedis/18652b906b27b4a795fc0af562831df912adbc0e-5f8e4948707bae0fd5f574e0f2c5556a39e1ad04/O.java
    			new Config(), 1000, "foobared", 2);
=======
				new Config(), 1000, "foobared", 2);
>>>>>>> commits-hd_100/xetorthio/jedis/18652b906b27b4a795fc0af562831df912adbc0e-5f8e4948707bae0fd5f574e0f2c5556a39e1ad04/B.java

<<<<<<< commits-hd_100/xetorthio/jedis/18652b906b27b4a795fc0af562831df912adbc0e-5f8e4948707bae0fd5f574e0f2c5556a39e1ad04/A.java
	// perform failover
||||||| commits-hd_100/xetorthio/jedis/18652b906b27b4a795fc0af562831df912adbc0e-5f8e4948707bae0fd5f574e0f2c5556a39e1ad04/O.java
    	// perform failover
=======
		// perform failover
>>>>>>> commits-hd_100/xetorthio/jedis/18652b906b27b4a795fc0af562831df912adbc0e-5f8e4948707bae0fd5f574e0f2c5556a39e1ad04/B.java
<<<<<<< commits-hd_100/xetorthio/jedis/18652b906b27b4a795fc0af562831df912adbc0e-5f8e4948707bae0fd5f574e0f2c5556a39e1ad04/A.java
	doSegFaultMaster(pool);
||||||| commits-hd_100/xetorthio/jedis/18652b906b27b4a795fc0af562831df912adbc0e-5f8e4948707bae0fd5f574e0f2c5556a39e1ad04/O.java
    	doSegFaultMaster(pool);
=======
		doSegFaultMaster(pool);
>>>>>>> commits-hd_100/xetorthio/jedis/18652b906b27b4a795fc0af562831df912adbc0e-5f8e4948707bae0fd5f574e0f2c5556a39e1ad04/B.java

<<<<<<< commits-hd_100/xetorthio/jedis/18652b906b27b4a795fc0af562831df912adbc0e-5f8e4948707bae0fd5f574e0f2c5556a39e1ad04/A.java
	// perform failover once again
||||||| commits-hd_100/xetorthio/jedis/18652b906b27b4a795fc0af562831df912adbc0e-5f8e4948707bae0fd5f574e0f2c5556a39e1ad04/O.java
    	// perform failover once again
=======
		// perform failover once again
>>>>>>> commits-hd_100/xetorthio/jedis/18652b906b27b4a795fc0af562831df912adbc0e-5f8e4948707bae0fd5f574e0f2c5556a39e1ad04/B.java
<<<<<<< commits-hd_100/xetorthio/jedis/18652b906b27b4a795fc0af562831df912adbc0e-5f8e4948707bae0fd5f574e0f2c5556a39e1ad04/A.java
	doSegFaultMaster(pool);
||||||| commits-hd_100/xetorthio/jedis/18652b906b27b4a795fc0af562831df912adbc0e-5f8e4948707bae0fd5f574e0f2c5556a39e1ad04/O.java
    	doSegFaultMaster(pool);
=======
		doSegFaultMaster(pool);
>>>>>>> commits-hd_100/xetorthio/jedis/18652b906b27b4a795fc0af562831df912adbc0e-5f8e4948707bae0fd5f574e0f2c5556a39e1ad04/B.java

<<<<<<< commits-hd_100/xetorthio/jedis/18652b906b27b4a795fc0af562831df912adbc0e-5f8e4948707bae0fd5f574e0f2c5556a39e1ad04/A.java
	// you can test failover as much as possible
||||||| commits-hd_100/xetorthio/jedis/18652b906b27b4a795fc0af562831df912adbc0e-5f8e4948707bae0fd5f574e0f2c5556a39e1ad04/O.java
    	// you can test failover as much as possible
=======
		// you can test failover as much as possible
>>>>>>> commits-hd_100/xetorthio/jedis/18652b906b27b4a795fc0af562831df912adbc0e-5f8e4948707bae0fd5f574e0f2c5556a39e1ad04/B.java
<<<<<<< commits-hd_100/xetorthio/jedis/18652b906b27b4a795fc0af562831df912adbc0e-5f8e4948707bae0fd5f574e0f2c5556a39e1ad04/A.java
	// but you need to prepare additional slave per failover
||||||| commits-hd_100/xetorthio/jedis/18652b906b27b4a795fc0af562831df912adbc0e-5f8e4948707bae0fd5f574e0f2c5556a39e1ad04/O.java
    	// but you need to prepare additional slave per failover
=======
		// but you need to prepare additional slave per failover
>>>>>>> commits-hd_100/xetorthio/jedis/18652b906b27b4a795fc0af562831df912adbc0e-5f8e4948707bae0fd5f574e0f2c5556a39e1ad04/B.java
	}

	private void doSegFaultMaster(JedisSentinelPool pool)
<<<<<<< commits-hd_100/xetorthio/jedis/18652b906b27b4a795fc0af562831df912adbc0e-5f8e4948707bae0fd5f574e0f2c5556a39e1ad04/A.java
	    throws InterruptedException {
||||||| commits-hd_100/xetorthio/jedis/18652b906b27b4a795fc0af562831df912adbc0e-5f8e4948707bae0fd5f574e0f2c5556a39e1ad04/O.java
=======
		HostAndPort oldMaster = pool.getCurrentHostMaster();

>>>>>>> commits-hd_100/xetorthio/jedis/18652b906b27b4a795fc0af562831df912adbc0e-5f8e4948707bae0fd5f574e0f2c5556a39e1ad04/B.java
<<<<<<< commits-hd_100/xetorthio/jedis/18652b906b27b4a795fc0af562831df912adbc0e-5f8e4948707bae0fd5f574e0f2c5556a39e1ad04/A.java
	// jedis connection should be master
||||||| commits-hd_100/xetorthio/jedis/18652b906b27b4a795fc0af562831df912adbc0e-5f8e4948707bae0fd5f574e0f2c5556a39e1ad04/O.java
    	// jedis connection should be master
=======
		// jedis connection should be master
>>>>>>> commits-hd_100/xetorthio/jedis/18652b906b27b4a795fc0af562831df912adbc0e-5f8e4948707bae0fd5f574e0f2c5556a39e1ad04/B.java
<<<<<<< commits-hd_100/xetorthio/jedis/18652b906b27b4a795fc0af562831df912adbc0e-5f8e4948707bae0fd5f574e0f2c5556a39e1ad04/A.java
	Jedis jedis = pool.getResource();
||||||| commits-hd_100/xetorthio/jedis/18652b906b27b4a795fc0af562831df912adbc0e-5f8e4948707bae0fd5f574e0f2c5556a39e1ad04/O.java
    	Jedis jedis = pool.getResource();
=======
		Jedis jedis = pool.getResource();
>>>>>>> commits-hd_100/xetorthio/jedis/18652b906b27b4a795fc0af562831df912adbc0e-5f8e4948707bae0fd5f574e0f2c5556a39e1ad04/B.java
<<<<<<< commits-hd_100/xetorthio/jedis/18652b906b27b4a795fc0af562831df912adbc0e-5f8e4948707bae0fd5f574e0f2c5556a39e1ad04/A.java
	assertEquals("PONG", jedis.ping());
||||||| commits-hd_100/xetorthio/jedis/18652b906b27b4a795fc0af562831df912adbc0e-5f8e4948707bae0fd5f574e0f2c5556a39e1ad04/O.java
    	assertEquals("PONG", jedis.ping());
=======
		assertEquals("PONG", jedis.ping());
>>>>>>> commits-hd_100/xetorthio/jedis/18652b906b27b4a795fc0af562831df912adbc0e-5f8e4948707bae0fd5f574e0f2c5556a39e1ad04/B.java

<<<<<<< commits-hd_100/xetorthio/jedis/18652b906b27b4a795fc0af562831df912adbc0e-5f8e4948707bae0fd5f574e0f2c5556a39e1ad04/A.java
	try {
||||||| commits-hd_100/xetorthio/jedis/18652b906b27b4a795fc0af562831df912adbc0e-5f8e4948707bae0fd5f574e0f2c5556a39e1ad04/O.java
    	try {
=======
		try {
>>>>>>> commits-hd_100/xetorthio/jedis/18652b906b27b4a795fc0af562831df912adbc0e-5f8e4948707bae0fd5f574e0f2c5556a39e1ad04/B.java
<<<<<<< commits-hd_100/xetorthio/jedis/18652b906b27b4a795fc0af562831df912adbc0e-5f8e4948707bae0fd5f574e0f2c5556a39e1ad04/A.java
	    jedis.debug(DebugParams.SEGFAULT());
||||||| commits-hd_100/xetorthio/jedis/18652b906b27b4a795fc0af562831df912adbc0e-5f8e4948707bae0fd5f574e0f2c5556a39e1ad04/O.java
    		jedis.debug(DebugParams.SEGFAULT());
=======
			jedis.debug(DebugParams.SEGFAULT());
>>>>>>> commits-hd_100/xetorthio/jedis/18652b906b27b4a795fc0af562831df912adbc0e-5f8e4948707bae0fd5f574e0f2c5556a39e1ad04/B.java
<<<<<<< commits-hd_100/xetorthio/jedis/18652b906b27b4a795fc0af562831df912adbc0e-5f8e4948707bae0fd5f574e0f2c5556a39e1ad04/A.java
	} catch (Exception e) {
||||||| commits-hd_100/xetorthio/jedis/18652b906b27b4a795fc0af562831df912adbc0e-5f8e4948707bae0fd5f574e0f2c5556a39e1ad04/O.java
    	} catch (Exception e) {
=======
		} catch (Exception e) {
>>>>>>> commits-hd_100/xetorthio/jedis/18652b906b27b4a795fc0af562831df912adbc0e-5f8e4948707bae0fd5f574e0f2c5556a39e1ad04/B.java
<<<<<<< commits-hd_100/xetorthio/jedis/18652b906b27b4a795fc0af562831df912adbc0e-5f8e4948707bae0fd5f574e0f2c5556a39e1ad04/A.java
	}
||||||| commits-hd_100/xetorthio/jedis/18652b906b27b4a795fc0af562831df912adbc0e-5f8e4948707bae0fd5f574e0f2c5556a39e1ad04/O.java
    	}
=======
		}
>>>>>>> commits-hd_100/xetorthio/jedis/18652b906b27b4a795fc0af562831df912adbc0e-5f8e4948707bae0fd5f574e0f2c5556a39e1ad04/B.java

<<<<<<< commits-hd_100/xetorthio/jedis/18652b906b27b4a795fc0af562831df912adbc0e-5f8e4948707bae0fd5f574e0f2c5556a39e1ad04/A.java
	Thread.sleep(35000);
||||||| commits-hd_100/xetorthio/jedis/18652b906b27b4a795fc0af562831df912adbc0e-5f8e4948707bae0fd5f574e0f2c5556a39e1ad04/O.java
    	Thread.sleep(35000);
=======
		waitForFailover(pool, oldMaster);
>>>>>>> commits-hd_100/xetorthio/jedis/18652b906b27b4a795fc0af562831df912adbc0e-5f8e4948707bae0fd5f574e0f2c5556a39e1ad04/B.java

<<<<<<< commits-hd_100/xetorthio/jedis/18652b906b27b4a795fc0af562831df912adbc0e-5f8e4948707bae0fd5f574e0f2c5556a39e1ad04/A.java
	jedis = pool.getResource();
||||||| commits-hd_100/xetorthio/jedis/18652b906b27b4a795fc0af562831df912adbc0e-5f8e4948707bae0fd5f574e0f2c5556a39e1ad04/O.java
    	jedis = pool.getResource();
=======
		jedis = pool.getResource();
>>>>>>> commits-hd_100/xetorthio/jedis/18652b906b27b4a795fc0af562831df912adbc0e-5f8e4948707bae0fd5f574e0f2c5556a39e1ad04/B.java
<<<<<<< commits-hd_100/xetorthio/jedis/18652b906b27b4a795fc0af562831df912adbc0e-5f8e4948707bae0fd5f574e0f2c5556a39e1ad04/A.java
	assertEquals("PONG", jedis.ping());
||||||| commits-hd_100/xetorthio/jedis/18652b906b27b4a795fc0af562831df912adbc0e-5f8e4948707bae0fd5f574e0f2c5556a39e1ad04/O.java
    	assertEquals("PONG", jedis.ping());
=======
		assertEquals("PONG", jedis.ping());
>>>>>>> commits-hd_100/xetorthio/jedis/18652b906b27b4a795fc0af562831df912adbc0e-5f8e4948707bae0fd5f574e0f2c5556a39e1ad04/B.java
<<<<<<< commits-hd_100/xetorthio/jedis/18652b906b27b4a795fc0af562831df912adbc0e-5f8e4948707bae0fd5f574e0f2c5556a39e1ad04/A.java
	assertEquals("foobared", jedis.configGet("requirepass").get(1));
||||||| commits-hd_100/xetorthio/jedis/18652b906b27b4a795fc0af562831df912adbc0e-5f8e4948707bae0fd5f574e0f2c5556a39e1ad04/O.java
    	assertEquals("foobared", jedis.configGet("requirepass").get(1));
=======
		assertEquals("foobared", jedis.configGet("requirepass").get(1));
>>>>>>> commits-hd_100/xetorthio/jedis/18652b906b27b4a795fc0af562831df912adbc0e-5f8e4948707bae0fd5f574e0f2c5556a39e1ad04/B.java
<<<<<<< commits-hd_100/xetorthio/jedis/18652b906b27b4a795fc0af562831df912adbc0e-5f8e4948707bae0fd5f574e0f2c5556a39e1ad04/A.java
	assertEquals(2, jedis.getDB().intValue());
||||||| commits-hd_100/xetorthio/jedis/18652b906b27b4a795fc0af562831df912adbc0e-5f8e4948707bae0fd5f574e0f2c5556a39e1ad04/O.java
    	assertEquals(2, jedis.getDB().intValue());
=======
		assertEquals(2, jedis.getDB().intValue());
>>>>>>> commits-hd_100/xetorthio/jedis/18652b906b27b4a795fc0af562831df912adbc0e-5f8e4948707bae0fd5f574e0f2c5556a39e1ad04/B.java
	}

	private void waitForFailover(JedisSentinelPool pool, HostAndPort oldMaster) throws InterruptedException {
		HostAndPort newMaster = JedisSentinelTestUtil.waitForNewPromotedMaster(sentinel1, 
				MASTER_NAME, oldMaster);
		JedisSentinelTestUtil.waitForNewPromotedMaster(sentinel2, MASTER_NAME, oldMaster);
		JedisSentinelTestUtil.waitForSentinelsRecognizeEachOthers();
		waitForJedisSentinelPoolRecognizeNewMaster(pool, newMaster);
	}

	private void waitForJedisSentinelPoolRecognizeNewMaster(JedisSentinelPool pool,
			HostAndPort newMaster) throws InterruptedException {

		while (true) {
			String host = pool.getCurrentHostMaster().getHost();
			int port = pool.getCurrentHostMaster().getPort();

			if (host.equals(newMaster.getHost()) && port == newMaster.getPort())
				break;

			System.out.println("JedisSentinelPool's master is not yet changed, sleep...");

			Thread.sleep(1000);
		}
	}

}
