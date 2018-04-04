#include <ESP8266WiFi.h>
#include <WiFiClient.h>
#include <ESP8266WebServer.h>
#include <ESP8266mDNS.h>
#include <ArduinoJson.h>
#include <ESP8266HTTPClient.h>

const char* ssid = "linksysVK";
const char* password = "1234512345";

IPAddress ipAddress;
const String successResponse = "OK";
const String argumentError = "Error, no arguments or arguments missing";
const String pinStateError = "Invalid pin state, it can be 1 or 0";

const String nameOfDevice = "Test MCU";
const String typeOfDevice = "SW";
const String area = "Moja soba 1";

const int numberOfLights = 3;
const bool changeInLights = false;

const String lightNames[numberOfLights] = {"Svetlo 1", "Svetlo 2", "Svetlo 3"};
const int lightPins[numberOfLights] = {12,13, 14};
const int lightIds[numberOfLights] = {1, 2, 3};

ESP8266WebServer server(80);

void switchPin(int pinNumber,int pinState){
  digitalWrite(pinNumber, pinState);
}


/*void handleSwitch(){
    unsigned int pinNumber = server.arg(0).toInt();
    int state = server.arg(1).toInt();
    state = ((state == 1)? HIGH : LOW);
    switchPin(pinNumber, state);
    server.send(200, "text/plain", successResponse);
}*/

/*void handleSwitch(){
  Serial.println("Recieved post request on /switch");
  Serial.println(server.args());

  StaticJsonBuffer<200> jsonBuffer;
  JsonObject& root = jsonBuffer.parseObject(server.arg("plain"));
  char jsonChar[100];
  root.printTo((char*)jsonChar, root.measureLength() + 1);
  Serial.print(jsonChar);
  Serial.println("Done with json");
    
  if(server.args() > 0){
    Serial.print("Recieved arguments: ");
    Serial.println(server.args());
    if(server.hasArg("pin") && server.hasArg("state")){
      int wantedPin = server.arg("pin").toInt();
      int wantedState = server.arg("state").toInt();

      if(wantedState == 1 || wantedState == 0){
        wantedState = ((wantedState == 1)? HIGH : LOW);
        switchPin(wantedPin, wantedState);
        server.send(200, "text/plain", successResponse); 
      }
      else{
        server.send(400, "text/plain", pinStateError);
      }
    }
    else{
      server.send(400, "text/plain", argumentError);
    }
  }
  else{
    server.send(400, "text/plain", "No arguments recieved");
  }
  
}*/

void setupLights(){

  for(int i = 0; i < numberOfLights; i++){
    pinMode(lightPins[i], OUTPUT);
    digitalWrite(lightPins[i], LOW);
  }
  
}

String generateJSON(String mac_address, String ip){

  DynamicJsonBuffer JSONbuffer;
  JsonObject& JSONencoder = JSONbuffer.createObject();

  JSONencoder["mac_address"] = mac_address;
  JSONencoder["ip"] = ip;
  JSONencoder["name"] = "Test MCU";
  JSONencoder["typeOfDevice"] = "SW";
  JSONencoder["area"] = area;
  JsonArray& switches = JSONencoder.createNestedArray("switches");
  
  for(int i = 0; i < numberOfLights;i++){

    JsonObject& device = switches.createNestedObject();
    device["name"] = lightNames[i];
    device["state"] = 0;
    device["pin"] = lightPins[i];
    device["id"] = lightIds[i];
  }

  //char JSONmessageBuffer[500];
  //JSONencoder.prettyPrintTo(JSONmessageBuffer,sizeof(JSONmessageBuffer));
  String JSONmessageBuffer; 
  JSONencoder.printTo(JSONmessageBuffer);
  
  Serial.println(JSONmessageBuffer);

  return JSONmessageBuffer;
  
}

