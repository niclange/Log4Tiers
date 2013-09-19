package org.log4perf;

/**
 * Created with IntelliJ IDEA.
 * User: nicolas
 * Date: 16/09/13
 * Time: 14:23
 * To change this template use File | Settings | File Templates.
 */

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;

import java.util.concurrent.ConcurrentHashMap;

/**
 * WARNING DISABLE THIS ASPECT IN NORMAL PRODUCTION MODE
 *
 * */
public class PerfLogger {

    /** logger log4j */
    private static final Logger LOGGER = Logger.getLogger(PerfLogger.class);

    /** sperator */
    private static final String SEPARATOR = ";";

    /** name of the application top tier, usually service tier (ignore case) */
    private static final String TOP_TIERS = "Service";


    /** PARAMETRers_SEPARATOR */
    private static final String PARAM_SEPARATOR = ",";


    /** threadIdHash */
    private static ConcurrentHashMap<Long, Long> threadIdHash = new ConcurrentHashMap<Long, Long>();



    /** Type of line to log (configured in springcontext file
     * Service is a special tag, it's processed as upper tier.
     * ex : DAO - Service - Business */
    private String logTag;

    /** logParameters */
    private boolean logParameters = false;

    /**
     * logAround
     *
     * @param joinPoint
     *            joinPoint
     * @return  proceed
     * @throws Throwable
     *             exception
     */
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {

        Object[] args = joinPoint.getArgs();

        //method name to intercept
        String name = joinPoint.getSignature().toShortString();
        long threadId = getThreadIdHashed();

        StringBuilder sb = new StringBuilder(SEPARATOR + Long.toString(threadId) + SEPARATOR);

        sb.append(logTag).append(SEPARATOR);
        sb.append(name);
        if (this.isLogParameters()) {

            boolean firstTime = true;
            // method args
            for (Object arg : args) {
                if(!firstTime)
                    sb.append(PARAM_SEPARATOR);
                sb.append("'").append(arg).append("'");
                firstTime = false;
            }

            sb.append(SEPARATOR);
        }


        long dateDebut = System.nanoTime();

        Object ret = joinPoint.proceed();

        long diff = (System.nanoTime() - dateDebut) / 1000000;

        //perf logs level is INFO, you can change it
        LOGGER.info(sb.toString() + Long.toString(diff));

        return ret;
    }

    /**
     * get the unique if of the top call using the thread id
     *
     * @return unique top call id
     */
    private long getThreadIdHashed() {
        long threadId = Thread.currentThread().getId();
        Long threadHash =  threadIdHash.get(threadId);
        if (threadHash != null) {
            if (TOP_TIERS.equalsIgnoreCase(this.logTag)) {
                threadIdHash.put(threadId, threadIdHash.get(threadId) * 3 + threadId);
            }
            threadId = threadId * threadIdHash.get(threadId);

        } else {
            threadIdHash.put(threadId, threadId * 17);
        }
        return threadId;
    }

    /**
     * getteur
     *
     * @return the logParameters
     */
    public boolean isLogParameters() {
        return logParameters;
    }

    /**
     * setteur
     *
     * @param logParameters
     *            the logParameters to set
     */
    public void setLogParameters(boolean logParameters) {
        this.logParameters = logParameters;
    }

    /**
     * getteur
     *
     * @return the logTag
     */
    public String getLogTag() {
        return logTag;
    }

    /**
     * setteur
     *
     * @param logTag
     *            the logTag to set
     */
    public void setLogTag(String logTag) {
        this.logTag = logTag;
    }

}
