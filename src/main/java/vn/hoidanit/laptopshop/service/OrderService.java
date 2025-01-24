package vn.hoidanit.laptopshop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import vn.hoidanit.laptopshop.domain.Order;
import vn.hoidanit.laptopshop.domain.OrderDetail;
import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.repository.OrderDetailRepository;
import vn.hoidanit.laptopshop.repository.OrderRepository;

@Service
public class OrderService {
    // DI
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;

    public OrderService(OrderRepository orderRepository,
            OrderDetailRepository orderDetailRepository) {
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
    }

    public Page<Order> getAllOrder(Pageable page) {
        return this.orderRepository.findAll(page);
    }

    public Order getOrderById(long id) {
        return this.orderRepository.findById(id);
    }

    public void handleDeleteOrder(long id) {
        Order order = new Order();
        order.setId(id);
        List<OrderDetail> orderDetails = this.orderDetailRepository.findOrderDetailByOrder(order);

        for (OrderDetail od : orderDetails) {
            this.orderDetailRepository.deleteById(od.getId());
        }
        this.orderRepository.deleteById(id);
    }

    public void handleUpdateOrder(Order od) {
        Order orderCurrant = this.getOrderById(od.getId());
        orderCurrant.setStatus(od.getStatus());

        this.orderRepository.save(orderCurrant);
    }

    public List<Order> getOrderByUser(User user) {
        return this.orderRepository.findOrdersByUser(user);
    }
}
