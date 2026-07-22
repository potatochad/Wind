
# Allowed Example (Do Not Deviate)

```kotlin
class TestData : LazyData() {
    var stringVar by lazyS("hello")
    var intVar by lazyS(2)
    var boolVar by lazyS(yes)
    val stringVAL by lazyS("hello")
}
```

Stored as:

```text
 15:35:33.158 E [bad]   : { id: [items:bdc95870-d61e-4d6f-aac2-5b6663a0956f], vars: [name][class java.lang.String]["item"], [stringVar][class java.lang.String]["hello"], [intVar][class java.lang.Integer][2], [boolVar][class java.lang.Boolean][true], [stringVAL][class java.lang.String]["hello"] }
 15:35:33.158 E [bad]   : saving...
 15:35:35.230 E [bad]   : gotStyle: true

```

Rules:

```text

Only VARS
Only: Bool, string, Int (can store)

```

