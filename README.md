# coolpay-backend-demo
Coolpay Integration API

Scenario
----------

Coolpay is a new company that allows to easily send money to friends through their API.
 
You work for Fakebook, a successful social network. You’ve been tasked to integrate Coolpay inside Fakebook. A/B tests show that users prefer to receive money than pokes!
 
You can find Coolpay documentation here: http://docs.coolpayapi.apiary.io/
 
You will write a small app that uses Coolplay API in a language of your choice. The app should be able do the following:
 
- Authenticate to Coolpay API
- Add recipients
- Send them money
- Check whether a payment was successful

## Architecture
Maven, Jersey, Java 8, Tomcat 8, Gson

## Deploy
Create the war file that will be deployed under Tomcat.
 ```
 <root project>/mvn clean install
 ```
## Test the app
I used postman as HTTP client for testing the API
The API tested have been the following:
- http://localhost:8080/coolpay-backend-demo/webapi/services/login
- http://localhost:8080/coolpay-backend-demo/webapi/services/createRecipient/{token}
- http://localhost:8080/coolpay-backend-demo/webapi/services/recipients/{token}
- http://localhost:8080/coolpay-backend-demo/webapi/services/recipients/{token}/{name}
- http://localhost:8080/coolpay-backend-demo/webapi/services/createPayment/{token}
- http://localhost:8080/coolpay-backend-demo/webapi/services/payments/{token}

## TODO
 - Implement Tests 
 - Implement front end (I was thinking to do that with Angular JS)