void sendJSON(String json, String path){

  HTTPClient http;  
  http.begin("http://192.168.1.101:3000/" + path);
  http.addHeader("Content-Type", "application/json");

  int httpCode = http.POST(json);

  if(httpCode > 0){
    Serial.printf("Http code: %d\n", httpCode);

    if(httpCode == HTTP_CODE_OK){
      String result = http.getString();
      Serial.printf("Answer recieved %s\n", result.c_str());
    }
  }
  else{
    Serial.printf("Http failed , error: %s\n", http.errorToString(httpCode).c_str());
  }
  http.end();
}

void connectWithServer(String mac_address, String ip){

  sendJSON(generateJSON(mac_address, ip), "setupNodeMCU");
  
}




void handleSwitch(){
  Serial.println("Handeling switch");
  if(server.args() > 0){
    if(server.hasArg("plain")){
       StaticJsonBuffer<200> jsonBuffer;
       JsonObject& root = jsonBuffer.parseObject(server.arg("plain")); 
       int pin = root["pin"];
       int state = root["state"];

       if(pin && (state == 0 || state == 1)){
        switchPin(pin,state);
        server.send(200, "text/plain", successResponse); 
       }
       else{
        server.send(400, "text/plain", argumentError);
       }
    }
    else{
      server.send(400, "text/plain", argumentError);
    }
  }
  else{
    server.send(400, "text/plain", "No arguments recieved");
  }
  
}

void handleMultipleSwitch(){
  Serial.println("Handling multiple switch");
  if(server.args() > 0){
    if(server.hasArg("plain")){
      DynamicJsonBuffer jsonBuffer;
      JsonObject& root = jsonBuffer.parseObject(server.arg("plain"));
      int state = root["state"];
      int amount = root["amount"];
      JsonArray& lights = root["pins"];

      Serial.println("Recoeved arguments: " + server.arg("plain"));
      
      if(true){
        for(int i = 0; i < lights.size(); i++){
//          Serial.println("Writing to light: " + String(lights[i]) +" ,State: "+ String(state));
          switchPin(lights[i], state);
        
        }
        server.send(200,"text/plain" , successResponse);
      }
      else{
        Serial.println("lights unavailable");
      }
    }
    else{
    Serial.println(" plain argument not found");
    server.send(400,"text/plain", argumentError);
    }
  }
  else{
    Serial.println("Recieved 0 arguments");
    server.send(400,"text/plain", argumentError);
  }
  
  
}


void handleRoot(){
  Serial.print("Number of arguments: ");
  Serial.println(server.args());
  if(server.hasArg("state")){
    String message = "Body: \n";
    message+= server.arg("state");
    Serial.println(message);
  }
  else{
    Serial.print(server.arg(0));
  }
  server.send(200, "text/plain", "Hello world");
}

void setup(void){
  //Set pin modes for the pins in use
 // pinMode(12, OUTPUT);
  //pinMode(13, OUTPUT);
  //pinMode(14, OUTPUT);
  //pinMode(9, OUTPUT);
  setupLights();
 // digitalWrite(13, 0);
  Serial.begin(115200);
  //Connect to Wifi network -->
  Serial.println("Wifi begin");
  WiFi.begin(ssid, password);

  while(WiFi.status() != WL_CONNECTED){
    delay(500);
    Serial.print(".");
  }

  ipAddress = WiFi.localIP();
  Serial.print("Connected to ");
  Serial.println(ssid);
  Serial.print("IP address: ");
  Serial.println(WiFi.localIP());
  Serial.println(ipAddress);

  if (MDNS.begin("esp8266")) {
    Serial.println("MDNS responder started");
  }


  //Server routes -->

  server.on("/", handleRoot);
  server.on("/switch", handleSwitch);
  server.on("/switchMultiple", handleMultipleSwitch);
  server.onNotFound([](){
    server.send(200, "plain/text", "Welcome into the unknown");
  });

  server.begin();
  Serial.println("HTTP server started");

  connectWithServer(WiFi.macAddress(),WiFi.localIP().toString());
}


void loop(void){
  server.handleClient();
}