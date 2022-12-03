package com.programmingtechie.orderservice.service;

import com.programmingtechie.orderservice.dto.InventoryResponse;
import com.programmingtechie.orderservice.dto.OrderDto;
import com.programmingtechie.orderservice.dto.OrderLineItemsDto;
import com.programmingtechie.orderservice.dto.OrderRequest;
import com.programmingtechie.orderservice.model.Order;
import com.programmingtechie.orderservice.model.OrderLineItems;
import com.programmingtechie.orderservice.repository.OrderRepository;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final WebClient.Builder webClientBuilder;
    private final Tracer tracer;

    public OrderService(OrderRepository orderRepository, WebClient.Builder webClientBuilder, Tracer tracer) {
        this.orderRepository = orderRepository;
        this.webClientBuilder = webClientBuilder;
        this.tracer = tracer;
    }

    public String placeOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        var orderLineItems = orderRequest.getOrderLineItemsDtoList()
                .stream()
                .map(this::mapToDto).toList();

        order.setOrderLineItemsList(orderLineItems);

        var skuCodes = order.getOrderLineItemsList().stream()
                .map(OrderLineItems::getSkuCode)
                .toList();


        Span inventoryServiceSpan = tracer.nextSpan().name("InventoryServiceSpan");

        try (Tracer.SpanInScope spanInScope = tracer.withSpan(inventoryServiceSpan)) {
            // Call inventory service and place order if product is in
            var inventoryResponses = webClientBuilder.build().get()
                    .uri("http://inventory-service/api/inventory/isinstock/",
                            uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
                    .retrieve()
                    .bodyToMono(InventoryResponse[].class)
                    .block();

            assert inventoryResponses != null;
            var result = Arrays.stream(inventoryResponses)
                    .allMatch(InventoryResponse::getIsInStock);

            if (Boolean.TRUE.equals(result)) {
                orderRepository.save(order);
                return "Order places successfully";
            } else {
                throw new IllegalArgumentException("product is not in the stock");
            }
        } finally {
            inventoryServiceSpan.end();
        }
    }

    public List<OrderDto> getOrders() {
        return orderRepository.findAll().stream()
                .map(this::mapToOrderDto).toList();
    }

    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());

        return orderLineItems;
    }

    private OrderDto mapToOrderDto(Order order) {

        return OrderDto.builder()
                .id(order.getId())
                .orderNumber(order.getOrderNumber())
                .build();
    }
}
