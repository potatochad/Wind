
# Allowed Example (Do Not Deviate)

```kotlin
class TestData : LazyData() {
    var stringVar by lazyS("hello")
    var intVar by lazyS(2)
    var boolVar by lazyS(yes)
    val stringVAL by lazyS("hello")
}
```

Stored as (one str):

```text
{
id: [items:bdc95870-d61e-4d6f-aac2-5b6663a0956f],
vars:
[stringVar][class java.lang.String]["hello"],
[intVar][class java.lang.Integer][2],
[boolVar][class java.lang.Boolean][true],
[stringVAL][class java.lang.String]["hello"]
}

```

Rules:

```text

Only VARS
Only: Bool, string, Int (can store)

```

