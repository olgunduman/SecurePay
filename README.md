# SecurePay

### Proje Adı : SecurePay

### Kullanılan Teknolojiler : 
- Java 1.8
- Java Spring Boot
- Maven
- H2 Db (In memory)
- -Lombok
- ORM Tools (Hibernate)
- JUnit5
- -Swagger (Project Documentation)

  ### Customer Service Açıklaması :

  Customer Service'de 2 adet public methodlarımız bulunmaktadır.

  -createCustomer : Bu method içerisinde customer ekleme işlemini yaparız. Valid kontrol olarak kayıtlı email adresi varsa
  ona uygun bir hata mesajı döner.
  -getCustomerWithCardInfo : Bu method bir "long customerId" parametresi alır. Method içerisinde parametre olarak geçilen customer var mı yok mu kontrolü yapılır.
  Bu kontrol sonrası ilgili customer'ın  "name,mail,ve kayıtlı card'ları' getirilir.

  ### Card Service açıklaması :

  Card Service'de 1 adet public methodumuz var.
  - createCardForCustomer : Bu method 2 adet parametre alır. "customerId,cardNumber". Method içerisinde customer ve card number için valid kontrolleri yapılır.
 
  ### Payment Service açıklaması :
  Payment Service'de 2 adet public method bulunmaktadır.
  -createPayment : Bu method 2 parametre alıyor. "customerId,Amount". Bu iki parametre için valid kontroller yapılmaktadır.
  kontroller sonrası bize ödemeyi yapan customerId,Amount ve ödemenin ne zaman gerçekleştiğini dönen bir sonuc gelir.

  ### Projenenin açıklaması :
  Proje de Bir müşteri ekleme işlemi gerçekleşmektedir. İlgili müşteriye card ekleme işlemi ve ödeme işlemlerini gerçekleştiren işlemler vardır.
  Proje de müşterinin ödeme işlemleri, belirli tarihler arasında gerçekleşen ödemelerin listesi gibi işlemlerimiz vardır. Proje monotoloik bir yapı olup, katmanlı bir
  mimariye uygun olarak tasarlanmıştır. Proje içerisinde servisler için birim testleri yazılmıştır. Aşağıdaki resimler de test sonuçları mevcuttur.

  ### Birim Test :
  ![Birim_Testi](https://github.com/olgunduman/SecurePay/assets/72493657/ffcfe8b4-8e7d-48f5-b16f-6f562d490626)

### Veritabanı :

Veritabanı olarak H2 in memory db kullanılmıştır.
* veritabanı için ;
   - jdbc url : jdbc:h2:mem:mainDB
   - username :sa
   - password :sa
 link : http://localhost:8085/h2-console/login.jsp

### veritabanı örnek kayıtlar :
![customer_db](https://github.com/olgunduman/SecurePay/assets/72493657/2a96046a-a210-4d75-b7d5-d3d81d6690e5)

![card_db](https://github.com/olgunduman/SecurePay/assets/72493657/e31c9eac-a169-49a1-8a0e-aa8195e1ff1f)

![payment_db](https://github.com/olgunduman/SecurePay/assets/72493657/af8fc0d4-812e-49a9-a637-3c5953dbf382)

### Swagger :
link : http://localhost:8085/swagger-ui/index.html#/

![swagger_resim](https://github.com/olgunduman/SecurePay/assets/72493657/120f65d3-ab83-4c00-97ab-bf5b85954707)




    
  
