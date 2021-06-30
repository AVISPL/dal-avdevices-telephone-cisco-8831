package com.insightsystems.dal.cisco;

import com.avispl.symphony.api.dal.dto.monitor.ExtendedStatistics;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

public class CP8831Test {
    CP8831 cp8831 = new CP8831();
    @Before
    public void before() throws Exception {
        cp8831.setHost("127.0.0.1");
        cp8831.setProtocol("http");
        cp8831.setPort(5500);
        cp8831.init();
    }

    @Test
    public void checkExtendedStatistics() throws Exception {
        ExtendedStatistics extendedStatistics = (ExtendedStatistics) cp8831.getMultipleStatistics().get(0);
        Map<String,String> stats = extendedStatistics.getStatistics();

        Assert.assertNotEquals("",stats.get("MacAddress"));
        Assert.assertNotEquals("",stats.get("Hostname"));
        Assert.assertNotEquals("",stats.get("PhoneDn"));
        Assert.assertNotEquals("",stats.get("Version"));
        Assert.assertNotEquals("",stats.get("HardwareRevision"));
        Assert.assertNotEquals("",stats.get("SerialNumber"));
        Assert.assertNotEquals("",stats.get("ModelNumber"));
        Assert.assertNotEquals("",stats.get("MessageWaiting"));
        Assert.assertNotEquals("",stats.get("TimeZone"));
        Assert.assertNotEquals("",stats.get("SystemFreeMemory"));
        Assert.assertNotEquals("",stats.get("JavaHeapFreeMemory"));
        Assert.assertNotEquals("",stats.get("JavaPoolFreeMemory"));
        Assert.assertNotEquals("",stats.get("TimeDate"));
    }
}
