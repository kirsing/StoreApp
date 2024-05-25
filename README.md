# Store Application - Spring Boot Microservice Example(Eureka Server, Config Server, API Gateway, Services , Grafana Stack, RabbitMQ Redis, Resilience4j, Docker, Kubernetes)
![](/Users/kirylyudayeu/Downloads/Store APP .jpg)

# About the project
<ul style="list-style-type:disc">
  <li>This project is based Spring Boot Microservices with the usage of Docker and Kubernetes</li>
  <li>User can't register but login through auth service (provided by Okta) by user role (ADMIN or USER) through api gateway</li>
  <li>User can send any request to relevant service through api gateway with its bearer token</li>
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
      <td>authenticate/login</td>
      <td>Login for User and Admin</td>
      <td><a href="README.md#login">Info</a></td>
      <td></td>
      <td></td>
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
  <tr>
      <td>GET</td>
      <td>/payment/order/{order_id}</td>
      <td>Get Payment Details by Order Id</td>
      <td></td>
      <td></td>
      <td><a href="README.md#getPaymentDetailsByOrderId">Info</a></td>
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
    * PostgreSQL
* Redis
* Grafana Stack
* Docker Compose
* Google Jib
* Kubernetes
* RabbitMQ
* Junit
* Log4j2

## Valid Request Body

##### <a id="login"> Login for User and Admin
```
    http://localhost:9090/authenticate/login
    
    {
        "username" : "User",
        "password" : "User"
    }
    
    http://localhost:9090/authenticate/login
    
    {
        "username" : "UserAdmin",
        "password" : "UserAdmin"
    }
```

##### <a id="refreshtoken"> Refresh Token for User and Admin
```
    http://localhost:9090/authenticate/refreshtoken
    
    {
        "refreshToken" : ""
    }
```

##### <a id="addProduct"> Add Product
```
    http://localhost:9090/product
    
    {
        "name" : "Product 1",
        "price" : 100,
        "quantity" : 1
    }
    
    Bearer Token : User Token
```

##### <a id="placeorder"> Place Order
```
    http://localhost:9090/order/placeorder
    
    {
        "productId" : 1,
        "totalAmount" : 100,
        "quantity" : 1,
        "paymentMode" : "CASH"
    }
    
    Bearer Token : User Token
```

## Valid Request Params

##### <a id="reduceQuantityOfProduct">Reduce Quantity of Product
```
    http://localhost:9090/product/reduceQuantity/1?quantity=1
    
    Bearer Token : User Token
```

## Valid Request Params and Body

##### <a id="getProductById">Get Product By Id
```
    http://localhost:9090/product/{prodcutId}
    
    Bearer Token : User Token
```

##### <a id="deleteProductById">Delete Product By Id
```
    http://localhost:9090/product/{prodcutId}
    
    Bearer Token : Admin Token
```

##### <a id="deleteProductById">Delete Product By Id
```
    http://localhost:9090/order/{order_id}
    
    Bearer Token : User Token
```

##### <a id="getPaymentDetailsByOrderId">Get Payment Details by Order Id
```
    http://localhost:9090/payment/order/{order_id}
    
    Bearer Token : User Token
```

### 🔨 Run the App
<b>1 )</b> Download your project from this link `https://github.com/kirsing/StoreApp`

<b>2 )</b> Go to the project's home directory :  `cd StoreApp`

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