## Passport Java Client ![semver 2.0.0 compliant](http://img.shields.io/badge/semver-2.0.0-brightgreen.svg?style=flat-square)
If you're integrating Passport with a Java application, this library will speed up your development time.

For additional information and documentation on Passport refer to [https://www.inversoft.com](https://www.inversoft.com).


## Downloading
Released versions can be downloaded from the Savant Repository.
 
 * http://savant.inversoft.org/com/inversoft/passport/passport-java-client/

# Maven 
```xml
<dependency>
  <groupId>com.inversoft.passport</groupId>
  <artifactId>passport-java-client</artifactId>
  <version>1.13.1</version>
</dependency>
```

## Building with Savant
**Note:** This project uses the Savant build tool. To compile using using Savant, follow these instructions:

```bash
$ mkdir ~/savant
$ cd ~/savant
$ wget http://savant.inversoft.org/org/savantbuild/savant-core/1.0.0/savant-1.0.0.tar.gz
$ tar xvfz savant-1.0.0.tar.gz
$ ln -s ./savant-1.0.0 current
$ export PATH=$PATH:~/savant/current/bin/
```

Then, perform an integration build of the project by running:
```bash
$ sb int
```

For more information, checkout [savantbuild.org](http://savantbuild.org/).

### Examples Usages:

#### Build the Client

```java
String apiKey = "5a826da2-1e3a-49df-85ba-cd88575e4e9d";
PassportClient client = new PassportClient(apiKey, "http://localhost:9011");
```

#### Login a user

```java
String applicationId = "68364852-7a38-4e15-8c48-394eceafa601";

LoginRequest request = new LoginRequest(applicationId, "joe@inversoft.com", null, "abc123");
ClientResponse<LoginResponse, Errors> result = client.login(request);
if (!result.wasSuccessful()) {
 // Error
}

// Hooray! Success
```
