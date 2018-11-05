# Parasut Rest Api (V4) Java Client

This is the java client library purely written using apache http client for [parasut api](https://apidocs.parasut.com/) **v4**. [Parasut](https://www.parasut.com/) is the most commonly used, cloud based pre-accounting tool in Turkey. 

#### Note 
This client api currenty  supports below functionalities :

 - 	get/refresh oauth token,
 - create contact
 - create product
 - create sales invoice 


## Installation

### Required Java Version :  Java 1.8 or newer

### Maven users
Add this library to your projects build file (pom.xml) 

    <dependency>
      <groupId>io.github.easikoglu</groupId>
      <artifactId>parasut-java-client-api</artifactId>
      <version>1.0.0</version>
      <classifier>javadoc</classifier>
    </dependency>
    
### Build From Source 
With your teminal window run below command in the root folder of the project ( where pom.xml located).
	
    mvn clean install

## Usage 
 This client api follows builder design pattern useful structure. 
 In below I try to explain some of the use cases, but for detailed information take a look at the test cases under src/test folder. 
### Before Starting
Before starting using api, you should configure client properties,such as :

 - ClientID,ClientSecret, ApiVersion(default v4), password, username.
 
 You can use test.config.properties(under /src/test/resources/)  file as an example required parameters to use this client api. 

    clientId=YOUR_CLIENT_ID  
    clientSecret=YOUR_CLIENT_SECRET  
    redirectUri=YOUR_REDIRECT_URI - DEFAULT :urn:ietf:wg:oauth:2.0:oob  
    username=YOUR_USERNAME  
    password=YOUR_PASSWORD  
    companyId=YOUR_COMPANYID  
    baseUrl: https://api.parasut.com  
    apiVersion=/v4

 
 
### Get Token 
to get starting using api.parasut.com/v4 you have to first get token information. then use this token in the **Authorization** header with **Beaerer** keyword in each request.

    ParasutGetToken.get(httpOptions, ParasutAuthPasswordRequest.builder()  
            .password(password)  
            .username(username)  
            .clientId(httpOptions.getApiKey())  
            .clientSecret(httpOptions.getSecretKey())  
            .redirectUri(redirectUri)  
            .grant_type(ParasutGrantType.PASSWORD.getValue()).build());
      
### Refresh Token

   Token can be invalidated in a given time period, you can check that time in the GetToken response. So to be able to get new refreshed token you can use refresh token request. 

     ParasutGetToken parasutRefreshedToken = ParasutGetToken  
            .refresh(httpOptions, ParasutAuthRefreshRequest.builder()  
                    .refresh_token(parasutGetToken.getRefresh_token())  
                    .clientId(httpOptions.getApiKey())  
                    .clientSecret(httpOptions.getSecretKey())  
                    .redirectUri(redirectUri)  
                    .grant_type(ParasutGrantType.REFRESH_TOKEN.getValue()).build());


### Create Contact
Before creating a sales_invoice in parasut api you need to create and get contact id on parasut api first. After that request you can map returning id (parasutContactId) with your user id to prevent to create duplicate records on parasut api.

    ParasutContact parasutContact = ParasutContact.create(httpOptions, ParasutContactRequest.builder()  
                    .accountType(ParasutContactAccountType.CUSTOMER.getValue())  
                    .email("test@parasut.com")  
                    .name("test parasut")  
                    .build(),  
      ParasutSimpleRequest.builder()  
                    .companyId(companyId)  
                    .token(BEARER + parasutGetToken.getAccess_token())  
                    .build()  
    );

### Create Product
Even you have your product catalog in house application, you need to create your product copy on parasut system with not-detailed product information. After you create related product on parasut you need to map your product id with parasutProductId. 
  
    ParasutProduct parasutProduct = ParasutProduct.create(httpOptions, ParasutProductRequest.builder()  
                    .code("testProductCode")  
                    .buyingPrice(new BigDecimal(89.9))  
                    .name("testProductName")  
                    .build(),  
      ParasutSimpleRequest.builder()  
                    .companyId(companyId)  
                    .token(BEARER + parasutGetToken.getAccess_token())  
                    .build());


### Create Invoice
After you successfully created product and contact information on parasut system now you ready to create sales_invoice via parasut api endpoint. 
All you need to do gather api related informations as in example below : 

    ParasutInvoice parasutInvoice = ParasutInvoice.create(httpOptions, ParasutInvoiceRequest.builder()  
                    .contactId(parasutContact.getData().getId())  
                    .attributes(ParasutInvoiceAttributes.builder()  
                            .invoiceId(1)  
                            .issueDateInDateFormat(LocalDateTime.now())  
                            .billingAddress("billingAddress")  
                            .city("istanbul")  
                            .orderDateInDateFormat(LocalDateTime.now())  
                            .build())  
                    .details(generateInvoiceDetailList(parasutProduct))  
                    .build(),  
      ParasutSimpleRequest.builder()  
                    .companyId(companyId)  
                    .token(BEARER + parasutGetToken.getAccess_token())  
                    .build());

    private ArrayList<ParasutInvoiceDetails> generateInvoiceDetailList(ParasutProduct parasutProduct) {  
        return new ArrayList<ParasutInvoiceDetails>() {{  
      
      
            add(ParasutInvoiceDetails.builder()  
                    .unitPrice(new BigDecimal(89.0)) //price without vat value  
      .quantity(1)  
                    .productId(parasutProduct.getData().getId())  
                    .build());  
      
      }};  
    }
