Requester is a type safe library for Android

## Usage

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

Using the RequestBuilder we can create a `GetRequest` which returns a basic GSON `JsonObject`.

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
