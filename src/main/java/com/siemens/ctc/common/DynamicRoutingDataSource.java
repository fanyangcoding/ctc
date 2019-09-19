package com.siemens.ctc.common;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DynamicRoutingDataSource extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
        Object object = DatabaseContextHolder.getDatabaseType();
        return object;
    }
}
