package com.e_commerce.e_commerce.service;

import com.e_commerce.e_commerce.configuration.ApiResponse;
import com.e_commerce.e_commerce.constant.OrderStatus;
import com.e_commerce.e_commerce.dto.requests.OrderRequest;
import com.e_commerce.e_commerce.dto.response.CartResponse;
import com.e_commerce.e_commerce.dto.response.OrderResponse;
import com.e_commerce.e_commerce.dto.response.PagedResponse;
import com.e_commerce.e_commerce.dto.response.ProductResponse;
import com.e_commerce.e_commerce.entity.*;
import com.e_commerce.e_commerce.exception.AppException;
import com.e_commerce.e_commerce.exception.ErrorCode;
import com.e_commerce.e_commerce.mapper.OrderItemMapper;
import com.e_commerce.e_commerce.mapper.OrderMapper;
import com.e_commerce.e_commerce.repository.AddressRepository;
import com.e_commerce.e_commerce.repository.OrderRepository;
import com.e_commerce.e_commerce.repository.UserRepository;
import com.e_commerce.e_commerce.utils.PageUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderService {

    OrderMapper orderMapper;
    UserRepository userRepository;
    AddressRepository addressRepository;
    OrderItemMapper orderItemMapper;
    OrderRepository orderRepository;

    public OrderResponse createOrder(OrderRequest request) {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userId = jwt.getClaim("id");

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        Address address = addressRepository.findById(request.getAddressId())
                .orElseThrow(() -> new AppException(ErrorCode.ADDRESS_NOT_FOUND));

        Order order = orderMapper.toEntity(request);
        order.setUser(user);
        order.setAddress(address);
        order.setStatus(OrderStatus.PENDING);

        List<OrderItem> orderItems = request.getItems().stream()
                .map(itemReq -> {
                    OrderItem item = orderItemMapper.toEntity(itemReq);
                    item.setOrder(order);
                    return item;
                }).toList();

        order.setOrderItems(orderItems);

        BigDecimal total = orderItems.stream()
                .map(i -> i.getPrice().multiply(BigDecimal.valueOf(i.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        order.setTotalAmount(total);

        Order savedOrder = orderRepository.save(order);

        return orderMapper.toResponse(savedOrder);
    }

    public OrderResponse updateOrder(String orderId, OrderRequest request) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));

        if (order.getStatus() != OrderStatus.PENDING) {
            throw new AppException(ErrorCode.ORDER_CANNOT_BE_UPDATED);
        }

        Address address = addressRepository.findById(request.getAddressId())
                .orElseThrow(() -> new AppException(ErrorCode.ADDRESS_NOT_FOUND));
        order.setAddress(address);

        List<OrderItem> updatedItems = request.getItems().stream()
                .map(itemReq -> {
                    OrderItem item = orderItemMapper.toEntity(itemReq);
                    item.setOrder(order);
                    return item;
                }).toList();

        order.setOrderItems(updatedItems);

        BigDecimal total = updatedItems.stream()
                .map(i -> i.getPrice().multiply(BigDecimal.valueOf(i.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        order.setTotalAmount(total);

        Order updatedOrder = orderRepository.save(order);
        return orderMapper.toResponse(updatedOrder);
    }

    public void cancelOrder(String orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));

        if (order.getStatus() != OrderStatus.PENDING) {
            throw new AppException(ErrorCode.ORDER_CANNOT_BE_CANCELLED);
        }

        order.setStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);
    }

    public OrderResponse getOrderDetail(String orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));

        return orderMapper.toResponse(order);
    }

    public List<OrderResponse> getOrdersByUser() {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userId = jwt.getClaim("id");

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        return orderRepository.findByUser(user)
                .stream()
                .map(orderMapper::toResponse)
                .toList();
    }

    public PagedResponse<OrderResponse> getAllOrders(String status, String userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));

        Specification<Order> spec = (root, query, cb) -> cb.conjunction();

        if (status != null && !status.isEmpty()) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("status"), OrderStatus.valueOf(status.toUpperCase()))
            );
        }

        if (userId != null && !userId.isEmpty()) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("user").get("id"), userId)
            );
        }


        Page<Order> pageData = orderRepository.findAll(spec, pageable);
        Page<OrderResponse> responsePage = pageData.map(orderMapper::toResponse);

        return PageUtils.buildPagedResponse(responsePage);
    }
}
