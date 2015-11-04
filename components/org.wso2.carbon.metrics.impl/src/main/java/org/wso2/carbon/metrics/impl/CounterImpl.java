/*
 * Copyright 2014-2015 WSO2 Inc. (http://wso2.org)
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.wso2.carbon.metrics.impl;

import org.wso2.carbon.metrics.impl.internal.MetricServiceValueHolder;
import org.wso2.carbon.metrics.manager.Counter;
import org.wso2.carbon.metrics.manager.Level;
import org.wso2.carbon.metrics.manager.Metric;
import org.wso2.carbon.metrics.manager.MetricUpdater;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation class wrapping {@link com.codahale.metrics.Counter} metric
 */
public class CounterImpl extends AbstractMetric implements Counter, MetricUpdater {

    private com.codahale.metrics.Counter counter;
    private List<Counter> affected;

    public CounterImpl(Level level, String name, String path, String identifier, com.codahale.metrics.Counter counter) {
        super(level, name, path, identifier);
        this.counter = counter;
        this.affected = new ArrayList<Counter>();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.wso2.carbon.metrics.manager.Counter#inc()
     */
    @Override
    public void inc() {
        if (isEnabled()) {
            counter.inc();
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see org.wso2.carbon.metrics.manager.Counter#incAll()
     */
    @Override
    public void incAll() {
        if (isEnabled()) {
            counter.inc();
            for (Counter c : this.affected) {
                c.inc();
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.wso2.carbon.metrics.manager.Counter#inc(long)
     */
    @Override
    public void inc(long n) {
        if (isEnabled()) {
            counter.inc(n);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see org.wso2.carbon.metrics.manager.Counter#incAll(long)
     */
    @Override
    public void incAll(long n) {
        if (isEnabled()) {
            counter.inc(n);
            for (Counter c : this.affected) {
                c.inc(n);
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.wso2.carbon.metrics.manager.Counter#dec()
     */
    @Override
    public void dec() {
        if (isEnabled()) {
            counter.dec();
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see org.wso2.carbon.metrics.manager.Counter#decAll()
     */
    @Override
    public void decAll() {
        if (isEnabled()) {
            counter.dec();
            for (Counter c : this.affected) {
                c.dec();
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.wso2.carbon.metrics.manager.Counter#dec(long)
     */
    @Override
    public void dec(long n) {
        if (isEnabled()) {
            counter.dec(n);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see org.wso2.carbon.metrics.manager.Counter#decAll(long)
     */
    @Override
    public void decAll(long n) {
        if (isEnabled()) {
            counter.dec(n);
            for (Counter c : this.affected) {
                c.dec(n);
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.wso2.carbon.metrics.manager.Counter#getCount()
     */
    @Override
    public long getCount() {
        return counter.getCount();
    }

    /*
     * (non-Javadoc)
     *
     * @see org.wso2.carbon.metrics.manager.MetricUpdater#updateAffectedMetrics(String)
     */
    @Override
    public void updateAffectedMetrics(String path) {
        affected.clear();
        super.setPath(path);
        List<Metric> affectedMetrics = MetricServiceValueHolder.getMetricServiceInstance().getAffectedMetrics(getLevel(), getName(), path, getIdentifier());
        for (Metric metric : affectedMetrics) {
            affected.add((Counter) metric);
        }
    }

}
