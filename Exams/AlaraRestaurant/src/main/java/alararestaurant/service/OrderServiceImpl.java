package alararestaurant.service;

import alararestaurant.constants.ImportConstants;
import alararestaurant.domain.dtos.orders.OrderImportDto;
import alararestaurant.domain.dtos.orders.OrderImportRootDto;
import alararestaurant.domain.dtos.orders.OrderItemImportDto;
import alararestaurant.domain.dtos.orders.OrderItemImportRootDto;
import alararestaurant.domain.entities.Employee;
import alararestaurant.domain.entities.Item;
import alararestaurant.domain.entities.Order;
import alararestaurant.domain.entities.OrderItem;
import alararestaurant.domain.enums.OrderType;
import alararestaurant.repository.EmployeeRepository;
import alararestaurant.repository.ItemRepository;
import alararestaurant.repository.OrderItemRepository;
import alararestaurant.repository.OrderRepository;
import alararestaurant.util.FileUtil;
import alararestaurant.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.io.StringReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    private OrderRepository orderRepository;
    private FileUtil fileUtil;
    private ValidationUtil validationUtil;
    private EmployeeRepository employeeRepository;
    private ItemRepository itemRepository;
    private OrderItemRepository orderItemRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, FileUtil fileUtil, ValidationUtil validationUtil, EmployeeRepository employeeRepository, ItemRepository itemRepository, OrderItemRepository orderItemRepository) {
        this.orderRepository = orderRepository;
        this.fileUtil = fileUtil;
        this.validationUtil = validationUtil;
        this.employeeRepository = employeeRepository;
        this.itemRepository = itemRepository;
        this.orderItemRepository = orderItemRepository;
    }

    @Override
    public Boolean ordersAreImported() {
        return this.orderRepository.count() > 0;
    }

    @Override
    public String readOrdersXmlFile() throws IOException {
        String relativePath = "files/orders.xml";
        return this.fileUtil.readFile(relativePath);
    }

    @Override
    public String importOrders() throws IOException, JAXBException {
        //•	The order dates will be in the format “dd/MM/yyyy HH:mm”.
        //•	If the order’s employee doesn’t exist, do not import the order.
        //•	If any of the order’s items do not exist, do not import the order.
        //•	Every employee will have a unique name
        String relativePath = "files/orders.xml";
        String fileContent = this.fileUtil.readFile(relativePath);

        JAXBContext jaxbContext = JAXBContext.newInstance(OrderImportRootDto.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        OrderImportRootDto orderImportRootDto =
                (OrderImportRootDto) unmarshaller.unmarshal(new StringReader(fileContent));

        OrderImportDto[] orderImportDtos = orderImportRootDto.getOrderImportDtos();
        StringBuilder resultFromImports = new StringBuilder();

        String dateFormat = "dd/MM/yyyy HH:mm";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);
        //Using the file orders.xml, import the data from the file into the database.
        // Print information about each imported object in the format described below.
        //If any of the model requirements is violated continue with the next entity.
        //Constraints
        //•	The order dates will be in the format “dd/MM/yyyy HH:mm”.
        //•	If the order’s employee doesn’t exist, do not import the order.
        //•	If any of the order’s items do not exist, do not import the order.
        //•	Every employee will have a unique name
        for (OrderImportDto orderImportDto : orderImportDtos) {
            if (!this.validationUtil.isValid(orderImportDto)) {
                resultFromImports.append(ImportConstants.INVALID_DATA).append(System.lineSeparator());
                continue;
            }

            boolean orderIsValid = true;
            String employeeName = orderImportDto.getEmployeeName();

            Employee employee = this.employeeRepository.findEmployeeByName(employeeName);
            if (employee == null) {
                resultFromImports.append(ImportConstants.INVALID_DATA).append(System.lineSeparator());
                continue;
            }

            OrderItemImportRootDto orderItemImportRootDto = orderImportDto.getOrderItemImportRootDto();
            OrderItemImportDto[] orderItemImportDtos = orderItemImportRootDto.getOrderItemImportDtos();

            List<OrderItem> orderItemList = new ArrayList<>();
            for (OrderItemImportDto orderItemImportDto : orderItemImportDtos) {
                String itemName = orderItemImportDto.getItemName();
                Item item = this.itemRepository.findItemByName(itemName);

                if (item == null) {
                    orderIsValid = false;
                    break;
                }

                OrderItem orderItem = new OrderItem();
                orderItem.setItem(item);
                orderItem.setQuantity(orderItemImportDto.getQuantity());
                orderItemList.add(orderItem);
            }

            if (!orderIsValid)
                continue;

            Order order = new Order();
            String customerName = orderImportDto.getCustomerName();
            order.setCustomer(customerName);

            String dateTime = orderImportDto.getDateTime();
            LocalDateTime localDateTime = LocalDateTime.parse(dateTime, formatter);
            order.setDateTime(localDateTime);

            order.setEmployee(employee);

            String orderTypeString = orderImportDto.getType();
            OrderType orderType = null;
            if (orderTypeString.toLowerCase().equals("forhere"))
                orderType = OrderType.FORHERE;
            else
                orderType = OrderType.TOGO;

            order.setType(orderType);

            this.orderRepository.saveAndFlush(order); //save to repo and give an id
            for (OrderItem orderItem : orderItemList) { //order has id and now can be added to order item
                orderItem.setOrder(order);
                this.orderItemRepository.saveAndFlush(orderItem);
            }

            resultFromImports.append(String.format("Order for %s on %s added",
                    order.getCustomer(), formatter.format(order.getDateTime())))
                    .append(System.lineSeparator());
        }

        return resultFromImports.toString().trim();
    }

    @Override
    public String exportOrdersFinishedByTheBurgerFlippers() {
        String positionName = "Burger Flipper";

        List<Object[]> employeeNamesAndOrders =
                this.orderRepository.getEmployeeNameAndOrders(positionName);

        StringBuilder resultExport = new StringBuilder();
        for (Object[] employeeNamesAndOrder : employeeNamesAndOrders) {
            String employeeName = (String)employeeNamesAndOrder[0];

            Order theOrder = (Order) employeeNamesAndOrder[1];
            Order[] orders = new Order[]{theOrder};

            resultExport.append("Name: ").append(employeeName).append(System.lineSeparator());
            resultExport.append("Orders: ").append(System.lineSeparator());

            for (Order order : orders) {
                resultExport.append("\tCustomer: ").append(order.getCustomer()).append(System.lineSeparator());
                resultExport.append("\tItems: ").append(System.lineSeparator());

                for (OrderItem orderItem : order.getOrderItems()) {
                    resultExport.append(this.orderItemFormat(orderItem)).append(System.lineSeparator());
                }

            }
        }
        return resultExport.toString();
    }

    private String orderItemFormat(OrderItem orderItem) {
        StringBuilder sb = new StringBuilder();
        sb.append("\t\tName: ").append(orderItem.getItem().getName()).append(System.lineSeparator());
        sb.append("\t\tPrice: ").append(orderItem.getItem().getPrice()).append(System.lineSeparator());
        sb.append("\t\tQuantity: ").append(orderItem.getQuantity()).append(System.lineSeparator());

        return sb.toString();
    }
}
