package com.tc.ff.payments_api.common.constants;

import static java.time.LocalDateTime.now;
import static org.apache.commons.lang3.RandomUtils.nextInt;
import static org.apache.commons.lang3.RandomUtils.nextLong;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class TestsConstants {

    public static final Long RANDOM_ID = nextLong();

    public static final String RANDOM_ORDER_ID = UUID.randomUUID().toString();

    public static final BigDecimal RANDOM_VALUE = new BigDecimal(nextInt(1, 10000));
    public static final LocalDateTime LOCAL_DATE_NOW = now();
}
