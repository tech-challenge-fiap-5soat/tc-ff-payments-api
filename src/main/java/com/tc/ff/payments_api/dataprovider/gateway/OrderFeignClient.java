package com.tc.ff.payments_api.dataprovider.gateway;

import com.tc.ff.payments_api.common.enums.PaymentStatus;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "order-api", url = "${order-api.service.url}")
public interface OrderFeignClient {

//    @FeignClient(name = "orderService", url = "${order.service.url}")
//    public interface OrderClient {
//
//        @PatchMapping("/order/{id}/status/{status}")
//        void updateOrderStatus(@PathVariable("id") Long orderId, @PathVariable("status") String status);
//    }
    @RequestMapping(method = RequestMethod.PUT, value = "/order/{orderId}/status/{status}")
    void udatePaymentStatus(@PathVariable("orderId") String orderId, @PathVariable("status") PaymentStatus status);

}
