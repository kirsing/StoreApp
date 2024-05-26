# Store Application - Spring Boot Microservice Example(Eureka Server, Config Server, API Gateway, Services , Grafana Stack, RabbitMQ Redis, Resilience4j, Docker, Kubernetes)
![](https://github.com/kirsing/StoreApp/assets/86996284/1cc2f1e2-4cc5-4804-a2b4-b665a8ba764a)

# About the project
<ul style="list-style-type:disc">
  <li>This project is based Spring Boot Microservices with the usage of Docker and Kubernetes</li>
  <li>Customer can't register himself but login through auth service (provided by Okta) by user role (ADMIN or USER) through api gateway</li>
  <li>Customer can send any request to relevant service through api gateway with its bearer token</li>
</ul>

8 services whose name are shown below have been devised within the scope of this project.

- Config Server
- Eureka Server
- API Gateway
- Auth Service
- Order Service
- Payment Service
- Product Service
- Message Service

### Explore Rest APIs

<table style="width:100%">
  <tr>
      <th>Method</th>
      <th>Url</th>
      <th>Description</th>
      <th>Valid Request Body</th>
      <th>Valid Request Params</th>
      <th>Valid Request Params and Body</th>
  </tr>
  <tr>
      <td>POST</td>
      <td>/product</td>
      <td>Add Product</td>
      <td><a href="README.md#addproduct">Info</a></td>
      <td></td>
      <td></td>
  </tr>
  <tr>
      <td>GET</td>
      <td>/product/{product_id}</td>
      <td>Get Product By Id</td>
      <td></td>
      <td></td>
      <td><a href="README.md#getProductById">Info</a></td>
  </tr>
  <tr>
      <td>PUT</td>
      <td>/reduceQuantity/{product_id}?quantity={quantity_value}</td>
      <td>Reduce Quantity of Product</td>
      <td></td>
      <td><a href="README.md#reduceQuantityOfProduct">Info</a></td>
      <td></td>
  </tr>
  <tr>
      <td>POST</td>
      <td>/order/placeorder</td>
      <td>Place Order</td>
      <td><a href="README.md#placeOrder">Info</a></td>
      <td></td>
      <td></td>
  </tr>
  <tr>
      <td>GET</td>
      <td>/order/{order_id}</td>
      <td>Get Order By Id</td>
      <td></td>
      <td></td>
      <td><a href="README.md#getOrderById">Info</a></td>
  </tr>

</table>

### Used Dependencies
* Core
    * Spring
        * Spring Boot
        * Spring Boot Test (Junit, WireMock and Mockito)
        * Spring Security
        * Spring Web
            * RestTemplate
            * FeignClient
        * Spring Data
            * Spring Data JPA
        * Spring Cloud
            * Spring Cloud Gateway Server
            * Spring Cloud Config Server
            * Spring Cloud Config Client
    * Netflix
        * Eureka Server
        * Eureka Client
* Database
    * MySQL
* Redis
* Grafana Stack
* Docker Compose
* Google Jib
* Kubernetes
* RabbitMQ
* Junit
* Log4j2

## Valid Request Body

##### <a id="login"> Login for Customer and Admin through Okta UI
```
    Customer Role
    http://localhost:8072/authenticate/login
    
        username : customer@store.com,
        password : user12345!
    
    Admin Role
    http://localhost:8072/authenticate/login
        username : admin@admin.com,
        password : store.123456789!
```

##### <a id="addProduct"> Add Product
```
    http://localhost:8072/services/products/product
    
    {
        "name" : "Google Pixel 8",
        "price" : 1000,
        "quantity" : 15
    }
    
    Bearer Token : User (ADMIN ROLE) Token
```

##### <a id="placeorder"> Place Order
```
    http://localhost:8072/services/orders/order/placeOrder
    
    {
        "productId" : 1,
        "totalAmount" : 100,
        "quantity" : 1,
        "paymentMode" : "CASH"
    }
    
    Bearer Token : User (CUSTOMER Role) Token
```

## Valid Request Params

##### <a id="reduceQuantityOfProduct">Reduce Quantity of Product
```
    http://localhost:8072/services/products/product/reduceQuantity/1?quantity=1
    
    Bearer Token : User Token
```

## Valid Request Params and Body

##### <a id="getProductById">Get Product By Id
```
    http://localhost:8072/services/products/product/{productId}
    
    Bearer Token : Both Token
```

##### <a id="getPaymentDetailsByOrderId">Get Payment Details by Order Id
```
    http://localhost:8072/services/payments/payment/order/{orderId}
    
    Bearer Token : Both Token
```
##### <a id="getOrderDetailsByOrderId">Get Order Details by Order Id
```
    http://localhost:8072/services/orders/order/{orderId}"
    
    Bearer Token : Both Token
```



### Additional information
#### If Docker:
Access to see queses by RabbitMQ:
```
http://localhost:15672/
user: guest
password: guest
```

Access to see metrics, tracing and so on, Grafana Dashboard
```
http://localhost:3000/
user: admin
password: admin
```

#### If K8s:
Access to see queses by RabbitMQ:
```
kubectl port-forward --namespace default svc/rabbitmq 5672:5672 echo "URL : http://127.0.0.1:15672/"
```

Access to see metrics, tracing and so on, Grafana Dashboard
```
echo "Browse to http://127.0.0.1:8080" 
kubectl port-forward svc/grafana 3000:3000 &
echo "User: admin" 
echo "Password: $(kubectl get secret grafana-admin --namespace default -o jsonpath="{.data.GF_SECURITY_ADMIN_PASSWORD}" | base64 -d)"
```


## 🔨 Run the App
<b>1 )</b> Download your project from this link `https://github.com/kirsing/StoreApp`

<b>2 )</b> Go to the project's home directory :  `cd StoreApp-main`

<b>Docker</b>

<b>1 )</b> Install <b>Docker Desktop</b>. Here is the installation <b>link</b> :

<b>2 )</b> Run all <b>Containers</b> through this command shown below under main folder

<b>5 )</b> Send request to any service by using request collections under <b>postman_collection</b> 

<b>Kubernetes</b>

<b>1 )</b> Install <b>minikube</b> or enable Kubernetes on Docker Desktop

<b>2 )</b> Install <b>Helm</b>  `https://helm.sh/docs/intro/quickstart/`

<b>3 )</b> Go to the directory :  `cd ~/StoreApp/helm/env`

<b>4 )</b> Go to the project's home directory :  `cd StoreApp`

<b>5 )</b> Install all <b>Helm Charts</b> using **Helm** through this command 
`helm install <your-application-name> dev-env`