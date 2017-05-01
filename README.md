## Requester

Requester is a simple JSON to Object HTTP library for Android.
This allows you to quickly translate JSON requests/responses to a HTTP & HTTPS server into Java classes you define.

## Usage

An example of the library using the `RequestBuilder` we can create a `GetRequest` which returns a basic GSON `JsonObject`.
Preferably the `context` passed to the `RequestWorker` should be the `getBaseContext()` for the activity.

```java
RequestWorker worker = RequestWorker.getInstance(context);

RequestBuilder.get(JsonObject.class)
    .setHeaders(worker.getDefaultHeaders())
    .setUrl("http://echo.jsontest.com/key/value")
    .setListener((JsonObject response) -> {
        String value = response.get("key").getAsString();
        Log.i(getClass().getSimpleName(), "value = " + value);
    })
    .setErrorListener((VolleyError error) -> {
        Log.e(getClass().getSimpleName(), error.toString());
    })
    .execute(worker);
```

When using methods which send a JSON object you will have to specify what you are sending.

```java

JsonObject exampleObject = new JsonObject();
exampleObject.addProperty("test", "value");
// equivalent to {"test": "value"}

RequestBuilder.post(JsonObject.class, JsonObject.class)
    .setRequestObject(exampleObject);

```

## Installation

For simplictity it is recommended to use the JitPack service to obtain a copy of the latest version of Requester.
To do this add the maven URL to your project `build.gradle`

```
allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}
```

Then simply require the Project and Artifact in your module `build.gradle`

```
dependencies {
    compile 'com.github.ljfio:Requester:v0.1.1'
}
```

## Licence

See the [LICENSE](LICENSE.md) file for license rights and limitations (MIT).
