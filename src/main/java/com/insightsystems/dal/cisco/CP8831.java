package com.insightsystems.dal.cisco;

import com.avispl.symphony.api.dal.dto.monitor.ExtendedStatistics;
import com.avispl.symphony.api.dal.dto.monitor.Statistics;
import com.avispl.symphony.api.dal.monitor.Monitorable;
import com.avispl.symphony.dal.communicator.HttpCommunicator;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Cisco CP8831 Phone Device Adapter
 * Company: Insight Systems
 * @author Jayden Loone (@JaydenLInsight)
 * @version 0.2
 *
 * No control possible with this device.
 *
 * Monitored Statistic:
 *  - MAC Address
 *  - Hostname
 *  - Phone Directory Number
 *  - Firmware Version
 *  - Hardware Revision
 *  - Serial Number
 *  - Model Number
 *  - Messages Waiting
 *  - Device Timezone
 *  - System Free Memory
 *  - Java Heap Free Memory
 *  - Java Pool Free Memory
 *  - Time and Date
 */
public class CP8831 extends HttpCommunicator implements Monitorable {
    public static Pattern MacAddressPattern = Pattern.compile("MAC Address.+?<B>([^<]+)</B>");
    public static Pattern HostnamePattern = Pattern.compile("Host Name.+?<B>([^<]+)</B>");
    public static Pattern PhoneDnPattern = Pattern.compile("Phone DN.+?<B>([^<]+)</B>");
    public static Pattern VersionPattern = Pattern.compile("Version.+?<B>([^<]+)</B>");
    public static Pattern HardwareRevisionPattern = Pattern.compile("Hardware Revision.+?<B>([^<]+)</B>");
    public static Pattern SerialNumberPattern = Pattern.compile("Serial Number.+?<B>([^<]+)</B>");
    public static Pattern ModelNumberPattern = Pattern.compile("Model Number.+?<B>([^<]+)</B>");
    public static Pattern MessageWaitingPattern = Pattern.compile("Message Waiting.+?<B>([^<]+)</B>");
    public static Pattern TimeZonePattern = Pattern.compile("Time Zone.+?<B>([^<]+)</B>");
    public static Pattern SystemFreeMemoryPattern = Pattern.compile("System Free Memory.+?<B>([^<]+)</B>");
    public static Pattern JavaHeapFreeMemoryPattern = Pattern.compile("Java Heap Free Memory.+?<B>([^<]+)</B>");
    public static Pattern JavaPoolFreeMemoryPattern = Pattern.compile("Java Pool Free Memory.+?<B>([^<]+)</B>");
    public static Pattern timePattern = Pattern.compile("Time.+?<B>([^<]+)</B>");
    public static Pattern datePattern = Pattern.compile("Date.+?<B>([^<]+)</B>");
    
    protected void authenticate() {}

    @Override
    public List<Statistics> getMultipleStatistics() throws Exception {
        final ExtendedStatistics extStats = new ExtendedStatistics();
        final Map<String,String> stats = new HashMap<>();

        final String res = doGet("/");

        stats.put("MacAddress",regexFind(res,MacAddressPattern));
        stats.put("Hostname",regexFind(res,HostnamePattern));
        stats.put("PhoneDn",regexFind(res,PhoneDnPattern));
        stats.put("Version",regexFind(res,VersionPattern));
        stats.put("HardwareRevision",regexFind(res,HardwareRevisionPattern));
        stats.put("SerialNumber",regexFind(res,SerialNumberPattern));
        stats.put("ModelNumber",regexFind(res,ModelNumberPattern));
        stats.put("MessageWaiting",regexFind(res,MessageWaitingPattern));
        stats.put("TimeZone",regexFind(res,TimeZonePattern));
        stats.put("SystemFreeMemory",regexFind(res,SystemFreeMemoryPattern));
        stats.put("JavaHeapFreeMemory",regexFind(res,JavaHeapFreeMemoryPattern));
        stats.put("JavaPoolFreeMemory",regexFind(res,JavaPoolFreeMemoryPattern));

        stats.put("TimeDate",regexFind(res,timePattern) + " " + regexFind(res,datePattern));

        extStats.setStatistics(stats);
        return Collections.singletonList(extStats);
    }

    /**
     * Finds a regular expression within a String and returns the first group
     * @param sourceString String to be searched
     * @param regex Regular expression to search for in string
     * @return First group matching the regular expression or an empty string if not found
     */
    private String regexFind(String sourceString,Pattern regex) {
        Matcher matcher = regex.matcher(sourceString);
        return matcher.find() ? matcher.group(1) : "";
    }
}
