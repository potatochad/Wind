
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
...

```

Rules:

```text

Only VARS
Only: Bool, string, Int (can store)

```

