# 🦙ollama-gate
#### A web application for controlling access to your ollama models, provides convenient management of tokens and users.

## Get started🚀

### 1️⃣ First launch

Download ollama-gate.jar from releases or build application with gradle (output would be in /build/libs)
```shell
./gradlew bootJar
```
Then start application with --create-admin, that will create default admin user with username *admin* and password *admin* 
#### ⚠️You must have java 17 or greater
```shell
java -jar ollama-gate.jar --create-admin
```
Now open in browser localhost:8080 (if on server: *server_ip*:8080) Login into that **admin**, open **About** tab, and change password, __OR__ you can go to **Users** tab, create new admin, go to **About** page, logout, login to new admin account and delete old admin

### 2️⃣ Manage tokens

In this app, users can be: **USER** or **ADMIN**.\
**ADMIN**s can create, delete all users (except themselves) and create access tokens. When creating access token, admin must assign this token to some user (it can be anyone)
Admins can see every existing token and every existing user. When an administrator deletes a user, all tokens assigned to that user are also deleted\
**USER**s can only see tokens that assigned to them and delete those tokens

### 3️⃣ Access to Ollama

To access ollama, go to **API** page, and add your ollama's url & model (if you running ollama in same device, it will most likely be localhost:11434 and llama3.1)\
After that you can access and use your ollama with REST API, ⚠️dont forget to add authorization header to your requests: key = "Authorization" value = "_some_token_"\
To see endpoints documentation go to **About** page, and open swagger

## Examples✅

#### Curl example:
Request:
```shell
curl -X POST http://localhost:8080/api/generate \
     -H "Authorization: lFH8rOwcsI881r5R9hGlr9WFeZhm0LnLPHhwsWuPQVQ" \
     -d '{"prompt": "What is your name?"}'
```
Response:
```json
{
  "response": "I don't have a name, i am language-model to communicate with people."
}
```