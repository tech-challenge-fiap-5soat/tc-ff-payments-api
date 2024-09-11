package com.tc.ff.payments_api.dataprovider.gateway;

import com.tc.ff.payments_api.common.enums.PaymentStatus;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "order-api", url = "${feign.client.config.order-api.service.url}")
public interface OrderFeignClient {
    @RequestMapping(method = RequestMethod.PUT, value = "/order/{orderId}/status/{status}")
    void updatePaymentStatus(@PathVariable("orderId") String orderId, @PathVariable("status") PaymentStatus status);
}
