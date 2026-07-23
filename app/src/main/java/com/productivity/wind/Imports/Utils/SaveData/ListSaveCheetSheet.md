
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
//"$listName:$id"
id: [items:bdc95870-d61e-4d6f-aac2-5b6663a0956f],
vars:
[stringVar][java.lang.String]["hello"],
[intVar][java.lang.Integer][2],
[boolVar][java.lang.Boolean][true]
}

```

Rules:

```text

Types Only:
java.lang.String
java.lang.Boolean
java.lang.Integer

Where use:
Not in compose function! Or atleast use remember

```

